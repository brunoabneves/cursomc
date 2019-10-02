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

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.dto.CategoryDTO;
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
	
	@PreAuthorize("hasAnyRole('ADMIN')") 
	@RequestMapping(method = RequestMethod.POST)
	//Recebe uma categoria no formato JSON e a insere no BD
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO objDTO){
		Category obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		//pega a URI do novo recurso inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoryDTO objDTO, @PathVariable Integer id){
		Category obj = service.fromDTO(objDTO);
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
	
	@RequestMapping(method = RequestMethod.GET)
	/*O método busca as listas de Categorias no banco e converte para DTO. 
	Retorna uma lista de categorias DTO */
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> list = service.findAll();
		//Percorre a lista e instancia o DTO correspondente para cada elemento da lista
		List<CategoryDTO> listDTO = list.stream().map(obj -> new CategoryDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoryDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0")Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "name")String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC")String direction) {
		Page<Category> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoryDTO> listDTO = list.map(obj -> new CategoryDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}
