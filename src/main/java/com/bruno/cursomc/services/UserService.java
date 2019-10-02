package com.bruno.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bruno.cursomc.security.UserSS;

public class UserService {
	
	//retorna o user logado no sistema
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
