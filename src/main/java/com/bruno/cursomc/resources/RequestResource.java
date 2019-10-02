package com.bruno.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.cursomc.domain.Request;
import com.bruno.cursomc.services.RequestService;

@RestController   						 //A classe é um controlador REST.
@RequestMapping(value="/requests")   //Vai responder por esse endpoint
public class RequestResource {
	
	@Autowired
	private RequestService service; //Acesso a classe de serviço
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)  //requisição para obter dados "GET"
	public ResponseEntity<Request> find(@PathVariable Integer id) {
		
		Request obj = service.find(id);
		
		//se tudo estiver ok, retorna o objeto da classe "Request"
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	//Recebe um Pedido no formato JSON e a insere no BD
	public ResponseEntity<Void> insert(@Valid @RequestBody Request obj){
		obj = service.insert(obj);
		//pega a URI do novo recurso inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Request>> findPage(
			@RequestParam(value="page", defaultValue = "0")Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "instant")String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC")String direction) {
		Page<Request> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
