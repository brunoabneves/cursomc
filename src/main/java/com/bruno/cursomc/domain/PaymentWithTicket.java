//Pagamento com boleto
package com.bruno.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.bruno.cursomc.domain.enums.PaymentState;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PaymentWithTicket extends Payment{
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;  	  //data de vencimento
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date datePayment; //data de pagamento
	
	public PaymentWithTicket() {
		
	}

	public PaymentWithTicket(Integer id, PaymentState state, Request request, Date dueDate, Date datePayment) {
		super(id, state, request);
		this.dueDate = dueDate;
		this.datePayment = datePayment;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}
	
}
