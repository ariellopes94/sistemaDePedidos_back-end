package com.sistemaspedidos.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaspedidos.domain.Pedido;
import com.sistemaspedidos.services.PedidoService;

@RestController
@RequestMapping(value = "pedidos")
public class PedidoResource {
	
	@Autowired
	PedidoService pedidoService;

	@GetMapping(value="/{id}")
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id){
		Pedido obj = pedidoService.buscarPorId(id);
	    return ResponseEntity.ok().body(obj);
	}
}
