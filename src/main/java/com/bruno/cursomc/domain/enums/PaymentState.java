//UM TIPO DA CLASSE Payment. TIPO ENUM
package com.bruno.cursomc.domain.enums;

public enum PaymentState {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String description;
	
	private PaymentState(int cod, String description) {
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
	
	public static PaymentState toEnum(Integer cod) {
		

		if (cod == null) {
			return null;
		}
		
		//percorre todos os valores possíveis do tipo enumerado Client
		for (PaymentState x : PaymentState.values()) {
			
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " +cod);
	}	
}
