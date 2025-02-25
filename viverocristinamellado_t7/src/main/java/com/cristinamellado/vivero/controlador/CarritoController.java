package com.cristinamellado.vivero.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristinamellado.vivero.modelo.Cliente;
import com.cristinamellado.vivero.modelo.Ejemplar;
import com.cristinamellado.vivero.modelo.Pedido;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.servicio.ServiciosCliente;
import com.cristinamellado.vivero.servicio.ServiciosEjemplar;
import com.cristinamellado.vivero.servicio.ServiciosPedido;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	ServiciosPlanta serviciosPlanta;
	
	@Autowired
	ServiciosPedido serviciosPedido;
	
	@Autowired
	ServiciosEjemplar serviciosEjemplar;
	
	@Autowired
	ServiciosCliente serviciosCliente;
	
    @GetMapping
    public String carrito() {
    	return "carrito";
    }
    
    @PostMapping("/anadir-carrito")
    public String anadirCarrito(@RequestParam Map<String, String> cantidad, HttpSession session, Model model) {
        Map<String, Integer> ejemplaresCarrito = (Map<String, Integer>) session.getAttribute("carrito");
        if (ejemplaresCarrito == null) {
            ejemplaresCarrito = new HashMap<>();
        }
    
        for (Map.Entry<String, String> entry : cantidad.entrySet()) {
            if (entry.getKey().startsWith("cantidad[")) {
                String nombrePlanta = entry.getKey().replace("cantidad[", "").replace("]", "");
                int cantidadPlanta = Integer.parseInt(entry.getValue()); // Convertir a Integer
                if (cantidadPlanta > 0) {
                    ejemplaresCarrito.put(nombrePlanta, cantidadPlanta);
                }
            }
        }
        
        session.setAttribute("carrito", ejemplaresCarrito);
        if(!ejemplaresCarrito.isEmpty()){
            model.addAttribute("mensajeCarrito", "El producto se ha añadido con éxito");
        } else {
            model.addAttribute("mensajeCarrito", "Es necesario seleccionar la cantidad de ejemplares");
        }
        List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
        model.addAttribute("plantas", listaPlantas);
        return "/cliente";
    }
        
    @PostMapping("/eliminar-pedido")
    public String eliminarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        return "redirect:/carrito"; 
    }
    
    @PostMapping("/realizar-pedido")
    public String realizarPedido(HttpSession session, Model model) {
    	 Map<String, Integer> ejemplaresCarrito = (Map<String, Integer>) session.getAttribute("carrito");
    	 if (ejemplaresCarrito != null) {
    		 List<Ejemplar> ejemplaresPlanta = new ArrayList<Ejemplar>();
    		 
    		 for (Map.Entry<String, Integer> entry : ejemplaresCarrito.entrySet()) {
    			 String tipoPlanta = entry.getKey();
    			 Integer cantidadEjemplares = entry.getValue();
    			 ejemplaresPlanta.addAll(serviciosEjemplar.obtenerCantidadEjemplares(tipoPlanta,cantidadEjemplares));			
    		 }
    		 
    	  Cliente cliente = serviciosCliente.findByNombre((String)session.getAttribute("usuario"));
    	 if(serviciosPedido.realizarPedido(new Pedido(new Date(), ejemplaresPlanta, cliente))) {
    		 session.removeAttribute("carrito");
    		 serviciosEjemplar.actualizarDisponible(ejemplaresPlanta);
    	 }
    	 
    }
    	 model.addAttribute("mensajeCarrito", "¡Pedido realizado con éxito!");
    	 return "/carrito";
    }
    
}
