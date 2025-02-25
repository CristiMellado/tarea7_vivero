package com.cristinamellado.vivero.servicio;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristinamellado.vivero.modelo.Ejemplar;
import com.cristinamellado.vivero.modelo.Mensaje;
import com.cristinamellado.vivero.modelo.Planta;
import com.cristinamellado.vivero.repository.EjemplarRepository;
import com.cristinamellado.vivero.validacion.Validacion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Service
public class ServiciosEjemplar {

	@Autowired
	EjemplarRepository ejemplarRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	public boolean insertarEjemplar(Ejemplar ejemplar) {
		if (Validacion.validarCodigoPlanta(ejemplar.getPlanta().getCodigo())) {
			if (ejemplarRepository.saveAndFlush(ejemplar) != null) {
				return true;
			}
		}
		return false;
	}

	public Long siguienteIdEjemplar() {
		return ejemplarRepository.siguienteIdEjemplar();
	}

    /**
     * Obtiene los ejemplares asociados a el id o los ids de las plantas seleccionadas.
     * Recorremos e inicializamos la listaMensajes, ya que no se carga automaticamente la coleccion relacionada y por ello
     * utilizamos el ejemplar.getListaMensajes().size().
     * @param seleccionIds Lista de id o ids de planta para la obtención de ejemplares
     * @return Lista de Ejemplares
     */
    @Transactional
    public List<Ejemplar> filtrarEjemplaresPlanta(List<Long> seleccionIds) {
        List<Ejemplar> ejemplares = ejemplarRepository.filtrarEjemplaresPlanta(seleccionIds);
        for (Ejemplar ejemplar : ejemplares) {
            ejemplar.getListaMensajes().size();
        }
        return ejemplares;
    }
    
    
    public List<Ejemplar> listaEjemplares(){
    	return ejemplarRepository.findAll();
    }
    
    public List<Mensaje> seguimientoMensajes(Long idEjemplar){
    	return ejemplarRepository.seguimientoMensajes(idEjemplar);
    }

	public Optional<Ejemplar> findById(Long id) {
		return ejemplarRepository.findById(id);
	}
	
	   public Optional<Ejemplar> findByNombre(String nombre) {
			return ejemplarRepository.findByNombre(nombre);
		}

	
	public List<Ejemplar> verEjemplaresId(){
		List<Ejemplar> lista = ejemplarRepository.findAllByOrderByPlantaNombreComunAscIdAsc();
		return lista;
	}
	
	public List<Ejemplar> obtenerCantidadEjemplares(String tipoPlanta, Integer cantidadEjemplares) {
		return ejemplarRepository.obtenerCantidadEjemplares(entityManager,tipoPlanta,cantidadEjemplares);
		
	}
	
	public void actualizarDisponible(List<Ejemplar> listaEjemplar) {
		ejemplarRepository.actualizarDisponible(listaEjemplar);
	}
}
