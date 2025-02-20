package com.cristinamellado.vivero.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cristinamellado.vivero.modelo.Credencial;
import com.cristinamellado.vivero.modelo.Perfil;
import com.cristinamellado.vivero.repository.CredencialRepository;

import jakarta.servlet.http.HttpSession;


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
	
	
	public void autenticar(String usuario, String password, HttpSession session) {
		if (usuario.equals("admin") && password.equals("admin")) {
			session.setAttribute("usuario", "admin");
			session.setAttribute("perfil", Perfil.ADMINISTRADOR);
		} else {
			Credencial credencial = credencialRepository.autenticar(usuario, password);
			if (credencial!=null) {
				session.setAttribute("usuario", usuario);
				session.setAttribute("perfil", Perfil.PERSONAL);
			}else {
				session.setAttribute("usuario", null);
				session.setAttribute("perfil", Perfil.INVITADO);
			}
		}
	}

	

	
}
