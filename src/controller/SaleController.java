package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import database.FileProductDB;
import database.ProductDB;
import database.SqlProductDB;
import discount.CalculatePrice;
import discount.NoDiscountPromotion;
import discount.ProductFixedPromotion;
import discount.ProductPercentagePromotion;
import discount.SaleFixedPromotion;
import discount.SalePercentagePromotion;
import domain.DomainException;
import domain.Product;
import domain.Order;
import domain.Sales;
import domain.Invoice;
import view.CustomerView;
import view.CashierView;
/**
 * @author Ruben Schroyen
 */
public class SaleController {
	private Sales register;
	private CashierView mView;
	private CustomerView cView;
	private ProductDB db;
	private HashMap<String, String> promo = new HashMap<String, String>();
	private CalculatePrice calculator;
	
	public SaleController(Sales registeer, CustomerView c, CashierView m) throws DomainException, FileNotFoundException{
		this.fillPromotioncodes();
		this.register=registeer;
		this.mView= m;
		this.cView=c;
		String[] options = new String[]{"File database","Sql database"};
		 int response = JOptionPane.showOptionDialog(null, "Choose database:", "Title",
		JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);
		 if(response == 0){
			 db = new FileProductDB("producten.txt");
		 }else{
			 db = new SqlProductDB();
		 }
		 db.connectDB();

		 mView.addPromoListener(new PromotionListener());
		 mView.addPriceListener(new CalculateListener());
		 mView.getModel().addTableModelListener(new TableModelListener(){
		 
		 @Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if(e.getColumn() == 1)
				{
					int rij = e.getFirstRow();
					int quantity = Integer.parseInt((String) mView.getModel().getValueAt(e.getFirstRow(), e.getColumn()));
					if(quantity == 0)
					{
						register.getInvoices().get(register.getInvoices().size()-1).deleteProductOrder(e.getFirstRow());
						
					}else
					{
						register.getInvoices().get(register.getInvoices().size()-1).updateProductOrder(e.getFirstRow(), quantity);
					}
					mView.setPrice(register.getInvoices().get(register.getInvoices().size()-1).getPrice());
					cView.setPrice(register.getInvoices().get(register.getInvoices().size()-1).getPrice());
					
				}
			}
		 });
	}
	

		
		
	private void fillPromotioncodes() {
		//Entry: <Promocode, ID>
		promo.put("ProductDiscount", "123");
		promo.put("Product%Discount", "123");
		promo.put("TotalDiscount", "0");
		promo.put("Total%Discount", "0");
	}
	
	public Sales getRegister() {
		return register;
	}




	public void setRegister(Sales register) {
		this.register = register;
	}
	
	class PromotionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
					Invoice invoice = new Invoice();
					String id = mView.getId();
					int quantity = mView.getQuantity();
					Product p;
					try {
						p = db.getItem(id);
						String promocode = mView.getPromocode();
						if (promo.containsKey(promocode)){
							if (promo.get(promocode).equals(id) && promocode.equals("ProductDiscount")){
								calculator = new ProductFixedPromotion();
							}
							else if (promo.get(promocode).equals(id) && promocode.equals("Product%Discount")){
								calculator = new ProductPercentagePromotion();
							}
							else if (promo.get(promocode).equals("0") && promocode.equals("TotalDiscount")){
								calculator = new SaleFixedPromotion();
							}
							else if (promo.get(promocode).equals("0") && promocode.equals("Total%Discount")){
								calculator = new SalePercentagePromotion();
							}
							else 
							{
								calculator = new NoDiscountPromotion();
								mView.displayErrorMessage("Not a valid promotion code.");
							}
						}
							
						Order order = new Order(quantity,p, calculator);
						invoice.addProductOrder(order);
						register.add(invoice);
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (DomainException e1) {
						e1.printStackTrace();
					}
					
				
			}
		
	}
	
	class CalculateListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
				
				try{
					if(register.getInvoices().isEmpty())
					{
						Invoice sale = new Invoice();
						sale.registerObserver(mView);
						mView.setSubject(sale);
						register.add(sale);
					}
					
					String id = mView.getId();
					int quantity = mView.getQuantity();
					Product p;
					try {
						p = db.getItem(id);
						Order order = new Order(quantity,p, calculator);
						register.getInvoices().get(register.getInvoices().size()-1).addProductOrder(order);
						mView.setPrice(order.getPrice());
						cView.setPrice(order.getPrice());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					
				}

				catch(NumberFormatException ex){
					mView.displayErrorMessage("NO VALID INPUT.");
					
				} catch (DomainException de) {
					mView.displayErrorMessage(de.getMessage());
				} catch(IllegalArgumentException ie){
					mView.displayErrorMessage(ie.getMessage());
				}
				
			}
		
}
}
