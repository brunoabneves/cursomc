package com.bruno.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Client;
import com.bruno.cursomc.repositories.ClientRepository;
import com.bruno.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Client client = clientRepository.findByEmail(email);
		if(client == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		
		client.setPassword(pe.encode(newPass));
		
		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	// gera uma senha de 10 caracteres aleatórios
	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { // gera um dígito
			return (char) (rand.nextInt(10) + 48);
		}
		else if(opt == 1) { // gera letra maiúscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minúscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
