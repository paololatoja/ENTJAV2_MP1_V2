package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;

import exceptions.*;

public class Order implements FireshipOnlineOrdering {
	private User user;
	public static ArrayList<Product> cart;
	private float vatRate;
	private double VAT;
	
	private String dateOrdered;  //PDF Date receipt
	public String creditCard;
	public String userFullName;
	public String shippingAddress;
	
	// Validation
	private boolean creditCardStatus = false;
	public boolean fullyValidated = false;
	
	// Computed Fields
	private double grossPrice; 		// (SUBTOTAL)
	private double netPrice;		// (SUBTOTAL + VAT_PRICE)
	
	
	
	// Def. Constructor
	public Order() {
		
	}
	
	
	
	
	/* -------------------------------------------- INTERFACE METHODS -------------------------------------------- */
	@Override
	public void validateCreditCard() {
		
		try {
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
//		        	System.err.println("cardStatus = FALSE");  
		        	throw new InvalidCardNumberException(); 
		        	// sendRedirect/Forward
		        } 
			} else {
//				System.err.println("INVALID CARD LENGTH");
				throw new InvalidCardLengthException(); //CreditCard Length != 16
				// sendRedirect/Forward
			}
		} catch (InvalidCardNumberException icne) {
			System.err.println(icne.getMessage());
		} catch (InvalidCardLengthException icle) {
			System.err.println(icle.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception error printStackTrace: " + e.getMessage());
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
		// User Details + Order Summary + Recorded Ordered items + Shop Details
		
	}
	@Override
	public void netPay() {
		netPrice = grossPrice + VAT;
	}
	@Override
	public void printPDFSalesReport() {
		// generate PDF in directory
	}
	/* ---------------------------------------------------------------------------------------------------------- */
	
	
	
	// FULL ORDER VALIDATION
	public void fullyValidated() {
		
		try {
			// quantity item must be valid, must not be zero or negative
			if (quantityItem == valid && quantityItem >= 0) {  // read each cart item's quantity value and compare?
				validateCreditCard();
				
				if (creditCardStatus) {
					fullyValidated = true;
				}
				
			} else {
				// order quantity ERROR  --> User Defined Exception
				throw new InvalidQuantityItemException();
				// Redirect
			}
		} catch (InvalidQuantityItemException iqie) {
			System.err.println(iqie.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception printStackTrace: " + e.getMessage());
		}
	}
	
	

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
	public boolean processOrder(User user) {
		boolean orderStatus = false;
		// get user details and credit card
		userFullName = user.getFirstName() + " " + user.getLastName();
		creditCard = user.getCreditCardNumber();
		shippingAddress = user.getShippingAddress();
		
		
		// get cart items  --> servletContext persistence, used for pdf
//		PDF pdf = new PDF(getCart(), userDetails, shopDetails);
//		getCart()
		
		// validate Order quantity, if valid ---> validate creditCard
		fullyValidated();  // ---> Create full validation
		if (fullyValidated) {
			// set date Ordered --> PDF receipt
			// orderStatus = true;
		}		
 
		return orderStatus;
	}
}
