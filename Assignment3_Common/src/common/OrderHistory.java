package common;

import java.io.Serializable;


/**
 * Show all orders for user
 * @author asem
 *
 */
public class OrderHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private int Ordernumbercl;
    private String ResName;
    private Double PriceCL;
    private String estimatedtimecl;
    private String Statuscl;
    private int RestaurantID;
    private String userID;
    private String SubmitDate;
    private String ReceiveTime;
    
    
    public OrderHistory(int orderid,int resID,String usrID,String SubmitDate,String Status)
    {
    	this.Ordernumbercl=orderid;
    	this.RestaurantID=resID;
    	this.userID=usrID;
    	this.SubmitDate=SubmitDate;
    	this.Statuscl=Status;
    }
    
	public int getRestaurantID() {
		return RestaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		RestaurantID = restaurantID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSubmitDate() {
		return SubmitDate;
	}
	public void setSubmitDate(String submitDate) {
		SubmitDate = submitDate;
	}
	public String getReceiveTime() {
		return ReceiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		ReceiveTime = receiveTime;
	}
	public int getOrdernumbercl() {
		return Ordernumbercl;
	}
	public void setOrdernumbercl(int ordernumbercl) {
		Ordernumbercl = ordernumbercl;
	}
	public String getResName() {
		return ResName;
	}
	public void setResName(String resName) {
		ResName = resName;
	}
	public Double getPriceCL() {
		return PriceCL;
	}
	public void setPriceCL(Double priceCL) {
		PriceCL = priceCL;
	}
	public String getEstimatedtimecl() {
		return estimatedtimecl;
	}
	public void setEstimatedtimecl(String estimatedtimecl) {
		this.estimatedtimecl = estimatedtimecl;
	}
	public String getStatuscl() {
		return Statuscl;
	}
	public void setStatuscl(String statuscl) {
		Statuscl = statuscl;
	}
	@Override
	public String toString() {
		return "OrderHistory [Ordernumbercl=" + Ordernumbercl + ", ResName=" + ResName + ", PriceCL=" + PriceCL
				+ ", estimatedtimecl=" + estimatedtimecl + ", Statuscl=" + Statuscl + "]";
	}
	public OrderHistory(int ordernumbercl, String resName, Double priceCL, String estimatedtimecl, String statuscl) {
		super();
		Ordernumbercl = ordernumbercl;
		ResName = resName;
		PriceCL = priceCL;
		this.estimatedtimecl = estimatedtimecl;
		Statuscl = statuscl;
	}

}
