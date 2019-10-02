package com.bruno.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.dto.ClientDTO;
import com.bruno.cursomc.dto.ClientNewDTO;
import com.bruno.cursomc.services.ClientService;

@RestController   						 //A classe é um controlador REST.
@RequestMapping(value = "/clients")   //Vai responder por esse endpoint
public class ClientResource {
	
	@Autowired
	private ClientService service; //Acesso a classe de serviço
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)  //requisição para obter dados "GET"
	public ResponseEntity<Client> find(@PathVariable Integer id) {
		
		Client obj = service.find(id);
		
		//se tudo estiver ok, retorna o objeto da classe "Client"
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	//Recebe uma categoria no formato JSON e a insere no BD
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO objDTO){
		Client obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		//pega a URI do novo recurso inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO objDTO, @PathVariable Integer id){
		Client obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	/*O método busca as listas de Categorias no banco e converte para DTO. 
	Retorna uma lista de categorias DTO */
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> list = service.findAll();
		//Percorre a lista e instancia o DTO correspondente para cada elemento da lista
		List<ClientDTO> listDTO = list.stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClientDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0")Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "name")String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC")String direction) {
		Page<Client> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClientDTO> listDTO = list.map(obj -> new ClientDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
