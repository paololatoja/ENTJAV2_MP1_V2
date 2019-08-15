package model;

public class User {
	private String firstName;
	private String lastName;
	private String creditCardNumber;
	private String shippingAddress;
	
	public User(String firstName, String lastName, String creditCardNumber, String shippingAddress) {
		this.firstName 			= firstName;
		this.lastName 			= lastName;
		this.creditCardNumber 	= creditCardNumber;
		this.shippingAddress 	= shippingAddress;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	
	
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	
	
	
	
}
