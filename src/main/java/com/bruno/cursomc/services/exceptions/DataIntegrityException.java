package com.bruno.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	//implementação padrão para tratar excessões
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause); 
	}
	
}
