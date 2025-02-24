package com.cristinamellado.vivero.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cristinamellado.vivero.modelo.Pedido;
import com.cristinamellado.vivero.repository.PedidoRepository;

@Service
public class ServiciosPedido {

	@Autowired
	PedidoRepository pedidoRepository;
	
	public boolean realizarPedido(Pedido pedido) {
		if(pedidoRepository.saveAndFlush(pedido)!=null) {
			return true;
		}
		return false;
	}
	
	
}
