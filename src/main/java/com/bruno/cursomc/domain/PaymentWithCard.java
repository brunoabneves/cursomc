//Pagamento com cartão
package com.bruno.cursomc.domain;

import javax.persistence.Entity;

import com.bruno.cursomc.domain.enums.PaymentState;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("paymentWithCard")
public class PaymentWithCard extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberOfInstallments; //número de parcelas
	
	public PaymentWithCard() {
		
	}

	public PaymentWithCard(Integer id, PaymentState state, Request request, Integer numberOfInstallments) {
		super(id, state, request);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
	
}
