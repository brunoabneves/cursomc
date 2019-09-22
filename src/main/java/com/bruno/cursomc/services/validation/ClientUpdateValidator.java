//Classe Valitator personalizado para esta anotação e para o nosso DTO

package com.bruno.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.dto.ClientDTO;
import com.bruno.cursomc.repositories.ClientRepository;
import com.bruno.cursomc.resources.exceptions.FieldMessage;
public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	public void initialize(ClientUpdate ann) {
	
	}
	
	@Override
	public boolean isValid(ClientDTO objDto, ConstraintValidatorContext context) {
		
		//O Map serve para armazernar chave e valor do http
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		//Salva o id da URI, na variável "uriId"
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Client aux = repo.findByEmail(objDto.getEmail());
		if(aux !=null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		/*Para cada objeto da lista do tipo FieldMessage (Erro personalizado),
		 * é adcicionado um erro correspondente na lista de erros do framework */
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}