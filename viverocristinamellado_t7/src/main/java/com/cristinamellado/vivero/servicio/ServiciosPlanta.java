package com.cristinamellado.vivero.servicio;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.repository.PlantaRepository;
import com.cristinamellado.vivero.validacion.Validacion;


@Service
public class ServiciosPlanta {

	
	@Autowired
	PlantaRepository plantaRepository;

	/**
	 * CU1: Ver Plantas
	 * @return lista completa de plantas ordenadas por código alfabéticamente
	 */
	public List<Planta> verPlantas(){
		return plantaRepository.findAllByOrderByCodigoAsc();
	}
	
	public List<Planta> verPlantasId(){
		return plantaRepository.findAllByOrderByIdAsc();
	}
	
	
	/**
	 * CU4A: Insertar Planta
	 * @return boolean que indica si se pudo o no insertar la planta
	 */
	public boolean insertarPlanta(Planta planta) {
		if (codigoCorrecto(planta.getCodigo())) {
			plantaRepository.saveAndFlush(planta);
			return true;
		}
		return false;
	}
	
	
	/**
	 * CU4B: Modificar Planta
	 * @return boolean que indica si se pudo o no modificar la planta
	 */
	public boolean modificarPlanta(Planta planta) {
		if(plantaRepository.existsById(planta.getId())) {
			plantaRepository.saveAndFlush(planta);
			return true;
		}
		return false;
	}
	
	public Optional<Planta> existePlanta(Long id) {
		return plantaRepository.findById(id);
	}
	
	public Planta findByCodigo(String codigo) {
		return plantaRepository.findByCodigo(codigo);
	}


	public boolean codigoCorrecto(String codigo) {
		if(Validacion.validarCodigoPlanta(codigo) && plantaRepository.findByCodigo(codigo) == null) {
			return true;
		}
		return false;
	}
	
	
	

	
}
