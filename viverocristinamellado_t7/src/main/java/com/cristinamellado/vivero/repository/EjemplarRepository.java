package com.cristinamellado.vivero.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cristinamellado.vivero.modelo.Ejemplar;
import com.cristinamellado.vivero.modelo.Mensaje;
import com.cristinamellado.vivero.modelo.Planta;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long>{


	/**
	 * Obtener la lista de todos los ejemplares. 
	 * Si la lista tiene ejemplares, se devuelve el numero de ejemplares + 1 y sino se devuelve 1
	 * @return Long que indica la siguiente posición del Ejemplar a insertar
	 */
	default Long siguienteIdEjemplar() {
		List<Ejemplar> listaEjemplares = findAll();
		if(!listaEjemplares.isEmpty()) {
			return count()+1;
		}
		return 1L;
	}

	Optional<Ejemplar> findById( Long id);
	
	@Query("Select e from Ejemplar e where e.planta.id in (:seleccionIds)")
	 List<Ejemplar> filtrarEjemplaresPlanta(@Param("seleccionIds") List<Long> seleccionIds);
	
	@Query("""
			Select e.listaMensajes from Ejemplar e
			JOIN e.listaMensajes m 
			JOIN m.persona 
			WHERE e.id = :idEjemplar
			ORDER BY m.fechahora
			""")
	List<Mensaje> seguimientoMensajes(@Param("idEjemplar") Long idEjemplar);
	
	List<Ejemplar> findAllByOrderByPlantaNombreComunAscIdAsc();
	
	Optional<Ejemplar> findByNombre(String nombre);
	
	
	/**
	 * @param entityManager
	 * @param tipoPlanta
	 * @param cantidadEjemplares
	 * @return
	 * Crea una consulta que devuelve una lista de ejemplares que tengan como nombre de planta el parametro
	 * que se le pasa (tipoPlanta y muestra los resultados indicados.
	 */
	default List<Ejemplar> obtenerCantidadEjemplares(EntityManager entityManager,String tipoPlanta, int cantidadEjemplares){
		return entityManager.createQuery("Select e From Ejemplar e Where e.planta.nombreComun = :tipoPlanta",Ejemplar.class)
				.setParameter("tipoPlanta", tipoPlanta)
				.setMaxResults(cantidadEjemplares)
				.getResultList();
	}

	@Modifying
	@Transactional
	@Query("UPDATE Ejemplar e SET e.disponible = false WHERE e IN :listaEjemplar")
    void actualizarDisponible(List<Ejemplar> listaEjemplar);
	
	
}//
