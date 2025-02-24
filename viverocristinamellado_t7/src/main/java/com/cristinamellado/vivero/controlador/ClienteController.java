package com.cristinamellado.vivero.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristinamellado.vivero.modelo.Cliente;
import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.servicio.ServiciosCliente;
import com.cristinamellado.vivero.servicio.ServiciosPlanta;


@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ServiciosCliente serviciosCliente;
    
    @Autowired
    private ServiciosPlanta serviciosPlanta;
    
    @GetMapping("/registrar-cliente")
    public String mostrarRegistroCliente() {
        return "registrar-cliente";
    }
    
    @PostMapping("/registrar-cliente")
    public String registrarCliente(@RequestParam String nombre, 
                                 @RequestParam String fechaNac,
                                 @RequestParam String nifnie,
                                 @RequestParam String direccion,
                                 @RequestParam String email,
                                 @RequestParam String telefono,
                                 @RequestParam String usuario,
                                 @RequestParam String password,
                                 Model model) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = dateFormat.parse(fechaNac);
            
            Cliente cliente = new Cliente(nombre, fecha, nifnie, direccion, email, telefono);
            String resultado = serviciosCliente.registrarCliente(cliente, usuario, password, Perfil.CLIENTE);
            
            if(resultado.equals("Cliente registrado correctamente")) {
                model.addAttribute("res", "ok");
            } else {
                model.addAttribute("res", "ko");
            }
            model.addAttribute("mensajeResultado", resultado);
            
        } catch (ParseException e) {
            model.addAttribute("res", "ko");
            model.addAttribute("mensajeResultado", "Error en el formato de la fecha");
        }
        return "registrar-cliente";
    }
    
    
    @GetMapping("/cliente")
    public String cliente(Model model) {
    	List<Planta> listaPlantas = serviciosPlanta.verPlantasId();
    	model.addAttribute("plantas", listaPlantas);
    	return "cliente";
    }
    

    
} 