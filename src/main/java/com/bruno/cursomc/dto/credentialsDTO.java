//Objeto para receber a requisição de email e senha
package com.bruno.cursomc.dto;

import java.io.Serializable;

public class credentialsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	public credentialsDTO() {
		
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
