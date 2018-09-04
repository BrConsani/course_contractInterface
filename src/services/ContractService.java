package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {
	
	OnlinePaymentService paymentService;
	
	public ContractService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void processContract(Contract contract, int months) {
		double basicPrice = contract.getTotalValue()/months;
		for(int i = 0; i < months; i++) {
			double interestPrice = basicPrice + paymentService.interest(basicPrice, i+1);
			double finalPrice = interestPrice + paymentService.paymentFee(interestPrice);
			Date newDate = addMonths(contract.getDate(), i+1);
			contract.addInstallment(new Installment(newDate, finalPrice));
		}
	}
	
	private Date addMonths(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime(); 
	}

}
