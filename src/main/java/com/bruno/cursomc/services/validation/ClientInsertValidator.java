//Classe Valitator personalizado para esta anotação e para o nosso DTO

package com.bruno.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.domain.enums.ClientType;
import com.bruno.cursomc.dto.ClientNewDTO;
import com.bruno.cursomc.repositories.ClientRepository;
import com.bruno.cursomc.resources.exceptions.FieldMessage;
import com.bruno.cursomc.services.validation.utils.BR;
public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	
	}
	
	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getType().equals(ClientType.PHYSICALPERSON.getCod()) && !BR.isValidCPF(objDto.getCpfOrcnpj())) {
			list.add(new FieldMessage("cpfOrcnpj", "CPF inválido"));
		}
		
		if (objDto.getType().equals(ClientType.LEGALPERSON.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrcnpj())) {
			list.add(new FieldMessage("cpfOrcnpj", "CNPJ inválido"));
		}
		
		Client aux = repo.findByEmail(objDto.getEmail());
		if(aux !=null) {
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
