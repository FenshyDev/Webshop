package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import controller.Dictionary;
import domain.DomainException;
import domain.Product;
/**
 * @author Ruben Schroyen, Wannes Fransen
 */
public class SqlProductDB implements ProductDB {

	private PreparedStatement statement;
	private Connection connection;
	ResultSet result;
	private Properties properties;
	Dictionary dict;
	
	public SqlProductDB() {
		dict = new Dictionary();
		this.properties = dict.getProperties();
	}


	@Override
	public void connectDB() throws FileNotFoundException {
		try {
			Class.forName("org.postgresql.Driver");
			this.setProperties(properties);

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void delete(String id) throws DomainException, SQLException {
		try {
			String url = properties.getProperty("url");

			connection = DriverManager.getConnection(url, properties);

			String sql = "delete from r0582534.product where productid = ?";

			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, id);
				result = statement.executeQuery();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}} finally{}
	}

	@Override
	public void add(Product item) throws DomainException, SQLException {
		try {
			String url = properties.getProperty("url");

			connection = DriverManager.getConnection(url, properties);

			String sql = "insert into r0582534.product values (? , ? , ?)";

			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, item.getId());
				statement.setString(2, item.getTitle());
				statement.setString(3, ""+item.getPrice());
				result = statement.executeQuery();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}} finally{}
	}

	@Override
	public Product getItem(String id) throws DomainException, SQLException {
		Product product = null;
		try {
			String url = properties.getProperty("url");

			connection = DriverManager.getConnection(url, properties);

			String sql = "Select * from r0582534.product where productid = ?";

			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, id);
				result = statement.executeQuery();

				while (result.next()) {
					String productid = result.getString("productid");
					String title = result.getString("title");
					double price = result.getDouble("price");
					product = new Product(productid,title,price);
					return product;

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}} finally{}
		return product;
	}

	@Override
	public void update(Product item) throws SQLException, DomainException {
		try {
			String url = properties.getProperty("url");

			connection = DriverManager.getConnection(url, properties);

			String sql = "update r0582534.product set productid = ? , title = ? , price = ?";

			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, item.getId());
				statement.setString(2, item.getTitle());
				statement.setString(3, ""+item.getPrice());
				result = statement.executeQuery();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}} finally{}

	}

	@Override
	public void close() throws DomainException {
		try {
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Product> getAll() throws DomainException, SQLException {
		List<Product> resultaat = new ArrayList<Product>();
		try {
			String url = properties.getProperty("url");

			connection = DriverManager.getConnection(url, properties);

			String sql = "Select * from r0582534.product";

			try {
				statement = connection.prepareStatement(sql);
				result = statement.executeQuery();

				while (result.next()) {
					String productid = result.getString("id");
					String title = result.getString("title");
					double price = result.getDouble("price");
					resultaat.add(new Product(productid,title,price));
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}} finally{
			}
		return resultaat;
	}

	private void setProperties(Properties properties) {
		this.properties = properties;
	}
}
