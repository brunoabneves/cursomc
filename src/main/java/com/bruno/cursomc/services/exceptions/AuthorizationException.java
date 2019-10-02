package com.bruno.cursomc.services.exceptions;

public class AuthorizationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	//implementação padrão para tratar excessões
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause); 
	}
	
}
