package com.bruno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.dto.CategoryDTO;
import com.bruno.cursomc.repositories.CategoryRepository;
import com.bruno.cursomc.services.exceptions.DataIntegrityException;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	//instancia automaticamente a dependencia "repo" pelo Spring
	@Autowired  
	private CategoryRepository repo; //Acesso a classe de dados
	
	public Category find(Integer id) {
		
		/*esta operação vai no BD, busca uma categoria com 
		o id e retorna uma categoria já pronta */
		Optional<Category> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}
	
	public Category insert(Category obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Category update(Category obj) {
		//testa se o objeto existe
		Category newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		//testa se o id existe
		find(id);
		try{
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Category> findAll(){
		return repo.findAll();
	}
	
	//Paginação
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//Instancia uma Categoria a partir de um DTO
	public Category fromDTO(CategoryDTO objDTO) {
		return new Category(objDTO.getId(), objDTO.getName());
	}
	
	//Atualiza o valor nome de "newObj" sem alterar os outros campos
	private void updateData(Category newObj, Category obj) {
		newObj.setName(obj.getName());
	}
	
}
