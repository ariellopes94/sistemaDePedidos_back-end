package com.sistemaspedidos.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaspedidos.domain.Produto;
import com.sistemaspedidos.domain.dto.ProdutoDTO;
import com.sistemaspedidos.resources.utils.URL;
import com.sistemaspedidos.services.ProdutoService;

@RestController
@RequestMapping(value = "produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;

	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Integer id){
		Produto obj = produtoService.findById(id);
	    return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> listObj = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listObjDto = listObj.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listObjDto);
	}
}
