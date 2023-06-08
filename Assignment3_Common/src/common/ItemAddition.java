package common;

import java.io.Serializable;

/**
 * get items and additions for workers controller
 * @author asem
 *
 */
public class ItemAddition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * item name
	 */
	private String item;
	/**
	 * additions name
	 */
	private String addition;
	/**
	 * show type of order
	 */
	private String typeoforder;
	/**
	 * show address
	 */
	private String AddressDelivery;
	private String PhoneDelivery;
	private String DeliveryDate;
	private String DeliveryType;
	private Delivery Delivery;

	public Delivery getDelivery() {
		return Delivery;
	}

	public void setDelivery(Delivery delivery) {
		Delivery = delivery;
	}

	public String getAddressDelivery() {
		return AddressDelivery;
	}

	public void setAddressDelivery(String addressDelivery) {
		AddressDelivery = addressDelivery;
	}

	public String getPhoneDelivery() {
		return PhoneDelivery;
	}

	public void setPhoneDelivery(String phoneDelivery) {
		PhoneDelivery = phoneDelivery;
	}

	public String getDeliveryDate() {
		return DeliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}

	public String getDeliveryType() {
		return DeliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		DeliveryType = deliveryType;
	}

	public ItemAddition(String item, String addition, String typeoforder,Delivery Delivery) {
		super();
		this.item = item;
		this.addition = addition;
		this.typeoforder = typeoforder;
		this.Delivery=Delivery;
	}

	public String getTypeoforder() {
		return typeoforder;
	}

	public void setTypeoforder(String typeoforder) {
		this.typeoforder = typeoforder;
	}

	public String getItem() {
		return item;
	}

	@Override
	public String toString() {
		return "ItemAddition [item=" + item + ", addition=" + addition + "]";
	}

	public ItemAddition(String item, String addition) {
		super();
		this.item = item;
		this.addition = addition;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

}