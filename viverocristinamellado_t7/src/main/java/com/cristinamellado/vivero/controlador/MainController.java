package com.cristinamellado.vivero.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cristinamellado.vivero.modelo.*;
import com.cristinamellado.vivero.servicio.*;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class MainController {
	
	
	@Autowired
	private ServiciosPlanta serviciosPlanta;
	
	//************************************ INICIO ************************************
	@GetMapping({"/","/inicio"})
	public String inicio(Model model) {
        List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
        model.addAttribute("plantas", listaPlantas);
		return "inicio";
	}

	//************************************ VOLVER ************************************
	@GetMapping("/volver")
    public String volver(Model model,HttpSession session) {
        if (session.getAttribute("perfil")== Perfil.ADMINISTRADOR) {
            return "administrador";
        } else if (session.getAttribute("perfil")== Perfil.PERSONAL) {
            return "personal";
        } else {
            List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
            model.addAttribute("plantas", listaPlantas);
            return "inicio";
        }
    }
    


    

     

    

    




		
	
 
    
 
    
}//
