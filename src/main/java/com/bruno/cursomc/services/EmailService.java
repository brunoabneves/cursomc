//Aqui estão definidas quais operações um serviço de email deve ter
package com.bruno.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.domain.Request;

public interface EmailService {

	void sendOrderConfirmationEmail(Request obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Request obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Client cliente, String newPass);
}
