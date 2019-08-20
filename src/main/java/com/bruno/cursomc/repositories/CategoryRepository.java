package com.bruno.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.cursomc.domain.Category;

//faz esta classe ter acesso à tabela "Categories" no BD
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
