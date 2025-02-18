package com.cristinamellado.vivero.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristinamellado.vivero.modelo.Credencial;
import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.modelo.Sesion;
import com.cristinamellado.vivero.repository.CredencialRepository;


@Service
public class ServiciosCredencial {

	@Autowired
	CredencialRepository credencialRepository;

	
	public boolean existeCredencial(String usuario) {
		if( credencialRepository.findByUsuario(usuario)!=null) {
			return true;
		}
		return false;
	}
	
	/*
	public boolean autenticar(String usuario, String password) {
		if (usuario.equals("admin") && password.equals("admin")) {
			FachadaInvitado.sesion = new Sesion(Perfil.ADMINISTRADOR);
			return true;
		} else {
			Credencial ret = credencialRepository.autenticar(usuario, password);
			if (ret!=null) {
				FachadaInvitado.sesion = new Sesion(Perfil.PERSONAL);
				return true;
			}
		}
		return false;
	}*/
	
	public Sesion autenticar(String usuario, String password) {
		Sesion sesion = new Sesion(Perfil.INVITADO);
		if (usuario.equals("admin") && password.equals("admin")) {
			sesion.setUsuario("admin");
			sesion.setPerfil(Perfil.ADMINISTRADOR);
		} else {
			Credencial ret = credencialRepository.autenticar(usuario, password);
			if (ret!=null) {
				sesion.setUsuario(usuario);
				sesion.setPerfil(Perfil.PERSONAL);
			}
		}
		return sesion;
	}

	

	
}
