package view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * @author Ruben Schroyen
 */
public class CustomerView extends JFrame{

	
	private static final long serialVersionUID = 1L;
	private JTextField price = new JTextField(5);
	private JLabel pay = new JLabel("To pay: ");
	
	public CustomerView(){
		JPanel panel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200, 75);
		price.setEditable(false);
		panel.add(pay);
		panel.add(price);
		this.add(panel);
	}
		public double getPrice(){
			return Double.parseDouble(price.getText());
		}
		public void setPrice(double dble ){
			price.setText(Double.toString(dble));
		}
		public void addPriceListener(ActionListener listenForAddButton){
			price.addActionListener(listenForAddButton);
		}
}
