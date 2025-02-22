package com.cristinamellado.vivero.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.modelo.Persona;
import com.cristinamellado.vivero.servicio.ServiciosPersona;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private ServiciosPersona serviciosPersona;
    
	@GetMapping("/registrar-persona")
	 public String registrarPersona() {
		return "registrar-persona";
	}
    
    @PostMapping("/registrar-persona")
    public String registrarPersona(@RequestParam String nombre, @RequestParam String email, @RequestParam String usuario,@RequestParam String password, Model model) {
    	Persona persona = new Persona(nombre, email);
    	String resultado = serviciosPersona.registrarPersona(persona, usuario, password, Perfil.PERSONAL);
    	if(resultado.equals("Se insertó correctamente la persona y su credencial.")) {
    		model.addAttribute("res", "ok");
    	}else {
    		model.addAttribute("res", "ko");
    	}
    	model.addAttribute("mensajeResultado", resultado);
    	return "registrar-persona";
    }
} 