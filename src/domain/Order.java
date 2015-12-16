package domain;

import discount.CalculatePrice;
/**
 * @author Ruben Schroyen
 */
public class Order {
	int quantity;
	private Product product;
	private CalculatePrice strategy;
	
	public Order(int quantity, Product product, CalculatePrice calculator){
		setQuantity(quantity);
		setProduct(product);
		setStrategy(calculator);
	}

	private void setStrategy(CalculatePrice calculator) {
		this.strategy = calculator;
	}
	
	public CalculatePrice getStrategy(){
		return this.strategy;
	}

	private void setProduct(Product product) {
		this.product=product;
	}

	protected void setQuantity(int quantity) {
		if(quantity < 1){
			throw new IllegalArgumentException("Quantity must be higher than 0.");
		}
		this.quantity=quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}
	
	public double getPrice(){
		return product.getPrice()*this.getQuantity();
	}
	
	public String toString()
	{
		return "Id: " + product.getId() + " Title: " + product.getTitle() + " Quantity: " + this.getQuantity() + " Price: " + this.getPrice();
	}
}
