//Aqui estão definidas quais operações um serviço de email deve ter
package com.bruno.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.bruno.cursomc.domain.Request;

public interface EmailService {

	void sendOrderConfirmationEmail(Request obj);
	
	void sendEmail(SimpleMailMessage msg);
}
