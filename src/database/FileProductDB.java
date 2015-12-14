package database;





import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import domain.DomainException;
import domain.Product;


public class FileProductDB implements ProductDB {
	
	private Map<String, Product> items;
	private String bestand;
	
	public FileProductDB(String bestand) throws DomainException{
		items = new HashMap<String, Product>();
		setBestand(bestand);
		try {
			connectDB();
		} catch (FileNotFoundException f) {
			throw new DomainException("Bestand werd niet gevonden!");
		}
	}
	
	public void setBestand(String bestand) {
		if (bestand == null){
			throw new NullPointerException("Nullpointer setBestand FileProductDB ...");
		}
		this.bestand = bestand;
	}
	
	public void delete(String id) {
		items.remove(id);
	}

	public void add(Product item) throws DomainException {
		if(this.items.containsKey(item.getId())){
			throw new DomainException("Er is al een item met deze id..." + item.getId());
		}
		items.put(item.getId(),item);
	}

	public Product getItem(String id) throws DomainException {
		if(!this.items.containsKey(id)){
			throw new DomainException("Er is geen item met deze id...");
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
			File file = new File(bestand);
			PrintWriter printer = new PrintWriter(file);
			List<Product> items = this.getAll();
			for(Product item: items)
			 {
				printer.println(item.toString());
			}
			printer.close();
		}
		catch (FileNotFoundException ex) {
			throw new DomainException("Bestand werd niet gevonden");
		}
	}

	public void connectDB() throws FileNotFoundException {
			File file = new File(bestand);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String lijn = scanner.nextLine();
				if (!lijn.equals("")) {
					String [] velden = lijn.split("\t");

					String id = velden[0];
					String title = velden[1];		
					String priceStr= velden[2];
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
