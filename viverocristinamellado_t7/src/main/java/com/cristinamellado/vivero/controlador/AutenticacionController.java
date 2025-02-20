package com.cristinamellado.vivero.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cristinamellado.vivero.modelo.Credencial;
import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.servicio.ServiciosCredencial;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AutenticacionController {
    
  
    
    @Autowired
    private ServiciosCredencial serviciosCredencial;
    
    @Autowired
    private ServiciosPlanta serviciosPlanta;



	@PostMapping("/login")
	public String login(@RequestParam String usuario, @RequestParam String password, Model model,HttpSession session) {
		List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
		model.addAttribute("plantas", listaPlantas);
		serviciosCredencial.autenticar(usuario.trim(), password.trim(),session);

		if(session.getAttribute("usuario")!=null) {
			Perfil perfil = (Perfil) session.getAttribute("perfil");
			
			if(perfil !=null) {
				switch(perfil) {
				case ADMINISTRADOR:
					return "administrador";
				case PERSONAL:
					return "personal";
				case CLIENTE:
					return "cliente";
				case INVITADO:
					return "inicio";
				}
			}
		}
		model.addAttribute("mensajeError", "Usuario o Contraseña incorrecta. Inténtalo de nuevo");
		return "inicio";
	}
	

    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.removeAttribute("usuario");
    	session.removeAttribute("perfil");
    	session.invalidate();
    	return "redirect:/inicio";
    }
}