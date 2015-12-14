package database;



import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import domain.DomainException;
import domain.Product;

public interface ProductDB {
	void connectDB() throws FileNotFoundException;
	void delete(String id) throws DomainException, SQLException;
	void add(Product item) throws DomainException, SQLException;
	Product getItem(String id) throws DomainException, SQLException;
	void update(Product item) throws SQLException, DomainException;
	void close() throws DomainException;
	List<Product> getAll() throws DomainException, SQLException;
}
