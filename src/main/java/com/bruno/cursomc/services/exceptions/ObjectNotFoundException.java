package com.bruno.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	//implementação padrão para tratar excessões
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause); 
	}
	
}
