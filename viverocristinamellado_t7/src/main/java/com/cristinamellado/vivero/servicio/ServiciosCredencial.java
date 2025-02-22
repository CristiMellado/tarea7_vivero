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
	
	
	/**
	 * @param usuario
	 * @param password
	 * @param session
	 * Autentica el usuario y en caso de que exista se guardar en sesión el usuario y el perfil
	 */
	public void autenticar(String usuario, String password, HttpSession session) {
			Credencial credencial = credencialRepository.autenticar(usuario, password);
			if (credencial!=null) {
				session.setAttribute("usuario", usuario);
				session.setAttribute("perfil", credencial.getPerfil());
			}else {
				session.setAttribute("usuario", null);
				session.setAttribute("perfil", Perfil.INVITADO);
			}
		}
	

	

	
}
