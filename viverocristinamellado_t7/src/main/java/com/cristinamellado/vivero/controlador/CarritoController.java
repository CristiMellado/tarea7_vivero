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
import com.cristinamellado.vivero.modelo.Mensaje;
import com.cristinamellado.vivero.modelo.Pedido;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.servicio.ServiciosCliente;
import com.cristinamellado.vivero.servicio.ServiciosEjemplar;
import com.cristinamellado.vivero.servicio.ServiciosMensaje;
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
	
	@Autowired
	ServiciosMensaje serviciosMensaje;

	@GetMapping
	public String carrito() {
		return "carrito";
	}

	@PostMapping("/anadir-carrito")
	public String anadirCarrito(@RequestParam Map<String, String> cantidad, HttpSession session, Model model) {
		// Obtener la lista de pedidos de la sesión (si no existe, inicializarla)
		List<Map<String, Integer>> carrito = (List<Map<String, Integer>>) session.getAttribute("carrito");
		if (carrito == null) {
			carrito = new ArrayList<>();
		}

		// Crear un nuevo pedido para esta acción de "Añadir al carrito"
		Map<String, Integer> nuevoPedido = new HashMap<>();
		
		for (Map.Entry<String, String> entry : cantidad.entrySet()) {
			if (entry.getKey().startsWith("cantidad[")) {
				String nombrePlanta = entry.getKey().replace("cantidad[", "").replace("]", "");
				int cantidadPlanta = Integer.parseInt(entry.getValue());
				if (cantidadPlanta > 0) {
					nuevoPedido.put(nombrePlanta, cantidadPlanta);
				}
			}
		}

	    // Si hay plantas seleccionadas, agregar el pedido a la lista
	    if (!nuevoPedido.isEmpty()) {
	        carrito.add(nuevoPedido);
	        model.addAttribute("mensajeCarrito", "El pedido se ha añadido con éxito");
	    } else {
	        model.addAttribute("mensajeCarrito", "Es necesario seleccionar la cantidad de ejemplares");
	    }
	    
		session.setAttribute("carrito", carrito);
		
		List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
		model.addAttribute("plantas", listaPlantas);
		return "/cliente";
	}

	@PostMapping("/eliminar-pedido")
	public String eliminarCarrito(@RequestParam int indicePedido, HttpSession session) {
	    List<Map<String, Integer>> carrito = (List<Map<String, Integer>>) session.getAttribute("carrito");

	    if (carrito != null && indicePedido >= 0 && indicePedido < carrito.size()) {
	        carrito.remove(indicePedido);
	    }

	    session.setAttribute("carrito", carrito);
	    return "redirect:/carrito";
	}

	@PostMapping("/realizar-pedido")
	public String realizarPedido(@RequestParam int indicePedido, HttpSession session, Model model) {
		List<Map<String, Integer>> carrito = (List<Map<String, Integer>>) session.getAttribute("carrito");
		if (carrito != null && indicePedido >= 0 && indicePedido < carrito.size()) {
			Map<String, Integer> ejemplaresCarrito = carrito.get(indicePedido);
			List<Ejemplar> ejemplaresPlanta = new ArrayList<>();

			Integer cantidadEjemplares=0;
			for (Map.Entry<String, Integer> entry : ejemplaresCarrito.entrySet()) {
				String tipoPlanta = entry.getKey();
				cantidadEjemplares = entry.getValue();
				ejemplaresPlanta.addAll(serviciosEjemplar.obtenerCantidadEjemplares(tipoPlanta, cantidadEjemplares));
			}

			Cliente cliente = serviciosCliente.findByNombre((String) session.getAttribute("usuario"));
			if(!ejemplaresPlanta.isEmpty() && ejemplaresPlanta.size() >= cantidadEjemplares) {
				Pedido pedido = serviciosPedido.realizarPedido(new Pedido(new Date(), ejemplaresPlanta, cliente));
				if (pedido != null) {
					serviciosEjemplar.actualizarDisponible(ejemplaresPlanta);
					for (Ejemplar ejemplar : ejemplaresPlanta) {
						String mensajePedido = "El cliente " + cliente.getNombre() + 
								" compró el ejemplar " + ejemplar.getNombre() + " el día " +
								new Date() + " en el pedido " + pedido.getId();
						
						serviciosMensaje.insertarMensaje(new Mensaje(mensajePedido, null, ejemplar));
					}
					carrito.remove(indicePedido);
					model.addAttribute("mensajeCarrito", "¡Pedido realizado con éxito!");
				}
			} else {
				carrito.remove(indicePedido);
				model.addAttribute("mensajeCarritoAgotado", "No hay existencias suficientes. No se pudo realizar el pedido");
			}

		}
		session.setAttribute("carrito", carrito);
		return "/carrito";
	}

}
