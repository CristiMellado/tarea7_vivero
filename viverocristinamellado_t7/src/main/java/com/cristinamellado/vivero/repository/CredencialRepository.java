package com.cristinamellado.vivero.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cristinamellado.vivero.modelo.Credencial;


@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long>{


	@Query(value = "SELECT * FROM Credenciales c WHERE c.usuario = :usuario AND c.password = :password", nativeQuery = true)
	Credencial autenticar(String usuario, String password);

	Optional<Credencial> findByUsuario(String usuario);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Credenciales (usuario, password,perfil) VALUES (:usuario, :password, :perfil)", nativeQuery = true)
	void insertarCredencial(String usuario, String password, String perfil);

	
	
}
