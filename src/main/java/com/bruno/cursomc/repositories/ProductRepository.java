package com.bruno.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.domain.Product;

//faz esta classe ter acesso à tabela "Products" no BD
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Transactional(readOnly = true)
	//Faz a consulta Sem usar a anotação Query com JPQL
	Page<Product> findDistinctByNameContainingAndCategoriesIn(String name, List<Category>categories, Pageable pageRequest);
	
	/*@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% AND cat IN :categories")
	//A anotação @Param joga os valores das variáveis nos respectivos parametros da consulta JPQL
	Page<Product> search(@Param("name") String name, @Param("categories") List<Category>categories, Pageable pageRequest);
	*/
}
