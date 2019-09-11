package com.bruno.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Request;
import com.bruno.cursomc.repositories.RequestRepository;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class RequestService {
	
	//instancia automaticamente a dependencia "repo" pelo Spring
	@Autowired  
	private RequestRepository repo; //Acesso a classe de dados
	
	public Request find(Integer id) {
		
		/*esta operação vai no BD, busca uma categoria com 
		o id e retorna uma categoria já pronta */
		Optional<Request> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Request.class.getName()));
	}
	
}
