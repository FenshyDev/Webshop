package domain;

import java.util.Observable;

public interface InvoiceObserver {
	void update();
	void setSubject(InvoiceObservable  observable );
}
