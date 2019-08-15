package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order implements FireshipOnlineOrdering {
	private User user;
	public static ArrayList<Product> cart;
	private float vatRate;
	private double VAT;
	private boolean creditCardStatus = false;
	private String dateOrdered;
	public String creditCard;
	
	// Computed Fields
	private double grossPrice; 		// (SUBTOTAL)
	private double netPrice;		// (SUBTOTAL + VAT_PRICE)
	
	
	
	// Def. Constructor
	public Order() {
		cart = new ArrayList<Product>();
	}
	
	
	public Order(String cardNum) {
		creditCard = cardNum;
	}
	
	
	
	/* -------------------------------------------- INTERFACE METHODS -------------------------------------------- */
	@Override
	public void validateCreditCard() {
		if(creditCard.length() == 16) {
			int s1 = 0, s2 = 0;
	        String reverse = new StringBuffer(creditCard).reverse().toString();
	        
	        for(int i = 0 ;i < reverse.length();i++) {
	            int digit = Character.digit(reverse.charAt(i), 10);
	            
	            if(i % 2 == 0) {	//this is for odd digits, they are 1-indexed in the algorithm
	                s1 += digit;
	                
	            } else {	
	                s2 += 2 * digit;	//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
	                
	                if(digit >= 5){
	                    s2 -= 9;
	                }
	            }
	        }
	        if ((s1 + s2) % 10 == 0) {
	        	creditCardStatus = true; // s1 + s2 = 100
	        } else {
	        	System.err.println("cardStatus = FALSE");  
	        	System.err.println("NOT VALID CARD NUMBER"); //replace with User-Defined Exception
	        	// sendRedirect/Forward
	        }
		} else {
			System.err.println("INVALID CARD LENGTH"); //replace with User-Defined Exception
			System.err.println("CreditCard Length != 16"); //replace with User-Defined Exception
			// sendRedirect/Forward
		}
	}
	@Override
	public void computeGrossPay() {
		for(Product item : cart) {
			grossPrice += (item.getPrice() * item.getQuantity());
		}
	}
	@Override
	public void computeVAT() {
		VAT = grossPrice * vatRate;
	}
	@Override
	public void generatePDFReceipt() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void netPay() {
		netPrice = grossPrice + VAT;
	}
	@Override
	public void printPDFSalesReport() {
		
	}
	/* ---------------------------------------------------------------------------------------------------------- */
	
	
	
	
	
	

	// GET CART ARRAY LIST
	public static ArrayList<Product> getCart() {
		return cart;
	}
	
	// setter for 'vatRate'
	public void setVatRate(float vatRate) {
		this.vatRate = vatRate;
	}
	
	
	
	public String getSubtotal() {
		return String.format("%,.2f", grossPrice);
	}
	public String getVAT() {
		return String.format("%,.2f", VAT);
	}
	public String getTotal() {
		return String.format("%,.2f", netPrice);
	}
	
	
	
	// CART OPERATIONS (ADD & REMOVE ITEMS) ----------------------------------------------------------------------------------------------------------------------- >
	public boolean removeItemFromCart(Product item) {		
		if(cart.remove(item)) {
			System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) Successfully REMOVED an item from the cart!");
			updatePrice();
			return true;
		}
		
		return false;
	}
	
	public boolean addItemToCart(Product item) {
		if(cart.add(item)) {
			System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) Successfully added an item added the cart!");
			System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) Shopping Cart UPDATED!");
			updatePrice();
			return true; //success
		}
		
		return false; //failed
	}
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------ >
	
	
	
	
	
	
	
	private void setOrderDate(Date date) {
		this.dateOrdered = new SimpleDateFormat().format(new Date());
	}
	
	private void updatePrice() {
		//reset
		grossPrice = 0;
		VAT = 0;
		computeGrossPay();
		computeVAT();
		netPay();
	}
	
	// TRIGGERED WHEN PAY BUTTON IS CLICKED ----------------------------------------------------------------------------------------------------------------------- >
	public boolean processOrder() {
		boolean orderStatus = false;
		// set user details and credit card
		
		// get cart items  --> saved in session/cookies?
		
		
		// validate Order quantity, if valid ---> validate creditCard
		validateCreditCard();
		if (creditCardStatus) {
			// set date Ordered --> PDF receipt
			// orderStatus = true;
		}		
 
		return orderStatus;
	}
}
