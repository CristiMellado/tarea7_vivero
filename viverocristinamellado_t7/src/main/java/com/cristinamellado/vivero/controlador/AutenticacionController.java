package com.cristinamellado.vivero.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AutenticacionController {
    
	@Autowired
	ServiciosPlanta serviciosPlanta;
	
    @GetMapping("/redireccionar")
    public String redireccionar(Authentication authentication, HttpSession session) {
        session.setAttribute("usuario", authentication.getName());
        // Obtener el perfil del usuario autenticado
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String perfil = authority.getAuthority(); 

            switch (perfil) {
                case "ROLE_ADMINISTRADOR":
                    session.setAttribute("perfil", Perfil.ADMINISTRADOR);
                    return "administrador"; 
                case "ROLE_PERSONAL":
                    session.setAttribute("perfil", Perfil.PERSONAL);
                    return "personal"; 
                case "ROLE_CLIENTE":
                    session.setAttribute("perfil", Perfil.CLIENTE);
                    return "redirect:/clientes/cliente"; 
                case "ROLE_INVITADO":
                    return "inicio"; 
                default:
                    return "inicio"; 
            }
        }

        return "inicio";
    }
	

    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.removeAttribute("usuario");
    	session.removeAttribute("perfil");
		session.removeAttribute("carrito");
    	session.invalidate();
    	return "redirect:/inicio";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado(){
        return "acceso-denegado";
    }
    
    @GetMapping("/error-login")
    public String errorLogin(Model model) {
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	model.addAttribute("mensajeError", "Usuario o contraseña incorrecta. Inténtalo de nuevo");
    	return "inicio";
    }
}