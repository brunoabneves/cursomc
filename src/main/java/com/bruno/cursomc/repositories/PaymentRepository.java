package com.bruno.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.cursomc.domain.Payment;

//faz esta classe ter acesso à tabela "Categories" no BD
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
