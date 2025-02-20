package com.cristinamellado.vivero.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;

@Controller
@RequestMapping("/plantas")
public class PlantaController {
    
    @Autowired
    private ServiciosPlanta serviciosPlanta;
    
    @GetMapping("/gestion-plantas")
    public String gestionPlantas(Model model){
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-plantas";
    }
    
    @GetMapping("/nueva-planta")
    	public String nuevaPlanta() {
    	return "gestion-plantas";
    }
    
    @PostMapping("/nueva-planta")
    public String nuevaPlanta(@RequestParam String codigo,@RequestParam String nombreComun,@RequestParam String nombreCientifico, Model model) {
    	Planta planta = new Planta(codigo.toUpperCase(), nombreComun, nombreCientifico);
    	if(serviciosPlanta.insertarPlanta(planta)) {
    		model.addAttribute("mensajeCrear", "Se insertó la planta "+ nombreComun.toUpperCase()+ " con éxito");
    		model.addAttribute("resCrear", "ok");
    	}else {
    		model.addAttribute("mensajeCrear", "Introduce un código que no exista con solo letras sin tildes, ni espacios en blanco");
    		model.addAttribute("resCrear", "ko");
    	}
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-plantas";
    }
    
    @GetMapping("/modificar-planta")
	public String modificarPlanta() {
    	return "gestion-plantas";
    }	
    
    @PostMapping("/modificar-planta")
	public String modificarPlanta(@RequestParam Long id,@RequestParam String nombreComun,@RequestParam String nombreCientifico, Model model) {
    	Optional<Planta> planta = serviciosPlanta.existePlanta(id);
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	if (planta.isPresent()) {
    		 Planta plantaExistente = planta.get();
    		 plantaExistente.setNombreComun(nombreComun);
    		 plantaExistente.setNombreCientifico(nombreCientifico);
    		if(serviciosPlanta.modificarPlanta(plantaExistente)) {
    			model.addAttribute("mensajeModificar", "Se modificó la planta con código "+ plantaExistente.getCodigo().toUpperCase()+ " con éxito");
    			model.addAttribute("resModificar", "ok");
    		}else {
    			model.addAttribute("mensajeModificar", "No se pudo modificar la planta con código "+ plantaExistente.getCodigo().toUpperCase());
    			model.addAttribute("resModificar", "ko");
    		}
    		
    	}
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-plantas";
    }
} 