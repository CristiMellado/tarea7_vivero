package com.cristinamellado.vivero.servicio;

import com.cristinamellado.vivero.modelo.Credencial;
import com.cristinamellado.vivero.repository.CredencialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ServiciosDetallesUsuario implements UserDetailsService {

    @Autowired
    private CredencialRepository credencialRepositorio;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
        Credencial credencial = credencialRepositorio.findByUsuario(usuario)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + usuario));

        // Convertir el perfil a un rol de Spring Security
        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + credencial.getPerfil())
        );

        // Crear el UserDetails
        return new User(
            credencial.getUsuario(),
            credencial.getPassword(),
            authorities
        );
    }
}

