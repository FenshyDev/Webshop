package domain;
public class Product {
	/**
	 * @author Ruben Schroyen
	 */
	private String title;
	private String id;
	private double price;
	
	public Product(String title, String id, double price) throws IllegalArgumentException{
		setTitle(title);
		setId(id);
		setPrice(price);
	}
	
	private void setPrice(double price) {
		this.price=price;
	}
	public double getPrice(){
		return this.price;
	}

	public String getTitle() 
	{
		return title;
	}
	

	protected void setTitle(String title) 
	{
		if(title == null || title.equals("") || title.trim().equals("")){
			throw new IllegalArgumentException("Title cannot be empty.");
		}
		this.title = title;
	}


	public String getId() {
		return id;
	}


	protected void setId(String id) 
	{
		if(id == null || id.equals("") || id.trim().equals("")){
			throw new IllegalArgumentException("Id cannot be empty.");
		}
		this.id = id;
	}
	
	public String toString() {
        return this.id + ":" + this.title;
    }
}
