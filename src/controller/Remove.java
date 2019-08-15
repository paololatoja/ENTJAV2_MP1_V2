package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;
import model.Product;

public class Remove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int index = Integer.parseInt(request.getParameter("item_index"));
		System.out.println(index);
		
		Order order				= (Order) getServletContext().getAttribute("ORDER");
		ArrayList<Product> cart = Order.getCart();
		
		order.removeItemFromCart(cart.get(index));
		
		response.sendRedirect("cart.jsp");
		
	}

}
