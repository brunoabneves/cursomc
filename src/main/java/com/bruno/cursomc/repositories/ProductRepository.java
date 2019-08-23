package com.bruno.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.cursomc.domain.Product;

//faz esta classe ter acesso à tabela "Products" no BD
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
