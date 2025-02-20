package com.cristinamellado.vivero.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.modelo.Sesion;
import com.cristinamellado.vivero.servicio.ServiciosCredencial;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;


@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private Sesion sesion;
    
    @Autowired
    private ServiciosCredencial serviciosCredencial;
    
    @Autowired
    private ServiciosPlanta serviciosPlanta;

    public Sesion getSesion() {
        return this.sesion;
    }

	@PostMapping("/login")
	public String login(@RequestParam String usuario, @RequestParam String password, Model model) {
		sesion = serviciosCredencial.autenticar(usuario.trim(), password.trim());
	
		if (sesion.getPerfil() == Perfil.ADMINISTRADOR) {
			 model.addAttribute("user", sesion.getUsuario());
			return "administrador";
		} else if (sesion.getPerfil() == Perfil.PERSONAL) {
			 model.addAttribute("user", sesion.getUsuario());
			return "personal";
		} else {
			List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
			model.addAttribute("mensajeError", "Usuario o Contraseña incorrecta. Inténtalo de nuevo.");
	        model.addAttribute("plantas", listaPlantas);
			return "inicio";
		}	
	}

    @GetMapping("/logout")
    public String logout() {
    	sesion.setPerfil(Perfil.INVITADO);
    	sesion.setUsuario(null);
    	return "redirect:/inicio";
    }
}