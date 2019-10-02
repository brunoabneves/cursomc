package com.bruno.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.domain.PaymentWithTicket;
import com.bruno.cursomc.domain.Request;
import com.bruno.cursomc.domain.RequestItem;
import com.bruno.cursomc.domain.enums.PaymentState;
import com.bruno.cursomc.repositories.PaymentRepository;
import com.bruno.cursomc.repositories.RequestItemRepository;
import com.bruno.cursomc.repositories.RequestRepository;
import com.bruno.cursomc.security.UserSS;
import com.bruno.cursomc.services.exceptions.AuthorizationException;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class RequestService {
	
	//instancia automaticamente a dependencia "repo" pelo Spring
	@Autowired  
	private RequestRepository repo; //Acesso a classe de dados
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RequestItemRepository requestItemRepository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmailService emailService;
	
	public Request find(Integer id) {
		
		/*esta operação vai no BD, busca uma categoria com 
		o id e retorna uma categoria já pronta */
		Optional<Request> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Request.class.getName()));
	}
	
	@Transactional
	public Request insert(Request obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.find(obj.getClient().getId()));
		obj.getPayment().setState(PaymentState.PENDENTE);
		//Associação de mão dupla
		obj.getPayment().setRequest(obj);
		if(obj.getPayment() instanceof PaymentWithTicket) {
			PaymentWithTicket payment = (PaymentWithTicket) obj.getPayment();
			ticketService.fillPaymentWithTicket(payment, obj.getInstant());
		}
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		//Percorre todos os itens de pedido associados ao obj
		for(RequestItem ri : obj.getItens()) {
			ri.setDiscount(0.0);
			ri.setProduct(productService.find(ri.getProduct().getId()));
			ri.setPrice(ri.getProduct().getPrice());
			ri.setRequest(obj);
		}
		requestItemRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Request> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Client client = clientService.find(user.getId());
		//Retorna somente os pedidos do cliente logado
		return repo.findByClient(client, pageRequest);
	}
}
