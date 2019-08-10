package com.bruno.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController   						 //A classe é um controlador REST
@RequestMapping(value = "/categories")   //Vai responder por esse endpoint
public class CategoryResource {
	
	@RequestMapping(method = RequestMethod.GET)  //requisição para obter dados
	public String listar() {
		return "REST está funcionando";
	}
}
