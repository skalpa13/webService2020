package net.codejava;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	private static ProductDAO instance;
	private static int id=3;//2 produits sont deja insere dans la liste id= 1 et id =2 (ligne 13 & 14)
	
	private static List<Product> data = new ArrayList<>();
	
	static {
		data.add(new Product(1, "iPhone X", 999.99f));
		data.add(new Product(2, "XBOX 360", 329.50f));
	}
	
	private ProductDAO() {
		//pas d'instanciation possible ici de product 
	}
	
	public static ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO();
		}
		
		return instance;				
	}
	
	public List<Product> listAll() {
		return new ArrayList<Product>(data);
	}
	
	
	public int add(Product product) {
		//int newId = data.size() + 1; si supression d'une data dans la liste risque de doublon d'id  
		product.setId(id);
		id++;
		data.add(product);
		return id;
	}
	
	public Product get(int id) {
		Product productToFind = new Product(id);
		int index = data.indexOf(productToFind);
		if (index >= 0) {
			return data.get(index);
		}
		return null;
	}
	
	public boolean delete(int id) {
		Product productToFind = new Product(id);
		int index = data.indexOf(productToFind);
		if (index >= 0) {
			data.remove(index);
			return true;
		}
		
		return false;
	}
	
	public boolean update(Product product) {
		int index = data.indexOf(product);
		if (index >= 0) {
			data.set(index, product);
			return true;
		}
		return false;
	}
}
