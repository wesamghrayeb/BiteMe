package common;

import java.io.Serializable;

/**
 * Class for taking wanted PDF
 * @author Bashar Bashir
 * 
 *
 */
public class PDFList implements Serializable{
	
	/**
	 * Restaurants location
	 */
	private String Location;
	
	/**
	 * Year of the report
	 */
	private int Year;
	
	/**
	 * Quarter for the report
	 */
	private String Months;
	
	/**
	 * @param location 
	 * @param year
	 * @param months
	 * Constructor 
	 */
	public PDFList(String location, int year, String months) {
		super();
		Location = location;
		Year = year;
		Months = months;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getYear() {
		return Year;
	}
	public void setYear(int year) {
		Year = year;
	}
	public String getMonths() {
		return Months;
	}
	public void setMonths(String months) {
		Months = months;
	}
	@Override
	public String toString() {
		return "PDFList [Location=" + Location + ", Year=" + Year + ", Months=" + Months + "]";
	}
	

}