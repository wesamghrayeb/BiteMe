package common;

import java.io.Serializable;

/**
 * @author Bashar Bashir
 *
 */
public class HistogramCEO implements Serializable{
 
	/**
	 * Restaurant Name 
	 */
	private String RestaurantName;
	
	/**
	 * Monthly InCome
	 */
	private double InCome;
	
	/**
	 * quantity Sold items 
	 */
	private int sold;
	
	/**
	 * @param restaurantName
	 * @param inCome
	 * @param sold
	 * Constructor for HistogramCEO class 
	 */
	public HistogramCEO(String restaurantName, double inCome, int sold) {
		RestaurantName = restaurantName;
		InCome = inCome;
		this.sold = sold;
	}
	/**
	 * @return Restaurant Name
	 * Getter for restaurant name
	 */
	public String getRestaurantName() {
		return RestaurantName;
	}
	
	/**
	 * @param restaurantName
	 * Setter for Restaurant name
	 */
	public void setRestaurantName(String restaurantName) {
		RestaurantName = restaurantName;
	}
	
	/**
	 * @return InCome
	 * Getter for InCome
	 */
	public double getInCome() {
		return InCome;
	}
	
	/**
	 * @param inCome
	 * Setter for InCome
	 */
	public void setInCome(double inCome) {
		InCome = inCome;
	}
	
	/**
	 * @return Sold
	 * Getter for sold
	 */
	public int getSold() {
		return sold;
	}
	
	/**
	 * @param sold
	 * setter for sold
	 */
	public void setSold(int sold) {
		this.sold = sold;
	}
	
	/**
	 * Printing the class information
	 */
	@Override
	public String toString() {
		return "HistogramCEO [RestaurantName=" + RestaurantName + ", InCome=" + InCome + ", sold=" + sold + "]";
	}
	
	
}