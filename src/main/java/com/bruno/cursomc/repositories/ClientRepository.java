package com.bruno.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.cursomc.domain.Client;

//faz esta classe ter acesso à tabela "Categories" no BD
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	//O readOnly faz com que ela não nescessite ser envolvida como uma transação de BD
	@Transactional(readOnly = true)
	//Busca no bando de dados um cliente passando um email como argumento
	Client findByEmail(String email);
	
}
