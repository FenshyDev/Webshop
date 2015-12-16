package domain;

import java.util.ArrayList;
import java.util.List;

import discount.CalculatePrice;
import domain.Order;
public class Invoice implements InvoiceObservable{
	private List<Order>productOrders;
	CalculatePrice calculator;
	
	
	private List<InvoiceObserver> observers;
	public Invoice(){
		this.productOrders = new ArrayList<Order>();
		observers= new ArrayList<InvoiceObserver>();
	}
	
	public void addProductOrder(Order order){
		boolean bl= false;
		for(Order prod: productOrders){
			if (prod.getProduct().getId().equals(order.getProduct().getId())){
				prod.setQuantity(order.getQuantity()+prod.getQuantity());
				bl= true;
			}
		}if(!bl){
			productOrders.add(order);
		}
		notifyObservers();
	}
	public List<Order> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(List<Order> productOrders) {
		this.productOrders = productOrders;
	}
	
	public void deleteProductOrder(int i)
	{
		productOrders.remove(i);
		notifyObservers();
	}
	
	public void updateProductOrder(int i, int quantity)
	{
		productOrders.get(i).setQuantity(quantity);
		notifyObservers();
	}

	public double getPrice(){
		double total = 0;
		for(Order order : productOrders){
			total+= order.getStrategy().calculateTotal(productOrders, order.getProduct().getId());
		}
		return total;
	}
	public boolean exists(Order order){
		for(Order prod: productOrders){
			if (prod.getProduct().getId().equals(order.getProduct().getId())){
				return true;
			}
		}
		return false;
	}
	@Override
	public void registerObserver(InvoiceObserver obs) {
		if(obs == null) throw new NullPointerException();
		if(!observers.contains(obs)){
			this.observers.add(obs);
		}
		
	}

	@Override
	public void removeObserver(InvoiceObserver obs) {
		int i = observers.indexOf(obs);
		if(i>=0){
			this.observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		for(InvoiceObserver observer : observers){
			observer.update();
		}
	}
	public Object getUpdate(InvoiceObserver obj){
		return this;
	}
	
	public String toString()
	{
		String alles = "";
		for(Order order : productOrders)
			alles += "ProductOrder: " + order.toString();
		return alles;
	}
	
	
	
	

	
}
