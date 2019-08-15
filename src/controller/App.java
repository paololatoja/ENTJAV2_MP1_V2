package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.Main;
import model.Order;
import model.Product;

public class App extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Declarations
	public static ArrayList<Product> products;
	public static Order order;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		Main app = new Main();
		app.readFile(config.getInitParameter("XML_FILE_PATH"));
		products = app.getProductList();
		
		// Initialize/Prepare order object & Initialize Cart
		order = new Order();
		String vatRate = config.getInitParameter("VAT_RATE");
		order.setVatRate(Float.parseFloat(vatRate) / 100);
		 
		// Bind the products and Shopping Cart to application
		ServletContext sc = getServletContext();
		sc.setAttribute("INITIAL", products);
		sc.setAttribute("ORDER", order);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
