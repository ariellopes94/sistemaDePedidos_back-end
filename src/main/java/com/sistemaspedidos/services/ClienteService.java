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
import com.sistemaspedidos.domain.Cliente;
import com.sistemaspedidos.domain.dto.CategoriaDTO;
import com.sistemaspedidos.domain.dto.ClienteDTO;
import com.sistemaspedidos.repositories.ClienteRepository;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void verificarExistencia (Cliente cliente) {
		findById(cliente.getId());
	}
	
	public Cliente findById(Integer id){
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado!"));
	}

	public Cliente update(Cliente cliente) {
		Cliente newObj = findById(cliente.getId());
		updateDate(newObj, cliente);
		return clienteRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		try {
		clienteRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir pois tem objetos relacionados");
		}
	}
	
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) , orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDto) {
		//return new Cliente(clienteDto.getId(), clienteDto.getNome());
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}
	
	private void updateDate(Cliente newObj , Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}