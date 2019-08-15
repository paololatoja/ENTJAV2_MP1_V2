package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;
import model.User;

public class ProcessOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName;
		String lastName;
		String creditCardNumber;
		String shippingAddress;
		boolean ProcessOrderStatus;
		
		firstName = request.getParameter("user_firstname").trim();
		lastName = request.getParameter("user_lastname").trim();
		shippingAddress = request.getParameter("shipping_address").trim();
		creditCardNumber = request.getParameter("creditCard").trim();
		
		User user = new User(firstName, lastName, creditCardNumber, shippingAddress);
		
		ProcessOrderStatus = new Order().processOrder(user);
		System.out.println("ORDER BEING PROCESSED....");
		
		if(ProcessOrderStatus) {
//			System.out.println("\nSUCCESS!");
			// forward success.jsp
		} else {
			System.err.println("Order Failed");
			// redirect invalid.jsp
		}
	}

}
