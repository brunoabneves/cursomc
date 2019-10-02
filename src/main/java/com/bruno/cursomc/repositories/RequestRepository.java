package com.bruno.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.domain.Request;

//faz esta classe ter acesso à tabela "Categories" no BD
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>{
	
	@Transactional(readOnly=true)
	Page<Request> findByClient(Client client, Pageable pageRequest);
}
