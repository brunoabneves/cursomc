package com.bruno.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.services.ClientService;

@RestController   						 //A classe é um controlador REST.
@RequestMapping(value = "/clients")   //Vai responder por esse endpoint
public class ClientResource {
	
	@Autowired
	private ClientService service; //Acesso a classe de serviço
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)  //requisição para obter dados "GET"
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Client obj = service.search(id);
		
		//se tudo estiver ok, retorna o objeto da classe "Client"
		return ResponseEntity.ok().body(obj);
	}
}
