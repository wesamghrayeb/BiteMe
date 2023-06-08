package common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * save Item name
 * @author asem
 *
 */
public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * item id (serial code)
	 */
	private int item_ID;
	public String getAdditions_names() {
		return additions_names;
	}

	public void setAdditions_names(String additions_names) {
		this.additions_names = additions_names;
	}

	/**
	 * item name
	 */
	private String item_Name;
	/**
	 * item price
	 */
	private Double price;
	private int orderNum;
	private int index;
	/**
	 * list of all items
	 */
	ArrayList<String> items;
	/**
	 * list of addition to specific item
	 */
	ArrayList<Addition> additions;
	/**
	 * category of item
	 */
	private Category category;
	/**
	 * quantity of wanted item
	 */
	private int quantity;
	private String cate;
	private String additions_names;
	
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	public Item(int item_ID,int quantity)
	{
		this.item_ID=item_ID;
		this.quantity=quantity;
	}

	public Item(int item_ID, String item_Name, Double price, int quantity) {
		super();
		this.item_ID = item_ID;
		this.item_Name = item_Name;
		this.price = price;
		this.quantity = quantity;
	}
	public Item (int orderNum,int item_ID,ArrayList<Addition> additions,int index)
	{
		this.orderNum=orderNum;
		this.item_ID=item_ID;
		this.additions=additions;
		this.index=index;
	}
	public Item (int orderNum,ArrayList<String> items,ArrayList<Addition> additions,int index)
	{
		this.orderNum=orderNum;
		this.items=items;
		this.additions=additions;
		this.index=index;
		this.items=items;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}

	public Item(int item_ID, String item_Name, Double price, ArrayList<Addition> additions, int quantity,Category category) {
		super();
		this.item_ID = item_ID;
		this.item_Name = item_Name;
		this.price = price;
		this.additions = additions;
		this.quantity = quantity;
		this.category=category;

	}
	
	public Item (int orderNum,int item_ID,String additions,int index)
	{
		this.orderNum=orderNum;
		this.item_ID=item_ID;
		this.additions_names=additions;
		this.index=index;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Item(int item_ID, String item_Name, Double price,Category category,ArrayList<Addition> additions) {
		this.item_ID = item_ID;
		this.item_Name = item_Name;
		this.price = price;
		this.category=category;
		this.additions=additions;
	}

	public ArrayList<Addition> getAdditions() {
		return additions;
		
	}

	public void setAdditions(ArrayList<Addition> additions) {
		this.additions = additions;
	}

	public int getItem_ID() {
		return item_ID;
	}
	public void setItem_ID(int item_ID) {
		this.item_ID = item_ID;
	}
	public String getItem_Name() {
		return item_Name;
	}
	public void setItem_Name(String item_Name) {
		this.item_Name = item_Name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCat() {
		return category;
	}

	public void setCat(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Item [item_ID=" + item_ID + ", item_Name=" + item_Name + ", price=" + price + ", additions=" + additions
				+ ", category=" + category + ", quantity=" + quantity + "]";
	}
}
