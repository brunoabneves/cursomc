package com.bruno.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.services.CategoryService;

@RestController   						 //A classe é um controlador REST.
@RequestMapping(value = "/categories")   //Vai responder por esse endpoint
public class CategoryResource {
	
	@Autowired
	private CategoryService service; //Acesso a classe de serviço
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)  //requisição para obter dados "GET"
	public ResponseEntity<Category> find(@PathVariable Integer id) {
		
		Category obj = service.find(id);
		
		//se tudo estiver ok, retorna o objeto da classe "Category"
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	//Recebe uma categoria no formato JSON e a insere no BD
	public ResponseEntity<Void> insert(@RequestBody Category obj){
		obj = service.insert(obj);
		//pega a URI do novo recurso inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Category obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
}
