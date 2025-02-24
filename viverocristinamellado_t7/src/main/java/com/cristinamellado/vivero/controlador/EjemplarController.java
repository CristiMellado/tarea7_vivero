package com.cristinamellado.vivero.controlador;

import java.util.Date;
import java.util.LinkedList;
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
import com.cristinamellado.vivero.servicio.ServiciosEjemplar;
import com.cristinamellado.vivero.servicio.ServiciosPersona;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ejemplares")
public class EjemplarController {

    @Autowired
    private ServiciosEjemplar serviciosEjemplar;
    
    @Autowired
    private ServiciosPlanta serviciosPlanta;
    
    @Autowired
    private ServiciosPersona serviciosPersona;
    
    
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
    public String registrarEjemplar(@RequestParam Long id,Model model,HttpSession session){
    	Planta planta = serviciosPlanta.existePlanta(id).get();
    	String nombreEjemplar= planta.getCodigo().toUpperCase()+"_"+serviciosEjemplar.siguienteIdEjemplar();
    	Persona persona = serviciosPersona.findByNombre((String)session.getAttribute("usuario"));
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
    
    @GetMapping("/gestion-stock")
    public String gestionStock(Model model) {
        List<Ejemplar> listaEjemplar = serviciosEjemplar.listaEjemplares();
        model.addAttribute("ejemplares", listaEjemplar);
    	return "gestion-stock";
    }
    
    
} 