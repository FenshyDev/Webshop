package domain;

import java.util.ArrayList;
import java.util.List;

public class Sales {
	List<Invoice> invoices;
	

	public Sales(){
		invoices = new ArrayList<Invoice>();
		
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void add(Invoice invoice){
		invoices.add(invoice);
	}
	public double getPrice(){
		double total = 0;
		for(Invoice invoice : invoices){
			total+= invoice.getPrice();
		}
		return total;
	}
	
	public void remove(Invoice invoice){
		int i = invoices.indexOf(invoice);
		if(i>=0){
			this.invoices.remove(i);
		}

	}
	
	public String toString()
	{
		String allInvoices = "";
		for(Invoice invoice: invoices)
			allInvoices = "Sale: " + invoice.toString();
		return allInvoices;
	}
	
}
