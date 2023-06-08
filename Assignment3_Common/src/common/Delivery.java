package common;

import java.io.Serializable;

/**
 * Class for delivery information
 * @author asem
 *
 */
public class Delivery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * first name for delivery
	 */
	private String firstname;
	/**
	 * phone number for delivery
	 */
	private String phonenumber;
	/**
	 * address to delivery to
	 */
	private String address;
	/**
	 * delivery type (basic,shared,robot)
	 */
	private String deliveryType;
	/**
	 * save date of wanted delivery
	 */
	private String date;
	/**
	 * save hour for wanted delivery
	 */
	private String hour;
	/**
	 * save minutes for wanted delivery
	 */
	private String minute;
	/**
	 * client open text
	 */
	private String clientText;
	/**
	 * save order number
	 */
	private int orderNum;
	/**
	 * save delivery number
	 */
	private int deliveryNum;
	private String datedelivary;
	
	public Delivery(String phonenumber, String address, String deliveryType, String datedelivary) {
		super();
		this.phonenumber = phonenumber;
		this.address = address;
		this.deliveryType = deliveryType;
		this.datedelivary = datedelivary;
	}

	public String getDatedelivary() {
		return datedelivary;
	}

	public void setDatedelivary(String datedelivary) {
		this.datedelivary = datedelivary;
	}

	public int getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(int deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Delivery(String firstname, String phonenumber, String address, String deliveryType, String date, String hour,
			String minute, String clientText,int orderNum) {
		this.firstname = firstname;
		this.phonenumber = phonenumber;
		this.address = address;
		this.deliveryType = deliveryType;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.clientText = clientText;
		this.orderNum=orderNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
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

	public String getClientText() {
		return clientText;
	}

	public void setClientText(String clientText) {
		this.clientText = clientText;
	}	
	
	@Override
	public String toString() {
		return "Delivery [firstname=" + firstname + ", phonenumber=" + phonenumber + ", address=" + address
				+ ", deliveryType=" + deliveryType + ", date=" + date + ", hour=" + hour + ", minute=" + minute
				+ ", clientText=" + clientText + ", orderNum=" + orderNum + ", deliveryNum=" + deliveryNum
				+ ", datedelivary=" + datedelivary + "]";
	}
}