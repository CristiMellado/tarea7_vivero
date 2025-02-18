package com.cristinamellado.vivero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristinamellado.vivero.modelo.Planta;


@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long>{
	
	Optional<Planta> findById(Long id);
	
	Planta findByCodigo(String codigo);

	List<Planta> findAllByOrderByCodigoAsc();

	List<Planta> findAllByOrderByIdAsc();

	

	
}
