package com.sistemaspedidos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.repositories.CategoriaRepository;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria findById(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Categorida não encontrado!"));		
	}
	
	
	public Categoria insert(Categoria categoria) {
		  categoria.setId(null);
		  return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		verificarExistencia(categoria);
		return categoriaRepository.save(categoria);
	}

	public void verificarExistencia(Categoria categoria) {
		findById(categoria.getId());
	}
}
