package common;

import java.io.Serializable;

/**
 * Class for saving orders report
 * @author Bashar Bashir
 * 
 */
public class OrdersReport implements Serializable{
	
	/**
	 * Restaurant name
	 */
	private String RestaurantName;
	/**
	 * Catalog from the menu
	 */
	private String Catalog;
	
	/**
	 * Sold quantity
	 */
	private int sold;
	
	/**
	 * @param restaurantName
	 * @param Catalog
	 * @param sold
	 * Constructor
	 */
	public OrdersReport(String restaurantName, String Catalog, int sold) {
		this.RestaurantName = restaurantName;
		this.Catalog = Catalog;
		this.sold = sold;
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
	 * Setter for the restaurant name
	 */
	public void setRestaurantName(String restaurantName) {
		RestaurantName = restaurantName;
	}
	
	/**
	 * @return Catalog
	 * Getter for the catalog
	 */
	public String getCatalog() {
		return Catalog;
	}
	
	/**
	 * @return Sold
	 * Getter for the Sold quantity
	 */
	public int getSold() {
		return sold;
	}
	/**
	 * @param sold
	 * Setter for the sold 
	 */
	public void setSold(int sold) {
		this.sold = sold;
	}
	/**
	 *Printing the class
	 *
	 */
	@Override
	public String toString() {
		return "OrdersReport [RestaurantName=" + RestaurantName + ", Catalog=" + Catalog + ", sold=" + sold + "]";
	}
	
}