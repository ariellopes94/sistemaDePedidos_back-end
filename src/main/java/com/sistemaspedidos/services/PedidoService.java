package com.sistemaspedidos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaspedidos.domain.Pedido;
import com.sistemaspedidos.repositories.PedidoRepository;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	public Pedido buscarPorId(Integer id) {
		 Optional<Pedido> obj = pedidoRepository.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado!"));
	}
}
