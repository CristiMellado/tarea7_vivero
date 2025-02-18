package com.cristinamellado.vivero;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cristinamellado.vivero.modelo.*;
import com.cristinamellado.vivero.servicio.*;


@Controller 
public class MainController {
	
	public Sesion sesion;
	
	@Autowired
	ServiciosPlanta serviciosPlanta;
	
	@Autowired
	ServiciosCredencial serviciosCredencial;
	
	@Autowired
	ServiciosPersona serviciosPersona;
	
	@Autowired
	ServiciosEjemplar serviciosEjemplar;
	
	@Autowired
	ServiciosMensaje serviciosMensaje;
	

	//*******************INICIO**********************************************************
	@GetMapping({"/","/inicio"}) //ruta de refenrencia desde los templates
	public String inicio(Model model) {
        List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
        model.addAttribute("plantas", listaPlantas);
		return "inicio";
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
	
//******************REGISTRAR PERSONA Y GESTION PLANTAS*****************************************************	
	@GetMapping("/registrar-persona") 
	 public String registrarPersona() {
		return "registrar-persona";  //este el nombre del template
	}
    
    @PostMapping("/registrar-persona")
    public String registrarPersona(@RequestParam String nombre, @RequestParam String email, @RequestParam String usuario,@RequestParam String password, Model model) {
    	Persona persona = new Persona(nombre, email);
    	String resultado = serviciosPersona.registrarPersona(persona, usuario, password);
    	if(resultado.equals("Se insertó correctamente la persona y su credencial.")) {
    		model.addAttribute("res", "ok");
    	}else {
    		model.addAttribute("res", "ko");
    	}
    	model.addAttribute("mensajeResultado", resultado);
    	return "registrar-persona";
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

    
    @GetMapping("/gestion-plantas")
    public String gestionPlantas(Model model){
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "gestion-plantas";
    }
//***************************GESTIÓN EJEMPLARES****************************************************************    
    
    /**
     * @param model
     * Carga la lista de plantas ordenada por id y la lista de ejemplares
     * @return el template gestion-ejemplares
     */
    @GetMapping("/gestion-ejemplares")
    public String gestionEjemplares(Model model){
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-ejemplares";
    }
    
    @GetMapping("/registrar-ejemplar")
	public String registrarEjemplar() {
	return "gestion-ejemplares";
}
    
    @PostMapping("/registrar-ejemplar")
    public String registrarEjemplar(@RequestParam Long id,Model model){
    	Planta planta = serviciosPlanta.existePlanta(id).get();
    	String nombreEjemplar= planta.getCodigo().toUpperCase()+"_"+serviciosEjemplar.siguienteIdEjemplar();
    	Persona persona = serviciosPersona.findByNombre(sesion.getUsuario());
    	String mensajeInicial = "Mensaje inicial de: "+ persona.getNombre()+" a las "+ new Date();
    	List<Mensaje> listaMensajes = new LinkedList<Mensaje>();
    	listaMensajes.add(new Mensaje(mensajeInicial, persona, null));
    	Ejemplar ejemplar = new Ejemplar(nombreEjemplar, planta, listaMensajes);
    	
    	if(serviciosEjemplar.insertarEjemplar(ejemplar)) {
    		model.addAttribute("msj", "Se insertó con éxito el ejemplar: "+nombreEjemplar);
    	}else {
    		model.addAttribute("msj", "No se insertó el ejemplar.");
    	}
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-ejemplares";
    }
    
    @GetMapping("/msj-seguimiento")
    public String msjSeguimiento() {
    	return "gestion-ejemplares";
    }
    
    @PostMapping("/msj-seguimiento")
    public String msjSeguimiento(@RequestParam Long id, Model model) {
    String nombreEjemplar=serviciosEjemplar.findById(id).get().getNombre();
    List<Mensaje> listaMsj= serviciosEjemplar.seguimientoMensajes(id);
    if (listaMsj.size() > 0) {
	    model.addAttribute("nombreEjemplar", nombreEjemplar);
	    model.addAttribute("mensajes", listaMsj);
	    model.addAttribute("mostrarModalMensaje", true);
    }
    List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    model.addAttribute("plantas", listaPlantas);
    List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    model.addAttribute("ejemplares", listaEjemplar);
    return "gestion-ejemplares";
    }
    
    @GetMapping("/filtrar-ejemplares")
    public String filtrarEjemplares(Model model) {
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	 model.addAttribute("mostrarModal", false);
    	return "gestion-ejemplares";
    }
    
    @PostMapping("/filtrar-ejemplares")
    public String filtrarEjemplares(@RequestParam(value = "seleccion", required = false) List<Long> seleccionIds, Model model) {
    	if(seleccionIds != null && !seleccionIds.isEmpty()) {
    		List<Ejemplar> listaEjemplares= serviciosEjemplar.filtrarEjemplaresPlanta(seleccionIds);
    		if(listaEjemplares.size()>0) {
    			model.addAttribute("ejemplaresFiltrado", listaEjemplares);
    			 model.addAttribute("mostrarModal", true);
    		}
    	}else {
    		model.addAttribute("notificacion", "No se ha seleccionado ninguna planta");	
    		model.addAttribute("mostrarModal", false);
    	}
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
    	model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-ejemplares";
    }
    
    
  //*******************GESTION MENSAJES*****************************************************************      
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
    
    
 //********************SESION **********************************************************************   
    @GetMapping("/logout")
    public String logout() {
    	sesion.setPerfil(Perfil.INVITADO);
    	sesion.setUsuario(null);
    	return "redirect:/inicio";
    }
   
    @GetMapping("/volver")
    public String volver(Model model) {
    	if (sesion != null) {
    		model.addAttribute("user", sesion.getUsuario());
    		if (sesion.getPerfil() == Perfil.ADMINISTRADOR) {
    			return "administrador";
    		} else {
    			return "personal";
    		}
    	} else {
    		return "redirect:/inicio";
    	}
    }
		
	
 
    
 
    
}//
