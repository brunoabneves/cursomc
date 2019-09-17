package com.bruno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.dto.ClientDTO;
import com.bruno.cursomc.repositories.ClientRepository;
import com.bruno.cursomc.services.exceptions.DataIntegrityException;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {
	
	//instancia automaticamente a dependencia "repo" pelo Spring
	@Autowired  
	private ClientRepository repo; //Acesso a classe de dados
	
	public Client find(Integer id) {
		/*esta operação vai no BD, busca uma categoria com 
		o id e retorna uma categoria já pronta */
		Optional<Client> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
	public Client update(Client obj) {
		//testa se o objeto existe
		Client newObj = find(obj.getId());
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
			throw new DataIntegrityException("Não é possível excluir pois existem entidades relacionadas");
		}
	}
	
	public List<Client> findAll(){
		return repo.findAll();
	}
	
	//Paginação
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//Instancia uma Cliente a partir de um DTO
	public Client fromDTO(ClientDTO objDTO) {
		return new Client(objDTO.getId(), objDTO.getName() , objDTO.getEmail(), null, null);
	}
	
	//Atualiza os valores de "newObj" sem alterar "cpfOrcnpj" ou "type"
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
}
