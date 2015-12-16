package controller;

import java.util.Properties;
/**
 * @author Wannes Fransen
 */
public class Dictionary {
	Properties properties;
	
	public void setProperties() {
		properties = new Properties();
		properties.setProperty("url", "jdbc:postgresql://gegevensbanken.khleuven.be:51516/2TX34");
		properties.setProperty("user", "r0582534");
		properties
				.setProperty("password", "Keyblade66");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory",
				"org.postgresql.ssl.NonValidatingFactory");
		System.out.println("setting properties complete");
	}
	
	public Properties getProperties(){
		setProperties();
		return properties;
	}
}
