package com.sistemaspedidos.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.services.CategoriaService;

@RestController
@RequestMapping(value = "categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscarPorId (@PathVariable Integer id) {
	
       Categoria obj = categoriaService.buscarPorId(id);
	   return ResponseEntity.ok().body(obj);
	}

}
