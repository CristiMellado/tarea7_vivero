package com.cristinamellado.vivero.servicio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristinamellado.vivero.modelo.Mensaje;
import com.cristinamellado.vivero.repository.MensajeRepository;


@Service
public class ServiciosMensaje {

	@Autowired
	MensajeRepository mensajesRepository;

	
	public boolean insertarMensaje(Mensaje p) {
		if(mensajesRepository.saveAndFlush(p)!=null) {
			return true;
		}
		return false;
	}

	public List<Mensaje> filtrarMensajesPersona(Long idPersona){
		return mensajesRepository.filtrarMensajesPersona(idPersona);
	}
	
	public List<Mensaje> filtrarMensajesPlanta(Long idPlanta){
		return mensajesRepository.filtrarMensajesPlanta(idPlanta);
	}
	
	public List<Mensaje> filtrarMensajesRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
		return mensajesRepository.filtrarMensajesRangoFechas(fechaInicio, fechaFin);
	}
	
}
