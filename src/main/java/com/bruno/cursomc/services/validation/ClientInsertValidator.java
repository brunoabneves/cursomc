//Classe Valitator personalizado para esta anotação e para o nosso DTO

package com.bruno.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bruno.cursomc.domain.enums.ClientType;
import com.bruno.cursomc.dto.ClientNewDTO;
import com.bruno.cursomc.resources.exceptions.FieldMessage;
import com.bruno.cursomc.services.validation.utils.BR;
public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
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
