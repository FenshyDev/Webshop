package domain;

import java.util.Observable;
/**
 * @author Ruben Schroyen
 */
public interface InvoiceObserver {
	void update();
	void setSubject(InvoiceObservable  observable );
}
