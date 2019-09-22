package com.bruno.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.PaymentWithTicket;

@Service
public class TicketService {
	
	public void fillPaymentWithTicket(PaymentWithTicket payment, Date instanteOfRequest) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteOfRequest);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		payment.setDueDate(cal.getTime());
	}

}
