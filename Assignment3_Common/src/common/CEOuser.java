package common;

import java.io.Serializable;

/**
 * Class for CEO user
 * @author Bashar Bashir
 */
public class CEOuser implements Serializable{ 
	
	
	/**
	 * CEO ID
	 */
	private String ID;
	/**
	 * CEO First Name
	 */
	private String FirstName;
	
	/**
	 * CEO last name
	 */
	private String LastName;
	
	/**
	 * CEO Phone number
	 */
	private String PhoneNumber;
	
	/**
	 * CEO Email
	 */
	private String Email;
	
	/**
	 * @param iD
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param email
	 * CEO user constructor
	 */
	public CEOuser(String iD, String firstName, String lastName, String phoneNumber, String email) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
	}
	
	/**
	 * @return ID
	 * Getter ID
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Setter iD
	 * @param iD
	 * 
	 */
	public void setID(String iD) {
		ID = iD;
	}
	
	/**
	 * @return FirstName
	 * Getter for First Name
	 */
	public String getFirstName() {
		return FirstName;
	}
	
	/**
	 * @param firstName
	 * Setter for First Name 
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	
	/**
	 * @return LastName
	 * Setter for Last Name
	 * 
	 */
	public String getLastName() {
		return LastName;
	} 
	
	/**
	 * @param lastName
	 * Setter for Last Name
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	} 
	
	/**
	 * @return PhoneNumber
	 * Getter for Phone number
	 */
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	
	/**
	 * @param phoneNumber
	 * Setter for Phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	
	/**
	 * @return Email
	 * Getter for email
	 */
	public String getEmail() {
		return Email;
	} 
	
	/**
	 * @param email
	 * Setter for Email
	 */
	public void setEmail(String email) {
		Email = email;
	}
	
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "CEOuser [ID=" + ID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", PhoneNumber="
				+ PhoneNumber + ", Email=" + Email + "]";
	}
	
}