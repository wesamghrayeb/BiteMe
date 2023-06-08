package common;

import java.io.Serializable;

/**
 * Save restaurant details 
 * @author asem
 *
 */
public class Resturaunt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int resIdCounter=1;
	/**
	 * get restaurant id
	 */
	private int resturauntID;
	/**
	 * restaurant id
	 */
	private String resturaunt_Name;
	/**
	 * restaurant location
	 */
	private String location;
	private int status;
	private Menu menu;
	/**
	 * save taxes rate for restaurant
	 */
	private double taxe;
	
	public double getTaxe() {
		return taxe;
	}
	public void setTaxe(double taxe) {
		this.taxe = taxe;
	}
	public void setResturauntID(int resturauntID) {
		this.resturauntID = resturauntID;
	}
	public int getStatus() {
		return status;
	}
	public Resturaunt(int ResId,String resturaunt_Name, Menu menu) {
		this.resturauntID = ResId;
		this.resturaunt_Name = resturaunt_Name;
		this.menu = menu;
	}
	
	public Resturaunt(int ResId,String resturaunt_Name,String location,Menu menu)
	{
		this.resturauntID = ResId;
		this.resturaunt_Name = resturaunt_Name;
		this.location=location;
		this.menu = menu;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Resturaunt(int resturauntID, String resturaunt_Name, String location) {
		super();
		this.resturauntID = resturauntID;
		this.resturaunt_Name = resturaunt_Name;
		this.location = location;
	}


	
	public Resturaunt(int resturauntID, String resturaunt_Name, String location, double taxe) {
		super();
		this.resturauntID = resturauntID;
		this.resturaunt_Name = resturaunt_Name;
		this.location = location;
		this.taxe = taxe;
	}
	public Resturaunt(String resturaunt_Name, Menu menu){
		this.resturauntID = resIdCounter++;
		this.resturaunt_Name = resturaunt_Name;
		this.menu = menu;
	}

	public String getResturaunt_Name() {
		return resturaunt_Name;
	}

	public void setResturaunt_Name(String resturaunt_Name) {
		this.resturaunt_Name = resturaunt_Name;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int getResturauntID() {
		return resturauntID;
	}

	@Override
	public String toString() {
		return "Resturaunt [resturauntID=" + resturauntID + ", resturaunt_Name=" + resturaunt_Name + ", menu=" + menu + "]";	
	}
}
