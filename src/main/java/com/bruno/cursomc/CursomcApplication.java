package com.bruno.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.cursomc.domain.Address;
import com.bruno.cursomc.domain.Category;
import com.bruno.cursomc.domain.City;
import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.domain.Payment;
import com.bruno.cursomc.domain.PaymentWithCard;
import com.bruno.cursomc.domain.PaymentWithTicket;
import com.bruno.cursomc.domain.Product;
import com.bruno.cursomc.domain.Request;
import com.bruno.cursomc.domain.RequestItem;
import com.bruno.cursomc.domain.State;
import com.bruno.cursomc.domain.enums.ClientType;
import com.bruno.cursomc.domain.enums.PaymentState;
import com.bruno.cursomc.repositories.AddressRepository;
import com.bruno.cursomc.repositories.CategoryRepository;
import com.bruno.cursomc.repositories.CityRepository;
import com.bruno.cursomc.repositories.ClientRepository;
import com.bruno.cursomc.repositories.PaymentRepository;
import com.bruno.cursomc.repositories.ProductRepository;
import com.bruno.cursomc.repositories.RequestItemRepository;
import com.bruno.cursomc.repositories.RequestRepository;
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
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private RequestItemRepository requestItemRepository;
	
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
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PHYSICALPERSON);
		cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		//Adicionando os endereços do cliente 1
		cli1.getAdresses().addAll(Arrays.asList(a1, a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));
		
		//Máscara de formatação para data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Request req1 = new Request(null, sdf.parse("30/09/2017 10:32"), cli1, a1);	
		Request req2 = new Request(null, sdf.parse("10/10/2017 19:35"), cli1, a2);
		
		Payment paymt1 = new PaymentWithCard(null, PaymentState.QUITADO, req1, 6);
		req1.setPayment(paymt1);
		
		Payment paymt2 = new PaymentWithTicket(null, PaymentState.PENDENTE, req2, sdf.parse("20/10/2017 00:00"), null);
		req2.setPayment(paymt2);
		
		cli1.getRequests().addAll(Arrays.asList(req1, req2));
		
		requestRepository.saveAll(Arrays.asList(req1, req2));
		paymentRepository.saveAll(Arrays.asList(paymt1, paymt2));
		
		RequestItem ri1 = new RequestItem(req1, p1, 0.00, 1, 2000.00);
		RequestItem ri2 = new RequestItem(req1, p3, 0.00, 2, 80.00);
		RequestItem ri3 = new RequestItem(req2, p2, 100.00, 1, 800.00);
		
		req1.getItens().addAll(Arrays.asList(ri1, ri2));
		req2.getItens().addAll(Arrays.asList(ri3));
		
		p1.getItems().addAll(Arrays.asList(ri1));
		p2.getItems().addAll(Arrays.asList(ri3));
		p3.getItems().addAll(Arrays.asList(ri2));
		
		requestItemRepository.saveAll(Arrays.asList(ri1, ri2, ri3));
		
	}

}