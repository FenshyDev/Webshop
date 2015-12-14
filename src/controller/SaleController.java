package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import database.FileProductDB;
import database.ProductDB;
import database.SqlProductDB;
import domain.DomainException;
import domain.Product;
import domain.Order;
import domain.Sales;
import domain.Invoice;
import view.CustomerView;
import view.CashierView;

public class SaleController {
	private Sales register;
	private CashierView mView;
	private CustomerView cView;
	private ProductDB db;
	
	public SaleController(Sales registeer, CustomerView c, CashierView m) throws DomainException, FileNotFoundException{
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
	

		
		
	public Sales getRegister() {
		return register;
	}




	public void setRegister(Sales register) {
		this.register = register;
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
						Order order = new Order(quantity,p);
						
						register.getInvoices().get(register.getInvoices().size()-1).addProductOrder(order);
						mView.setPrice(register.getInvoices().get(register.getInvoices().size()-1).getPrice());
						cView.setPrice(register.getInvoices().get(register.getInvoices().size()-1).getPrice());
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
