package database;


/**
 * @author Ruben Schroyen
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import domain.DomainException;
import domain.Product;


public class FileProductDB implements ProductDB {
	
	private Map<String, Product> items;
	private String file;
	
	public FileProductDB(String file) throws DomainException{
		items = new HashMap<String, Product>();
		setFile(file);
		try {
			connectDB();
		} catch (FileNotFoundException f) {
			throw new DomainException("File not found!");
		}
	}
	
	public void setFile(String file) {
		if (file == null){
			throw new NullPointerException("Nullpointer setFile FileProductDB ...");
		}
		this.file = file;
	}
	
	public void delete(String id) {
		items.remove(id);
	}

	public void add(Product item) throws DomainException {
		if(this.items.containsKey(item.getId())){
			throw new DomainException("There is already an item with this id..." + item.getId());
		}
		items.put(item.getId(),item);
	}

	public Product getItem(String id) throws DomainException {
		if(!this.items.containsKey(id)){
			throw new DomainException("There is no item with this id...");
		}
		Product item=items.get(id);
		return item;				
	}

	public void update(Product item) {
		items.put(item.getId(),item);
	}

	public List<Product> getAll() {
		return new ArrayList<Product>(items.values());
	}
	
	public void close() throws DomainException{		
		try {
			File doc = new File(file);
			PrintWriter printer = new PrintWriter(doc);
			List<Product> items = this.getAll();
			for(Product item: items)
			 {
				printer.println(item.toString());
			}
			printer.close();
		}
		catch (FileNotFoundException ex) {
			throw new DomainException("File not found");
		}
	}

	public void connectDB() throws FileNotFoundException {
			File doc = new File(file);
			Scanner scanner = new Scanner(doc);
			while (scanner.hasNext()) {
				String lijn = scanner.nextLine();
				if (!lijn.equals("")) {
					String [] fields = lijn.split("\t");

					String id = fields[0];
					String title = fields[1];		
					String priceStr= fields[2];
					double price= Double.parseDouble(priceStr);
					Product item= new Product(title,id,price);
					items.put(item.getId(), item);
				}
			}
			if (scanner!=null){
				scanner.close();
			}				
	}
	
	public HashSet<String> getIds(){
		HashSet<String> hashSet = new HashSet<String>();
		Set<String> set = this.items.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
		    hashSet.add(iter.next());
		}
		return hashSet;		
	}
	

}
