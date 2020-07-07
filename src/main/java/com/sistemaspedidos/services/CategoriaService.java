package com.sistemaspedidos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.domain.dto.CategoriaDTO;
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
		Categoria  newObj = findById(categoria.getId());
		updateData(newObj , categoria);
		return categoriaRepository.save(newObj);
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
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) , orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		return new Categoria(categoriaDto.getId(), categoriaDto.getNome());
	}
	private void updateData(Categoria newObj, Categoria categoria) {
		  newObj.setNome(categoria.getNome());
	}
	
}
