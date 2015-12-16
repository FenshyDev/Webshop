package ui;

import java.io.FileNotFoundException;

import controller.SaleController;
import domain.DomainException;
import domain.Sales;
import view.CustomerView;
import view.CashierView;
/**
 * @author Ruben Schroyen
 */
public class UI {
	public static void main(String[] args) throws FileNotFoundException, DomainException {
		CashierView mView = new CashierView();
		CustomerView cView = new CustomerView();
		Sales theModel = new Sales();
		
		
		SaleController control = new SaleController(theModel, cView, mView);
		mView.setVisible(true);
		cView.setVisible(true);
	}

}
