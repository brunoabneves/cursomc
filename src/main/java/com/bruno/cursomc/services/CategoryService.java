package com.bruno.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.repositories.CategoryRepository;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	//instancia automaticamente a dependencia "repo" pelo Spring
	@Autowired  
	private CategoryRepository repo; //Acesso a classe de dados
	
	public Category search(Integer id) {
		
		/*esta operação vai no BD, busca uma categoria com 
		o id e retorna uma categoria já pronta */
		Optional<Category> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}
	
}
