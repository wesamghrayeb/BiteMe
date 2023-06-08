package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import client.ChatClient;
import client.ClientUI;
import common.Item;
import common.ItemAddition;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.WorkerUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * this class to help the worker to see new orders request
 * handle all request from customers and process their orders 
 * @author asem
 *
 */
public class agreeRestaurantrequset implements Initializable {
	String sqltable;
	/**
	 * observableelist listm using to table view 
	 */
	ObservableList<Order> listM;
	ObservableList<Order> dataList;
	static ObservableList List1 = FXCollections.observableArrayList();

	public static Order order;
	/**
	 * order table view
	 */
	@FXML
	private TableView<Order> tabel;
	/**
	 * addition list of specific item
	 */
	@FXML
	private ListView<String> additionlist;

	@FXML
	private static ListView<String> itemlist;
	@FXML
	private TableColumn<Order, Integer> ordernumber;
	static int idorder;
	/**
	 * user id
	 */
	@FXML
	private TableColumn<Order, String> userid;

	@FXML
	private TableColumn<Order, String> submitdate;

	@FXML
	private TableColumn<Order, Double> price;

	/**
	 * pick up time of order
	 */
	@FXML
	private TableColumn<Order, String> pickuptime;

	/**
	 * path txet
	 */
	@FXML
	private Text pathtext;

	@FXML
	private Text errtxt;
	static String status;
	@FXML
	private DatePicker datetxt;

	@FXML
	private ComboBox<String> minutetxt;
	String pickupTime;;
	@FXML
	private ComboBox<String> hourtxt;
	/**
	 * using boolean sent to status text
	 */
	private boolean sent = false;
	URL location;
	ResourceBundle resources;
	/**
	 * Start function for drawing the needed page
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/agreeRestaurantrequset.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("confirm order page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	/**
	 * Send Delivery information to the customer and it's details, estimated delivery date and time
	 * Send Email to the personal Email for the user
	 * @param event
	 * @throws ParseException
	 */
	@FXML
	void Send_Delivery_Confirm(ActionEvent event) throws ParseException {
		if (datetxt.getValue() == (null) || hourtxt.getSelectionModel().isEmpty()
				|| minutetxt.getSelectionModel().isEmpty()) {
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("  Enter details");
			return;
		}
	
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date); // assigns calendar to given date
		int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int minutes = calendar.get(Calendar.MINUTE);

		String pickedDate = datetxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Date CurrentDate = sdformat.parse(RestaurantWorkerHomePage.sClock);
		Date picked_Date = sdformat.parse(pickedDate);
		int hour = Integer.parseInt(hourtxt.getValue());
		int minute = Integer.parseInt(minutetxt.getValue());

		if ((hours < hour && CurrentDate.compareTo(picked_Date) == 0)
				|| (minutes < minute && hours == hour && CurrentDate.compareTo(picked_Date) == 0)
				|| CurrentDate.compareTo(picked_Date) < 0) {
			String str = "Delivery on it's way\n" + "Expected Date and Time\n" + "Date: " + pickedDate + "\nTime: "
					+ hourtxt.getValue() + ":" + minutetxt.getValue();

			MessagesClass msg = new MessagesClass(Messages.getTypeOfOrder, idorder);
			ClientUI.chat.accept(msg);
			if (ChatClient.TypeOfOrder.equals("Pick Up")) {
				Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						errtxt.setText("");
					}
					
				}));
				fiveseconds.setCycleCount(Timeline.INDEFINITE);
				fiveseconds.play();
				errtxt.setText("Order is Pick Up Type");
				return;
			}
			msg = new MessagesClass(Messages.updateOrderStatus, idorder, "Delivery On it's way");
			ClientUI.chat.accept(msg);

			sendToClientEmail(str);
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Email Sent");
			
			initialize(location, resources);
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
			errtxt.setText("Enter Valid information");
			return;
		}
	}

	
	/**
	 * Send Pick information to the customer, Notify the customer
	 * Send Email to the personal Email for the user
	 * @param event
	 */
	@FXML
	void Send_Pickup_Confirm(ActionEvent event) {
		if (pickupTime == null) {
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Please pick order before you send");
			return;
		}
		
		MessagesClass msg = new MessagesClass(Messages.getTypeOfOrder, idorder);
		ClientUI.chat.accept(msg);

		if (ChatClient.TypeOfOrder.equals("Delivery")) {
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Order is Delivery Type");
			return;
		}
		msg = new MessagesClass(Messages.updateOrderStatus, idorder, "Ready");
		ClientUI.chat.accept(msg);

		String email = "Hi " + order.getUserid() + ",\nYour Order is waiting for you to pick it up! :)";
		sendToClientEmail(email);
		Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				errtxt.setText("");
			}
			
		}));
		fiveseconds.setCycleCount(Timeline.INDEFINITE);
		fiveseconds.play();
		errtxt.setText("Email Sent");
	}

	/**
	 * Return to restaurant worker home page
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		RestaurantWorkerHomePage aFrame = new RestaurantWorkerHomePage(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *Initialize for drawing needed things before opening the page
	 *Set years in combobox
	 *set months in combobox
	 *set all the confirm restaurants in combobox
	 *set reports type in combobox
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pathtext.setText("LogIn->WorkerPage->acceptOrders");

		
		
		int i;
		hourtxt.getItems().removeAll();
		minutetxt.getItems().removeAll();
		for (i = 0; i < 24; i++) {
			if (i < 10)
				hourtxt.getItems().add("0" + String.valueOf(i));
			else
				hourtxt.getItems().add(String.valueOf(i));
		}

		for (i = 0; i < 60; i++) {
			if (i < 10)
				minutetxt.getItems().add("0" + i);
			else
				minutetxt.getItems().add(String.valueOf(i));
		}

		MessagesClass msg11 = new MessagesClass(Messages.GetAllOreders,
				RestaurantWorkerHomePage.WorkerUser.getIDRestaurant());
		ClientUI.chat.accept(msg11);
		listM = FXCollections.observableArrayList(ChatClient.AllOrder);/** array list of normal users */
		ordernumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
		userid.setCellValueFactory(new PropertyValueFactory<Order, String>("userid"));
		submitdate.setCellValueFactory(new PropertyValueFactory<Order, String>("CurrentDateAndTime"));
		price.setCellValueFactory(new PropertyValueFactory<Order, Double>("TotalPrice"));
		pickuptime.setCellValueFactory(new PropertyValueFactory<Order, String>("pickupTime"));
		tabel.setItems(listM);
		settext();
	}

	Session newSession = null;
	MimeMessage mimeMessage = null;

	private void sendEmail() throws MessagingException {
		String fromUser = "g5.biteme@gmail.com"; // Enter sender email id
		String fromUserPassword = "Biteme123"; // Enter sender gmail password , this will be authenticated by gmail smtp
												// server
		String emailHost = "smtp.gmail.com";
		Transport transport = newSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserPassword);
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
		System.out.println("Email successfully sent!!!");
	}

	private MimeMessage draftEmail(String str) throws AddressException, MessagingException, IOException {
		System.out.println(ChatClient.Email);
		String emailReceipients = ChatClient.Email; // Enter list of email
		String emailSubject = "Bite Me Admins";
		String emailBody = str;
		mimeMessage = new MimeMessage(newSession);
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients));
		mimeMessage.setSubject(emailSubject);

		// CREATE MIMEMESSAGE
		// CREATE MESSAGE BODY PARTS
		// CREATE MESSAGE MULTIPART
		// ADD MESSAGE BODY PARTS ----> MULTIPART
		// FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(emailBody);
		// bodyPart.setContent(emailBody, "html/text");
		MimeMultipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(bodyPart);
		mimeMessage.setContent(multiPart);
		return mimeMessage;
	}

	private void setupServerProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		newSession = Session.getDefaultInstance(properties, null);
	}

	void sendToClientEmail(String str) {

		setupServerProperties();
		try {
			draftEmail(str);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sendEmail();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * When clicking on the row in the tableview all information of the clicked order are to be saved into parameters
	 * When clicking 2 clicks right away new controller open to show the info of the order
	 */
	private void settext() {
		tabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				order = tabel.getItems().get(tabel.getSelectionModel().getSelectedIndex());
				idorder = order.getOrderNum();
				pickupTime = order.getPickupTime();
				status = order.getStatus();
				MessagesClass msg11 = new MessagesClass(Messages.getuser, order.getId());
				ClientUI.chat.accept(msg11);
				if (event.getClickCount() == 2) {

					ItemsAddition aFrame = new ItemsAddition();
					Stage primaryStage = new Stage();
					try {
						aFrame.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					initialize(location, resources);
				}

			}
		});
	}
}
