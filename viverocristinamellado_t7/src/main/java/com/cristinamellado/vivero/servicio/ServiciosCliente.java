package com.cristinamellado.vivero.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristinamellado.vivero.modelo.Cliente;
import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.repository.ClienteRepository;
import com.cristinamellado.vivero.repository.CredencialRepository;
import com.cristinamellado.vivero.validacion.Validacion;

@Service
public class ServiciosCliente {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private CredencialRepository credencialRepository;

    public Optional<Cliente> buscarPorNifnie(String nifnie) {
        return clienteRepository.findByNifnie(nifnie);
    }
    
    @Transactional
    public String registrarCliente(Cliente cliente, String usuario, String password, Perfil perfil) {
        // Validar datos del cliente
        if (!Validacion.validarEmail(cliente.getEmail())) {
            return "El email no tiene un formato válido";
        }
        
        if (!Validacion.validarNifNie(cliente.getNifnie())) {
            return "El NIF/NIE no tiene un formato válido";
        }
        
        if (!Validacion.validarUsuario(usuario)) {
            return "El usuario debe contener solo letras y números, sin espacios";
        }
        
        if (!Validacion.validarContrasena(password)) {
            return "La contraseña debe contener al menos 5 caracteres alfanuméricos";
        }
        
        if (clienteRepository.findByNifnie(cliente.getNifnie()).isPresent()) {
            return "Ya existe un cliente con ese NIF/NIE";
        }

        if (credencialRepository.findByUsuario(usuario).isPresent()) {
            return "El nombre de usuario ya está en uso";
        }
        
        try {
            Cliente clienteGuardado = clienteRepository.save(cliente);
            
            if(clienteGuardado != null){
                credencialRepository.insertarCredencial(usuario, password, perfil.toString());
            }
            
            return "Cliente registrado correctamente";
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el cliente: " + e.getMessage());
        }
    }
}

