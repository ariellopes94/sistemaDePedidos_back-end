package com.sistemaspedidos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaspedidos.domain.Cliente;
import com.sistemaspedidos.repositories.ClienteRepository;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscarPorId(Integer id){
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado!"));
	}

}
