package common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to save each order details
 * @author asem
 *
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * order number
	 */
	private int orderNum;
	/**
	 * order to restaurant
	 */
	private Resturaunt res;
	/**
	 * order to user
	 */
	private User user;
	/**
	 * submit date of order
	 */
	private String CurrentDateAndTime;
	/**
	 * get total price
	 */
	private double TotalPrice;
	/**
	 * save all items for order
	 */
	private ArrayList<Item> items;
	private String date;
	private String hour;
	private String minute;
	private int month;
	private int year;
	private String userid;
	private String pickupTime;
	private String EarlyBooking;
	/**
	 * status of order
	 */
	private String Status;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getEarlyBooking() {
		return EarlyBooking;
	}

	public void setEarlyBooking(String earlyBooking) {
		EarlyBooking = earlyBooking;
	}

	public Order(int orderNum, double totalPrice,  String CurrentDateAndTime,String pickupTime,String userid) {
		super();
		this.orderNum = orderNum;
		TotalPrice = totalPrice;
		this.pickupTime = pickupTime;
		this.CurrentDateAndTime=CurrentDateAndTime;
		this.userid=userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	public Order(int orderNum,Resturaunt res, User user, String currentDateAndTime, double totalPrice, ArrayList<Item> items,
			String date, String hour, String minute,String EarlyBooking) {
		this.orderNum=orderNum;
		this.res = res;
		this.user = user;
		this.CurrentDateAndTime = currentDateAndTime;
		this.TotalPrice = totalPrice;
		this.items = items;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.pickupTime=date+"-"+hour+":"+minute;
		this.EarlyBooking=EarlyBooking;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public Order(Resturaunt res,ArrayList<Item> items,int month,int year)
	{
		this.res=res;
		this.items=items;
		this.month=month;
		this.year=year;
	}

	

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		
		String itemString2 = "";
		for (int i = 0; i < items.size(); i++) {
			itemString2+="item_ID="+String.valueOf(items.get(i).getItem_ID())+", quantity="+String.valueOf(items.get(i).getQuantity());
		}
		return "Order [orderNum=" + orderNum + ", res=" + res.getResturaunt_Name() + ", user=" + user.getID() + ", CurrentDateAndTime="
				+ CurrentDateAndTime + ", TotalPrice=" + TotalPrice +", "+ itemString2 + ", date=" + date + ", hour="
				+ hour + ", minute=" + minute + "]";
	}

	public int getOrderNum() {
		return orderNum;
	}

	public Resturaunt getRes() {
		return res;
	}

	public void setRes(Resturaunt res) {
		this.res = res;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCurrentDateAndTime() {
		return CurrentDateAndTime;
	}

	public void setCurrentDateAndTime(String currentDateAndTime) {
		CurrentDateAndTime = currentDateAndTime;
	}

	public double getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}
}