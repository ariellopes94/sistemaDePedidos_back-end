package com.sistemaspedidos.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.domain.Pedido;
import com.sistemaspedidos.domain.dto.CategoriaDTO;
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
	
	@PostMapping
	public ResponseEntity<Pedido> insert(@Valid @RequestBody Pedido obj){
		
		 obj = pedidoService.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				  buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
