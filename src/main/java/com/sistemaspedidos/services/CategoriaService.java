package com.sistemaspedidos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.repositories.CategoriaRepository;
import com.sistemaspedidos.services.exceptions.DataIntegrityException;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public void verificarExistencia(Categoria categoria) {
		findById(categoria.getId());
	}
	
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

	public void delete(Integer id) { 
		try {
			
			categoriaRepository.deleteById(id);
			
		}catch (DataIntegrityViolationException  e) {
			throw new DataIntegrityException("Não é Possível excluir uma categoria que possui produto");
		}
	}
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
}
