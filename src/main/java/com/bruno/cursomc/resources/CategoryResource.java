package com.bruno.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.cursomc.domain.Category;

@RestController   						 //A classe é um controlador REST
@RequestMapping(value = "/categories")   //Vai responder por esse endpoint
public class CategoryResource {
	
	@RequestMapping(method = RequestMethod.GET)  //requisição para obter dados "GET"
	public List<Category> toList() {
		
		Category cat1 = new Category(1, "Informática");
		Category cat2 = new Category(2, "Escritório");
		
		List<Category> list = new ArrayList<>();
		list.add(cat1);
		list.add(cat2);
		
		return list;	//retorna uma lista de categorias
	}
}
