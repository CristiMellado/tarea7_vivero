package com.cristinamellado.vivero.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cristinamellado.vivero.modelo.Persona;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

	@Query("Select p from Persona p where email=:email")
	Persona existePersona(@Param ("email") String email);

	Persona findByEmail(String email);

	Persona findByNombre(String nombre);
	
	Optional<Persona> findById(Long id);
}
