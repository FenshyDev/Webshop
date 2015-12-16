package domain;


public class DomainException extends Exception {
	/**
	 * @author Ruben Schroyen
	 */
	private static final long serialVersionUID = 1L;
	public DomainException(){
		super();
	}
	public DomainException(String msg){
		super(msg);
	}
}
