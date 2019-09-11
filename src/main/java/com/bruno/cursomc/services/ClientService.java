package com.bruno.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.repositories.ClientRepository;
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
	
}
