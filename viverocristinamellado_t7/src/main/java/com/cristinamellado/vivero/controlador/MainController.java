package com.cristinamellado.vivero.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cristinamellado.vivero.modelo.*;
import com.cristinamellado.vivero.servicio.*;

@RequestMapping("/")
@Controller
public class MainController {
	
	private Sesion sesion;
	
	@Autowired
	private ServiciosPlanta serviciosPlanta;
	
	@Autowired
	private AuthController authController;

	//************************************ INICIO ************************************
	@GetMapping({"/","/inicio"})
	public String inicio(Model model) {
        List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
        model.addAttribute("plantas", listaPlantas);
		return "inicio";
	}

	//************************************ VOLVER ************************************
	@GetMapping("/volver")
    public String volver(Model model) {
        this.sesion = authController.getSesion();
        if (sesion.getPerfil() == Perfil.ADMINISTRADOR) {
            model.addAttribute("user", sesion.getUsuario());
            return "administrador";
        } else if (sesion.getPerfil() == Perfil.PERSONAL) {
            model.addAttribute("user", sesion.getUsuario());
            return "personal";
        } else {
            List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
            model.addAttribute("plantas", listaPlantas);
            return "inicio";
        }
    }
    


    

     

    

    




		
	
 
    
 
    
}//
