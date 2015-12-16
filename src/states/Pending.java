package states;
/**
 * @author Ruben Schroyen
 */
import domain.Invoice;
import domain.Order;

public class Pending implements State{
private Invoice invoice;
	
	public Pending(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public void delete(int i) {
		invoice.deleteProductOrder(i);
	}

	public void add(Order order) {
		invoice.addProductOrder(order);
	}

	public void cancel() {
		invoice.setState(new Cancelled(invoice));
	}

	public void pay(double amountPaid) {
		if (amountPaid >= invoice.getPrice())	
			invoice.setState(new Paid(invoice));
	}
}
