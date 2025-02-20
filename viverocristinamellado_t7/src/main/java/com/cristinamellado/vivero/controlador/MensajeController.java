package com.cristinamellado.vivero.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristinamellado.vivero.modelo.Ejemplar;
import com.cristinamellado.vivero.modelo.Mensaje;
import com.cristinamellado.vivero.modelo.Persona;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.modelo.Sesion;
import com.cristinamellado.vivero.servicio.ServiciosEjemplar;
import com.cristinamellado.vivero.servicio.ServiciosMensaje;
import com.cristinamellado.vivero.servicio.ServiciosPersona;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;

@Controller
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private ServiciosMensaje serviciosMensaje;
    
    @Autowired
    private ServiciosEjemplar serviciosEjemplar;
    
    @Autowired
    private ServiciosPersona serviciosPersona;
    
    @Autowired
    private ServiciosPlanta serviciosPlanta;
    
    private Sesion sesion;
    
    @GetMapping("/gestion-mensajes")
    public String gestionMensajes(Model model){
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-mensajes";
    }
    
    @GetMapping("/insertar-mensaje")
    public String insertarMensaje(Model model) {
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-mensajes";
    }
    
    @PostMapping("/insertar-mensaje")
    public String insertarMensaje(@RequestParam Long id, @RequestParam String mensaje,Model model) {
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	Ejemplar ejemplar = serviciosEjemplar.findById(id).get();
    	Persona persona = serviciosPersona.findByNombre(sesion.getUsuario());
    	Mensaje msj = new Mensaje(mensaje, persona, ejemplar);
    	if(serviciosMensaje.insertarMensaje(msj)) {
    		model.addAttribute("mensajeInsertar", "Se añadió el mensaje correctamente");
    	}else {
    		model.addAttribute("mensajeInsertar", "No se añadió el mensaje");
    	}
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-mensajes";
    }
    
    @GetMapping("/filtrar-mensajes-persona")
    public String filtrarMensajePersona(Model model) {
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-mensajes";
    }
    
    @PostMapping("/filtrar-mensajes-persona")
    public String filtrarMensajePersona(@RequestParam Long id,Model model) {
    	String nombrePersona=serviciosPersona.findById(id).get().getNombre();
    	List<Mensaje> listaMensajes = serviciosMensaje.filtrarMensajesPersona(id);
    	if(listaMensajes.size()>0) {
    		model.addAttribute("nombrePersona", nombrePersona);
    		model.addAttribute("personasFiltrado", listaMensajes);
    		model.addAttribute("mostrarModal", true);
    	}
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-mensajes";
    }
    
    @GetMapping("/filtrar-mensajes-planta")
    public String filtrarMensajePlanta(Model model) {
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-mensajes";
    }
    
    @PostMapping("/filtrar-mensajes-planta")
    public String filtrarMensajePlanta(@RequestParam Long id,Model model) {
    	String nombrePlanta = serviciosPlanta.existePlanta(id).get().getNombreComun();
    	List<Mensaje> listaMensajes = serviciosMensaje.filtrarMensajesPlanta(id);
    	if(listaMensajes.size()>0) {
    		model.addAttribute("plantasFiltrado", listaMensajes);
    		model.addAttribute("mostrarModalPlanta", true);
    	}
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	model.addAttribute("nombrePlanta", nombrePlanta);
    	return "gestion-mensajes";
    }
    
    @GetMapping("/filtrar-mensajes-fechas")
    public String filtrarMensajeFechas(Model model) {
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-mensajes";
    }
    
    @PostMapping("/filtrar-mensajes-fechas")
    public String filtrarMensajeFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin,Model model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime inicio = LocalDate.parse(fechaInicio, formatter).atTime(0, 0, 0);
		LocalDateTime fin = LocalDate.parse(fechaFin, formatter).atTime(23, 59, 59);
		List<Mensaje> listaMensajes = serviciosMensaje.filtrarMensajesRangoFechas(inicio, fin);
		if(listaMensajes.size() > 0) {
			model.addAttribute("fechasFiltrado", listaMensajes);
			model.addAttribute("mostrarModalFecha", true);
		} else {
			model.addAttribute("notificacion", "No existen mensajes en ese rango de fechas");
		}
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Persona> listaPersonas = serviciosPersona.mostrarPersonas();
    	model.addAttribute("personas", listaPersonas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-mensajes";
    }
    

} 