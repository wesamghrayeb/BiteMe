package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Addition;
import common.BussinessUser;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.clientDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;


/**
 * Show that payment was successful and order will be processed
 * User can go back to home page and make another order 
 * @author asem
 *
 */
public class PaymentSuccessfulController {

	ClientController chat;
	
	/**
	 * go back to home page
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void BackToHomePage(ActionEvent event) throws Exception {
		//TypeOfOrderController.shared=false;
		PickUpFormController.Pickup = false;
		DeliveryInfoController.Delivery = false;
		
		if(TypeOfOrderController.payEnd==1)
		{
		
			ClientUI.chat = new ClientController(FirstPageController.ip, 5555);
			ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user,0));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			LogInForm aFrame = new LogInForm();
			Stage primaryStage = new Stage();
			aFrame.start1(primaryStage);
			TypeOfOrderController.payEnd=0;
			return;
		}
		if(ChatClient.user.getUserType().equals("Normal")||ChatClient.user.getUserType().equals("Bussiness")&&TypeOfOrderController.payEnd==0)
		{
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		NormalUserHomePageController NUHPC = new NormalUserHomePageController();
		NUHPC.start(primaryStage);
		//NormalUserHomePageController.homePageStage.show();
		return;

		}
		/*
		
		System.out.print(ChatClient.user+"****");
		chat = new ClientController(FirstPageController.ip, 5555);
		ClientUI.chat = new ClientController(FirstPageController.ip, 5555);
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user,0));
		//resetAll();
		LogInForm aFrame = new LogInForm();
		Stage primaryStage = new Stage();
		aFrame.start1(primaryStage);
		*/
	}

	public void start(Stage primaryStage) throws Exception {
		if (PickUpFormController.Pickup == true || DeliveryInfoController.Delivery == true)
			UpdateDBOrder();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/PaymentSuccessfulFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Payment success");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	//
	/**
	 * Upload order into the data base
	 * Update all order related information to database (solditems,reports,etc...)
	 */
	private void UpdateDBOrder() {
		Order order = null;
		Order solditems = null;

		MessagesClass msg = new MessagesClass(Messages.getOrderID, null);
		ClientUI.chat.accept(msg);
		int orderNum = ChatClient.orderNum;
		orderNum++;
		ArrayList<Item> items = new ArrayList<>();
		for (Item it : RestaurantController.spinners.keySet()) {
			Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
			if (spin.getValue().intValue() > 0)
				items.add(new Item(it.getItem_ID(), spin.getValue().intValue()));
		}
		
		//NEED TO BE CHECKED FOR TOMORROW from here
		MessagesClass msgi = new MessagesClass(Messages.getIND, null);
		ClientUI.chat.accept(msgi);
		int ind = ChatClient.ind;
		ind++;
		//TO HERE////
		ArrayList<Item> item_addition=new ArrayList<>();
		ArrayList<Item> item_additions=new ArrayList<>();
		ArrayList<Addition> additions;
		String str="";
		for(int i=0;i<AdditionsController.arlist.size();i++)
		{
			additions=new ArrayList<>();
			str="";
			CheckBox[] c = AdditionsController.arlist.get(i).getCB();
			for (int k = 0; k < c.length; k++) {
				if (c[k].isSelected())
				{
					Addition add=new Addition(c[k].getText());
					additions.add(add);
					str+=add.getName();
					str+=" ";
				}
			}
			item_addition.add(new Item(orderNum,AdditionsController.arlist.get(i).getItem().getItem_ID(),additions,ind++));
			item_additions.add(new Item(orderNum,AdditionsController.arlist.get(i).getItem().getItem_ID(),str,ind++));
		}
		
		String CurrentDateAndTime = String
				.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String EarlyBooking="";
		//if order is pick up upload
		if (PickUpFormController.Pickup == true) {
			DeliveryInfoController.Delivery = false;
			String datePick = String.valueOf(PickUpFormController.pickup.getDate());
			String hourPick = String.valueOf(PickUpFormController.pickup.getHour());
			String minutePick = String.valueOf(PickUpFormController.pickup.getMinute());
			
			String pickedDateAndTimeString=datePick+" "+hourPick+":"+minutePick;
			try {
				Date CurrentDate = sdformat.parse(CurrentDateAndTime);
				Date pickedDateAndTime=sdformat.parse(pickedDateAndTimeString);
				if (pickedDateAndTime.getTime() >= CurrentDate.getTime() + (1000*60*60*2))
				{
					EarlyBooking="Yes";
				}
				else
					EarlyBooking="No";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			order = new Order(orderNum, RestaurantController.restaurant, ChatClient.user, CurrentDateAndTime,
					AdditionsController.OverAllSum, items, datePick, hourPick, minutePick,EarlyBooking);

			String pickedDate = String.valueOf(PickUpFormController.pickup.getDate());
			String year = pickedDate.substring(0, 4);
			String month = pickedDate.substring(5, 7);
			
			solditems = new Order(RestaurantController.restaurant, items, Integer.parseInt(month),
					Integer.parseInt(year));

			MessagesClass msg4 = new MessagesClass(Messages.soldItems, solditems);
			ClientUI.chat.accept(msg4);

			MessagesClass msg1 = new MessagesClass(Messages.newOrder, order);
			ClientUI.chat.accept(msg1);
			
			//MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_addition);
			MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_additions);
			ClientUI.chat.accept(msg5);
		}
		
		//if order is delivery and not shared upload order and delivery to data base
		if (DeliveryInfoController.Delivery == true && !DeliveryInfoController.deliveryType.equals("Shared")) {
			PickUpFormController.Pickup = false;
			String datePick = String.valueOf(DeliveryInfoController.del.getDate());
			String hourPick = String.valueOf(DeliveryInfoController.del.getHour());
			String minutePick = String.valueOf(DeliveryInfoController.del.getMinute());
			String year = datePick.substring(0, 4);
			String month = datePick.substring(5, 7);
			
			
			String pickedDateAndTimeString=datePick+" "+hourPick+":"+minutePick;
			try {
				Date CurrentDate = sdformat.parse(CurrentDateAndTime);
				Date pickedDateAndTime=sdformat.parse(pickedDateAndTimeString);
				if (pickedDateAndTime.getTime() >= CurrentDate.getTime() + (1000*60*60*2))
				{
					EarlyBooking="Yes";
				}
				else
					EarlyBooking="No";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			order = new Order(orderNum, RestaurantController.restaurant, ChatClient.user, CurrentDateAndTime,
					AdditionsController.OverAllSum, items, datePick, hourPick, minutePick,EarlyBooking);
			DeliveryInfoController.del.setOrderNum(orderNum);
			DeliveryInfoController.del.setDeliveryNum(orderNum);
			MessagesClass msg2 = new MessagesClass(Messages.newOrder, order);
			ClientUI.chat.accept(msg2);
			
			solditems = new Order(RestaurantController.restaurant, items, Integer.parseInt(month),
					Integer.parseInt(year));

			MessagesClass msg4 = new MessagesClass(Messages.soldItems, solditems);
			ClientUI.chat.accept(msg4);

			MessagesClass msg3 = new MessagesClass(Messages.newDelivery, DeliveryInfoController.del);
			ClientUI.chat.accept(msg3);
			
			//MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_addition);
			MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_additions);
			ClientUI.chat.accept(msg5);
		}
		
		//if order is delivery and shared then set all group member to same delivery and upload each one order
		if (DeliveryInfoController.Delivery == true && DeliveryInfoController.deliveryType.equals("Shared")) {
			PickUpFormController.Pickup = false;
			String datePick = String.valueOf(DeliveryInfoController.del.getDate());
			String hourPick = String.valueOf(DeliveryInfoController.del.getHour());
			String minutePick = String.valueOf(DeliveryInfoController.del.getMinute());
			String year = datePick.substring(0, 4);
			String month = datePick.substring(5, 7);
			
			String pickedDateAndTimeString=datePick+" "+hourPick+":"+minutePick;
			try {
				Date CurrentDate = sdformat.parse(CurrentDateAndTime);
				Date pickedDateAndTime=sdformat.parse(pickedDateAndTimeString);
				if (pickedDateAndTime.getTime() >= CurrentDate.getTime() + (1000*60*60*2))
				{
					EarlyBooking="Yes";
				}
				else
					EarlyBooking="No";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			order = new Order(orderNum, RestaurantController.restaurant, ChatClient.user, CurrentDateAndTime,
					AdditionsController.OverAllSum, items, datePick, hourPick, minutePick,EarlyBooking);
			DeliveryInfoController.del.setOrderNum(orderNum);
			DeliveryInfoController.del.setDeliveryNum(orderNum);
			MessagesClass msg2 = new MessagesClass(Messages.newOrder, order);
			ClientUI.chat.accept(msg2);
			
			if(DeliveryInfoController.gp.getGroupSize()==1)
			{
		
				AdditionsController.OverAllSum+=25;
			}
			if(DeliveryInfoController.gp.getGroupSize()==2)
			{	
				
				AdditionsController.OverAllSum+=20;
			}
			if(DeliveryInfoController.gp.getGroupSize()>=3)
			{
				
				AdditionsController.OverAllSum+=15;
			}
			ChatClient.Bussinessuser.getW4c().setMoney(ChatClient.Bussinessuser.getW4c().getMoney()-AdditionsController.OverAllSum);
    		MessagesClass msgw4c=new MessagesClass(Messages.updateW4CforBussiness,ChatClient.Bussinessuser);
			ClientUI.chat.accept(msgw4c);
			
			solditems = new Order(RestaurantController.restaurant, items, Integer.parseInt(month),
					Integer.parseInt(year));

			MessagesClass msg4 = new MessagesClass(Messages.soldItems, solditems);
			ClientUI.chat.accept(msg4);

			MessagesClass msg3 = new MessagesClass(Messages.newDelivery, DeliveryInfoController.del);
			ClientUI.chat.accept(msg3);
			
			//MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_addition);
			MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_additions);
			ClientUI.chat.accept(msg5);
			
			//upload orders for all group members
			for(clientDetails cd:SharedDeliveryController.clients)
			{
				msg = new MessagesClass(Messages.getOrderID, null);
				ClientUI.chat.accept(msg);
				int orderNum1 = ChatClient.orderNum;
				orderNum1++;
				BussinessUser user=cd.getUser();
				Order tmporder=user.getOrder();
				tmporder.setOrderNum(orderNum1);
				tmporder.setDate(datePick);
				tmporder.setHour(hourPick);
				tmporder.setMinute(minutePick);
				tmporder.setEarlyBooking(EarlyBooking);
				DeliveryInfoController.del.setDeliveryNum(orderNum);
				DeliveryInfoController.del.setOrderNum(orderNum1);
				msg2 = new MessagesClass(Messages.newOrder, tmporder);
				ClientUI.chat.accept(msg2);	
				
				if(DeliveryInfoController.gp.getGroupSize()==1)
					user.setOverAllPrice(user.getOverAllPrice()+20);
					
				if(DeliveryInfoController.gp.getGroupSize()>=2)
					user.setOverAllPrice(user.getOverAllPrice()+15);
				
				user.getW4c().setMoney(user.getW4c().getMoney()-user.getOverAllPrice());
        		msgw4c=new MessagesClass(Messages.updateW4CforBussiness,user);
    			ClientUI.chat.accept(msgw4c);
				
				solditems = new Order(tmporder.getRes(), tmporder.getItems(), Integer.parseInt(month),
						Integer.parseInt(year));

				msg4 = new MessagesClass(Messages.soldItems, solditems);
				ClientUI.chat.accept(msg4);

				
				msg3 = new MessagesClass(Messages.newDelivery, DeliveryInfoController.del);
				ClientUI.chat.accept(msg3);	
				
				item_additions=cd.getItem_addition();
				for(int i=0;i<item_additions.size();i++)
				{
					Item order_num=item_additions.get(i);
					order_num.setOrderNum(orderNum1);
					item_additions.set(i, order_num);
				}
				msg5 = new MessagesClass(Messages.order_items_additions,item_additions);
				ClientUI.chat.accept(msg5);
								
			}
		}
	}

}
