package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import common.Addition;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.User;
import common.clientDetails;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * User have 2 options of how to get his order (Pick-up, Delivery)
 * If user wants shared delivery (Available only for business users) then he have to enter code of the group
 * If user enters shared delivery then he has to wait until the creator of the delivery submits the order
 * @author asem
 *
 */
public class TypeOfOrderController extends Thread implements Initializable {
	/**
	 * User chooses the delivery option
	 */
	@FXML
	private Button DeliveryBtn;

	/**
	 * User chooses the pick up option
	 */
	@FXML
	private Button PickUpBtn;

	/**
	 * Go back to Show Order controller
	 */
	@FXML
	private Button backBtn;

	/**
	 * Text for user to enter group delivery code
	 */
	@FXML
	private TextField sharedtxt;

	/**
	 * Click shared button to enter group
	 */
	@FXML
	private ButtonBar sharedBar;
	
	/**
	 * Pop error messages
	 */
	@FXML
    private Text errtxt;

    @FXML
    private Label sharedlbl;

	public clientDetails cd;

	public static Alert alert;
	private ActionEvent e;
	public static boolean shared=false;
	public static User user;
	public static int num;
	public static int payEnd=0;
	public static boolean inPayment=false;
	public static boolean ingruop=false;

	/**
	 * go back to show order of user page
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void BackToRestPage(ActionEvent event) throws Exception {
		shared=false;
		if(ChatClient.user.getUserType().equals("Bussiness") && ChatClient.Bussinessuser.getLock() == 1)
			return;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		ShowOrderController SOC = new ShowOrderController();
		try {
			SOC.start(primaryStage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 	go to delivery form
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void DeliveryFunction(ActionEvent event) throws Exception {
		shared=false;
		if(ChatClient.user.getUserType().equals("Bussiness") && ChatClient.Bussinessuser.getLock() == 1)
			return;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		DeliveryInfoController DIC = new DeliveryInfoController();
		DIC.start(primaryStage);
	}

	/**
	 * 	go to pickup form
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void PickUpFunction(ActionEvent event) throws Exception {
		shared=false;
		if(ChatClient.user.getUserType().equals("Bussiness") && ChatClient.Bussinessuser.getLock() == 1)
			return;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		PickUpFormController PUFC = new PickUpFormController();
		PUFC.start(primaryStage);
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/TypeOfOrderFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Type Of Order");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * if user is business and he picked shared delivery
	 * process the shared delivery type to the creator of the group
	 * @param e
	 */
	@FXML	
	void SharedBtn(ActionEvent e) {
		if(sharedtxt.getText().equals(""))
		{
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Please Enter Group Number");
			return;
		}
		if(ChatClient.Bussinessuser.getLock() == 1)
			return;
		this.e = e;

		//get group number from text field
		MessagesClass msg = new MessagesClass(Messages.getGroupNumber, Integer.parseInt(sharedtxt.getText()));
		ClientUI.chat.accept(msg);
	
		
		if (ChatClient.group_exist == true) {
			ingruop=true;
		int delivery=15;
		if(ChatClient.group_size==1)
		{
			delivery=20;
		}
		AdditionsController.OverAllSum+=delivery;
		// if business user have enough money in his refund card pop alert
		ShowInfoOfOrderController.BussinessPrice = ChatClient.getRefund-AdditionsController.OverAllSum;

		if (ChatClient.user.getUserType().equals("Bussiness") && ChatClient.getRefund != -1&&ShowInfoOfOrderController.BussinessPrice>=0&&ShowInfoOfOrderController.PayWithRefund==false) {
		
				Stage primaryStage = new Stage();
				AlertController AC = new AlertController();
				try {
					AC.start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

		}
		///////////////////////////////////////////////////////////////
		// if business user doesn't have enough money in his refund card pop alert
		if (ChatClient.user.getUserType().equals("Bussiness") && ChatClient.getRefund != -1&&ShowInfoOfOrderController.BussinessPrice<0&&ShowInfoOfOrderController.PayWithRefund==false) {
			
			Stage primaryStage = new Stage();
			AlertController AC = new AlertController();
			try {
				AC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

	}
		// if business user doesn't have enough money in his w4c card pop alert
	if (ChatClient.user.getUserType().equals("Bussiness")
				&& AdditionsController.OverAllSum > ChatClient.Bussinessuser.getW4c().getMoney()&& (ChatClient.getRefund == -1||ShowInfoOfOrderController.PayWithRefund==true)) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Payment");
			alert.setHeaderText("Ops you don't have enough money in your W4C balance");
			alert.setContentText("Pay with W4C and the rest with your credit card?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				ChatClient.Bussinessuser.getW4c().setMoney(0);
				MessagesClass msge = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
				ClientUI.chat.accept(msge);
			} else {
				return;
			}
		}
		// if business user have the required money in his w4c card -> update w4c
		// balance
		if (ChatClient.user.getUserType().equals("Bussiness")
				&& AdditionsController.OverAllSum < ChatClient.Bussinessuser.getW4c().getMoney()&& (ChatClient.getRefund == -1||ShowInfoOfOrderController.PayWithRefund==true)) {
			ChatClient.Bussinessuser.getW4c()
					.setMoney(ChatClient.Bussinessuser.getW4c().getMoney() - AdditionsController.OverAllSum);
			MessagesClass msgr = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
			ClientUI.chat.accept(msgr);
		}
	
		//check if group number exists in date base
			shared=true;
			user=ChatClient.user;
			cd = new clientDetails(ChatClient.Bussinessuser.getFirstName(),
					Integer.parseInt(sharedtxt.getText()), "Joined", 0);
			MessagesClass logoutmsg= new MessagesClass(Messages.updateStatus, ChatClient.user, 0);
			MessagesClass loginmsg= new MessagesClass(Messages.updateStatus, ChatClient.user, 1);
			cd.setLogoutmsg(logoutmsg);
			cd.setLoginmsg(loginmsg);
			cd.setNuser(ChatClient.user);
			//cd.setArlist(AdditionsController.arlist);
			ChatClient.Bussinessuser.setGroup_code(cd.getCode());
			ChatClient.Bussinessuser.setOverAllPrice(AdditionsController.OverAllSum);
			cd.setUser(ChatClient.Bussinessuser);

			
			//////////////////////////////////////////////////////////////////////
			MessagesClass msgc = new MessagesClass(Messages.getOrderID, null);
			ClientUI.chat.accept(msgc);
			int orderNum = ChatClient.orderNum;
			orderNum++;
			
			MessagesClass msgi = new MessagesClass(Messages.getIND, null);
			ClientUI.chat.accept(msgi);
			int ind = ChatClient.ind;
			ind++;
			ArrayList<Item> items = new ArrayList<>();
			for (Item it : RestaurantController.spinners.keySet()) {
				Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
				if (spin.getValue().intValue() > 0)
					items.add(new Item(it.getItem_ID(), spin.getValue().intValue()));
			}
			String CurrentDateAndTime = String
					.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

			//make order object of the user and save it
			Order order = new Order(orderNum, RestaurantController.restaurant, ChatClient.user, CurrentDateAndTime,
					AdditionsController.OverAllSum, items, "", "", "","");
			ChatClient.Bussinessuser.setOrder(order);
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
			cd.setItem_addition(item_additions);
			/*
			MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_addition);
			ClientUI.chat.accept(msg5);
			*/
			//update the admin table to show him that this user has joined the created room
			
			runThread2();
			/*
			MessagesClass msg1 = new MessagesClass(Messages.updateTable, cd);
			ClientUI.chat.accept(msg1);
			
			//pops alert to user and tells him to wait until everyone else joined the room
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Waiting for other member to join");
			alert.showAndWait();
			*/
			//set lock to user so that he can't interact anymore
			ChatClient.Bussinessuser.setLock(1);
			
			//run thread so when everyone joined the room and admins clicked on finish order everyone else is free to go
			runThread();
		} else {
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Wrong code, try again");
			return;
		}
	}
	
	private void runThread2() {
		Thread t = new Thread(() -> {
			while (inPayment==false)
				try {
					Thread.sleep(1);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			Platform.runLater(() -> {
				MessagesClass msg1 = new MessagesClass(Messages.updateTable, cd);
				ClientUI.chat.accept(msg1);
				
				//pops alert to user and tells him to wait until everyone else joined the room
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Waiting for other member to join");
				alert.showAndWait();

			});
		});
		t.start();
	}

	/**
	 * Wait until the creator of the group confirms that everyone joined the group
	 */
	private void runThread() {
		Thread t = new Thread(() -> {
			while (ChatClient.Bussinessuser.getLock() == 1)
				try {
					Thread.sleep(1);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			Platform.runLater(() -> {
				payEnd=1;
				
				
				//ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				PaymentSuccessfulController PSC = new PaymentSuccessfulController();
				try {
					PSC.start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			});
		});
		t.start();
	}

	/**
	 *Initialize parameters and controller fields
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!ChatClient.user.getUserType().equals("Bussiness")) {
			sharedBar.setVisible(false);
			sharedlbl.setVisible(false);
		}
	}

}
