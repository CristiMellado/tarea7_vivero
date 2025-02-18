package com.cristinamellado.vivero.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cristinamellado.vivero.modelo.Mensaje;



@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{


	@Query("""
			Select m From Mensaje m where m.persona.id=:idPersona
			""")
	List<Mensaje> filtrarMensajesPersona(@Param("idPersona") Long idPersona);
	

	@Query("""
			Select m From Mensaje m where m.ejemplar.planta.id=:idPlanta
			""")
	List<Mensaje> filtrarMensajesPlanta(@Param("idPlanta") Long idPlanta);
	
	
	@Query("""
			Select m From Mensaje m where m.fechahora BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fechahora DESC
			""")
	List<Mensaje> filtrarMensajesRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
	
}
