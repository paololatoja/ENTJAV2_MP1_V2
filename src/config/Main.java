package config;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Product;

public class Main {
	
	/* PRODUCT LIST */
	private static ArrayList<Product> products = new ArrayList<Product>();

	public void readFile(String xml_file_path) {
		
		try {
			File productXML = new File(xml_file_path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(productXML);
			
			doc.getDocumentElement().normalize();		
			
			// [1] get all "item" elements (get all products/items in the xml file)
			NodeList nList = doc.getElementsByTagName("item");
			Node node;
			Product p = null;
	
			// [2] iterate through each item and get data
			for (int i = 0; i < nList.getLength(); i++) {
				node = nList.item(i);		
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element e = (Element) node;
					
					p = new Product(
						e.getElementsByTagName("id").item(0).getTextContent(), 							/* ID */
						e.getElementsByTagName("name").item(0).getTextContent(), 						/* NAME */
						e.getElementsByTagName("image-name").item(0).getTextContent(), 					/* IMAGE NAME */
						Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent()), 	/* PRICE */
						Integer.parseInt(e.getElementsByTagName("stocks").item(0).getTextContent()),	/* STOCKS */
						Integer.parseInt(e.getElementsByTagName("rating").item(0).getTextContent()) 	/* RATING */
					);
					
					// [3] Add item to array list
					products.add(p);
				}
			}
					
		} catch (Exception e) {
			e.printStackTrace(); /* FILE NOT FOUND */
		}
	}
	
	public ArrayList<Product> getProductList() {
		return products;
	}

}
