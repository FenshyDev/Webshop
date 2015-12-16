package states;

import domain.Invoice;
import domain.Order;

public class Cancelled implements State{
private Invoice invoice;
	
	public Cancelled(Invoice invoice) {
		this.invoice = invoice;
		for (int i=0; i < invoice.getProductOrders().size()-1; i++)
			this.delete(i);
	}
	
	public void delete(int i) {
		invoice.deleteProductOrder(i);
	}

	public void add(Order order) {
		invoice.addProductOrder(order);
	}

	public void cancel() {
		invoice.setState(this);
	}

	public void pay(double amountPaid) {
	}
}
