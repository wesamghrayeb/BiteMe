package common;

import java.io.Serializable;

/**
 * InCome class for saving every restaurant InCome
 * @author Bashar Bashir
 * 
 */
public class InComeReport implements Serializable{
	
	/**
	 * Restaurant name
	 */
	private String RestaurantName;
	/**
	 * Variable for saving the InCome
	 */
	private double Income;
	
	/**
	 * Days in month
	 */
	private int Day;
	 
	/**
	 * @param restaurantName
	 * @param income
	 * @param Day
	 * Constructor for the class
	 */
	public InComeReport(String restaurantName,double income,int Day) {
		this.RestaurantName = restaurantName;
		this.Income=income;
		this.Day=Day;
	}

	/**
	 * @return Restaurant name
	 * Getter for the restaurant name
	 */
	public String getRestaurantName() {
		return RestaurantName;
	}

	/**
	 * @param restaurantName
	 * Setter for the Restaurant Name 
	 */
	public void setRestaurantName(String restaurantName) {
		RestaurantName = restaurantName;
	}

	/**
	 * @return InCome
	 * Getter for the InCome
	 */
	public double getIncome() {
		return Income;
	}

	/**
	 * @param income
	 * Setter for the InCome
	 */
	public void setIncome(double income) {
		Income = income;
	}

	/**
	 * @return Day
	 * Getter for the Day
	 */
	public int getDay() {
		return Day;
	}

	/**
	 * @param day
	 * Setter for the Day
	 */
	public void setDay(int day) {
		Day = day;
	}

	/**
	 * Printing the class information
	 */
	@Override
	public String toString() {
		return "InComeReport [RestaurantName=" + RestaurantName + ", Income=" + Income + ", Day=" + Day + "]";
	}

	
	
	

}