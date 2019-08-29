//UM TIPO DA CLASSE Client. TIPO ENUM	
//Salva um inteiro internamente mas externamente salva como um ClientType
package com.bruno.cursomc.domain.enums;

public enum ClientType {

	PHYSICALPERSON(1, "Pessoa Física"),
	LEGALPERSON(2, "Pessoa Jurídica");
	
	private int cod;
	private String description;
	
	private ClientType(int cod, String description) {
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
	
	public static ClientType toEnum(Integer cod) {
		

		if (cod == null) {
			return null;
		}
		
		//percorre todos os valores possíveis do tipo enumerado Client
		for (ClientType x : ClientType.values()) {
			
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " +cod);
	}
	
}
