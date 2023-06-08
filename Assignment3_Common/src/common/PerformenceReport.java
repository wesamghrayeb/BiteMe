package common;

import java.io.Serializable;
import java.sql.Time;

/**
 * class for Creating Performance report 
 * @author Bashar Bashir
 * 
 */
public class PerformenceReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Restaurant name
	 */
	private String RestaurantName;
	
	/**
	 * Report year
	 */
	private int Year;
	 
	/**
	 * Report month
	 */
	private int Month;
	
	/**
	 * Late delivery 
	 */
	private int Late;
	
	/**
	 * Early delivert
	 */
	private int Early;
	
	/**
	 * @param restaurantName
	 * @param year
	 * @param month
	 * @param late
	 * @param early
	 * Constructor
	 */
	public PerformenceReport(String restaurantName, int year, int month, int late, int early) {
		super();
		RestaurantName = restaurantName;
		Year = year;
		Month = month;
		Late = late;
		Early = early;
	}
	public String getRestaurantName() {
		return RestaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		RestaurantName = restaurantName;
	}
	public int getYear() {
		return Year;
	}
	public void setYear(int year) {
		Year = year;
	}
	public int getMonth() {
		return Month;
	}
	public void setMonth(int month) {
		Month = month;
	}
	public int getLate() {
		return Late;
	}
	public void setLate(int late) {
		Late = late;
	}
	public int getEarly() {
		return Early;
	}
	public void setEarly(int early) {
		Early = early;
	}
	@Override
	public String toString() {
		return "PerformenceReport [RestaurantName=" + RestaurantName + ", Year=" + Year + ", Month=" + Month + ", Late="
				+ Late + ", Early=" + Early + "]";
	}
	
	
	
}