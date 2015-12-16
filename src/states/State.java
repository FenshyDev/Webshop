package states;

import domain.Order;

public interface State {
	
	public void delete(int i);
	public void add(Order order);
	public void cancel();
	public void pay(double amountPaid);
}