package com.bruno.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class DBService {

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
	
	public void instantiateTestDatabase() throws ParseException {

		// objetos que serão inseridos no bd
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		// add uma lista de produtos a categoria 1 (cat1)
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		// Add os produtos as suas respectivas categorias
		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		/*
		 * salva uma lista de objetos no BD.("Arrays.asList" cria uma lista) Salva todas
		 * as categorias e produtos
		 */
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", state1);
		City c2 = new City(null, "São Paulo", state2);
		City c3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(c1));
		state2.getCities().addAll(Arrays.asList(c2, c3));

		// Salva primeiro os estados pois uma cidade obrigatoriamente precisa de 1
		// estado
		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", "brunoabnevestst@gmail.com", "36378912377", ClientType.PHYSICALPERSON);
		cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));

		Address a1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		// Adicionando os endereços do cliente 1
		cli1.getAdresses().addAll(Arrays.asList(a1, a2));

		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));

		// Máscara de formatação para data
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
