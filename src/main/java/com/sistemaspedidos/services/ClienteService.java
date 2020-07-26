package com.sistemaspedidos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaspedidos.domain.Cidade;
import com.sistemaspedidos.domain.Cliente;
import com.sistemaspedidos.domain.Endereco;
import com.sistemaspedidos.domain.dto.ClienteDTO;
import com.sistemaspedidos.domain.dto.ClienteNewDTO;
import com.sistemaspedidos.enuns.Perfil;
import com.sistemaspedidos.enuns.TipoCliente;
import com.sistemaspedidos.repositories.ClienteRepository;
import com.sistemaspedidos.repositories.EnderecoRepository;
import com.sistemaspedidos.security.UserSpringSecurity;
import com.sistemaspedidos.services.exceptions.AuthorizationException;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passowrdEncoder;
	
	public void verificarExistencia (Cliente cliente) {
		findById(cliente.getId());
	}
	
	public Cliente findById(Integer id){
		
		UserSpringSecurity user = UserService.authenticated();
		
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado!"));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipoCliente()), passowrdEncoder.encode(objDto.getSenha()));
	    Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
	    
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		 }
		return cli;
	}
	
	private void updateDate(Cliente newObj , Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}