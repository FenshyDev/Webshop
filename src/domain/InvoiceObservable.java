package domain;

/**
 * @author Ruben Schroyen
 */
public interface InvoiceObservable {
	void registerObserver(InvoiceObserver obs);
	void notifyObservers();
	void removeObserver(InvoiceObserver obs);
	Object getUpdate(InvoiceObserver obs);
	
}
