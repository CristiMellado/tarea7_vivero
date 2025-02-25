package com.cristinamellado.vivero.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristinamellado.vivero.modelo.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByNifnie(String nifnie);
	
	Cliente findByNombre(String nombre);
	
}
