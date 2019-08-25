package com.bruno.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.domain.City;
import com.bruno.cursomc.domain.Product;
import com.bruno.cursomc.domain.State;
import com.bruno.cursomc.repositories.CategoryRepository;
import com.bruno.cursomc.repositories.CityRepository;
import com.bruno.cursomc.repositories.ProductRepository;
import com.bruno.cursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		//objetos que serão inseridos no bd
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		//add uma lista de produtos a categoria 1 (cat1)
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		//Add os produtos as suas respectivas categorias
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		/*salva uma lista de objetos no BD.("Arrays.asList" cria uma lista)
		 *Salva todas as categorias e produtos */
		categoryRepository.saveAll(Arrays.asList(cat1,cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", state1);
		City c2 = new City(null, "São Paulo", state2);
		City c3 = new City(null, "Campinas", state2);
		
		state1.getCities().addAll(Arrays.asList(c1));
		state2.getCities().addAll(Arrays.asList(c2, c3));
		
		//Salva primeiro os estados pois uma cidade obrigatoriamente precisa de 1 estado
		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
	}

}