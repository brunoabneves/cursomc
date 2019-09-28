//UM TIPO DA CLASSE Client. TIPO ENUM	
//Salva um inteiro internamente mas externamente salva como um ClientType
package com.bruno.cursomc.domain.enums;

public enum Profile {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENT(2, "ROLE_CLIENT");
	
	private int cod;
	private String description;
	
	private Profile(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	//Somente o get, já que uma vez instanciado não se pode mudar o valor dos atributos
	public int getCod() {
		return cod;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Profile toEnum(Integer cod) {
		

		if (cod == null) {
			return null;
		}
		
		//percorre todos os valores possíveis do tipo enumerado Client
		for (Profile x : Profile.values()) {
			
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " +cod);
	}
	
}
