package com.bruno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.domain.Product;
import com.bruno.cursomc.repositories.CategoryRepository;
import com.bruno.cursomc.repositories.ProductRepository;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	//instancia automaticamente a dependencia "repo" pelo Spring
	@Autowired  
	private ProductRepository repo; //Acesso a classe de dados
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Product find(Integer id) {
		
		/*esta operação vai no BD, busca uma categoria com 
		o id e retorna uma categoria já pronta */
		Optional<Product> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}
	
	//Paginação
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}
}
