package com.sistemaspedidos.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sistemaspedidos.domain.Cliente;
import com.sistemaspedidos.domain.dto.ClienteDTO;
import com.sistemaspedidos.domain.dto.ClienteNewDTO;
import com.sistemaspedidos.services.ClienteService;

@RestController
@RequestMapping(value = "clientes")
public class ClienteResource {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id){
		Cliente obj = clienteService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value="/email")
	public ResponseEntity<Cliente> buscarPorEmail(@RequestParam(value = "value") String email){
		Cliente obj = clienteService.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteDTO clienteDto , @PathVariable Integer id){
		
		Cliente categoria = clienteService.fromDTO(clienteDto);
		
		 categoria.setId(id);
		 clienteService.update(categoria);
		 return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteDto){
		Cliente obj = clienteService.fromDTO(clienteDto);
		obj = clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
	    clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> listObj = clienteService.findAll();
		List<ClienteDTO> listObjDto = listObj.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listObjDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		Page<Cliente> listObj = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listObjDto = listObj.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listObjDto);
	}
}
