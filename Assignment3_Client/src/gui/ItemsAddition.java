package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
import common.ItemAddition;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.OrderHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * @author moham
 *
 */
public class ItemsAddition implements Initializable {
	/**
	 * list of items and addition
	 */
	ObservableList<ItemAddition> listM;
	ObservableList<ItemAddition> dataList;
	/**
	 * table view 
	 */
	@FXML
	private TableView<ItemAddition> itemsadditiontable;
	/**
	 * confirm text 
	 */
	@FXML
	private Text confiredtxt;
	/**
	 * type of order delivery or pick up
	 */
	@FXML
	private Text typeoforder;
	/**
	 * delivery information text
	 */
	@FXML
    private Text deleviryinfo;
	@FXML
    private Text phonenumbertext;

    /**
     * adress
     */
    @FXML
    private Text adresssss;

    /**
     * date
     */
    @FXML
    private Text dateee;

    /**
     * type
     */
    @FXML
    private Text typeee;
    /**
     * phone 
     */
    @FXML
    private Text phondelivery;

    /**
     * addres
     */
    @FXML
    private Text AddresDelivery;

    /**
     * gat date of date
     */
    @FXML
    private Text Datedelivery;

    /**
     * type
     */
    @FXML
    private Text typede;
	/**
	 * items
	 */
	@FXML
	private TableColumn<ItemAddition, String> item;
	/**
	 * accept buttons
	 */
	@FXML
	private Button acceptbt;
	/**
	 * addition of item
	 */
	  @FXML
	    private Text ordernumbertext;

	@FXML
	private TableColumn<ItemAddition, String> addition;
	URL location;
	ResourceBundle resources;

	/**
	 * @param primaryStage
	 * @throws Exception
	 * start method that open the gui of item addition
	 * Start function for drawing the needed page

	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/Items&Addition.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Items&Addition");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 *initialize function for drawing needed thing in before opening the page
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (agreeRestaurantrequset.status.equals("Accepted")) {
			acceptbt.setDisable(true);
			confiredtxt.setText("Confirmed");
		}
		ordernumbertext.setText(""+agreeRestaurantrequset.idorder);
		deleviryinfo.setVisible(false);
		phondelivery.setVisible(false);
		AddresDelivery.setVisible(false);
		Datedelivery.setVisible(false);
		typede.setVisible(false);
		phonenumbertext.setVisible(false);
		adresssss.setVisible(false);
		dateee.setVisible(false);
		typeee.setVisible(false);

		System.out.println(agreeRestaurantrequset.idorder);
		MessagesClass msg1 = new MessagesClass(Messages.GetOrderItems, agreeRestaurantrequset.idorder);
		ClientUI.chat.accept(msg1);

		listM = FXCollections.observableArrayList(ChatClient.AllOrderItem);/** array list of normal users */
		item.setCellValueFactory(new PropertyValueFactory<ItemAddition, String>("item"));
		addition.setCellValueFactory(new PropertyValueFactory<ItemAddition, String>("addition"));
		for (int i = 0; i < ChatClient.AllOrderItem.size(); i++) {
			ItemAddition IA = (ItemAddition) ChatClient.AllOrderItem.get(i);
			System.out.println(IA.getTypeoforder().equals("Delivery"));
			if (IA.getTypeoforder().equals("Delivery")) {
				typeoforder.setText(IA.getTypeoforder());
				System.out.println("*" + IA.getDelivery());
				deleviryinfo.setVisible(true);
				phondelivery.setVisible(true);
				AddresDelivery.setVisible(true);
				Datedelivery.setVisible(true);
				typede.setVisible(true);
				phonenumbertext.setVisible(true);
				adresssss.setVisible(true);
				dateee.setVisible(true);
				typeee.setVisible(true);
				phondelivery.setText(IA.getDelivery().getPhonenumber());
				AddresDelivery.setText(IA.getDelivery().getAddress());
				Datedelivery.setText(IA.getDelivery().getDatedelivary());
				typede.setText(IA.getDelivery().getDeliveryType());
			} else
				typeoforder.setText(IA.getTypeoforder());

		}

		itemsadditiontable.setItems(listM);

	}

	Session newSession = null;
	MimeMessage mimeMessage = null;

	/**
	 * this method to send email to user 
	 * @throws MessagingException
	 */
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

	private MimeMessage draftEmail() throws AddressException, MessagingException, IOException {
		System.out.println(ChatClient.Email);
		String emailReceipients = ChatClient.Email; // Enter list of email
		String emailSubject = "Bite Me Admins";
		String emailBody = "We confirmed Your Order";
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

	void sendToClientEmail() {

		setupServerProperties();
		try {
			draftEmail();
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
	 * 
	 * @param event
	 * @throws ParseException
	 */
	@FXML
	void closebt(ActionEvent event) throws ParseException {
		sendToClientEmail();
		agreeRestaurantrequset.status = "Accepted";
		acceptbt.setDisable(true);
		confiredtxt.setText("Confirmed");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String sClock = dtf.format(now);
		System.out.println(agreeRestaurantrequset.order.getId() + "******");
		String pickuptime = agreeRestaurantrequset.order.getPickupTime();

		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sClock);
		System.out.println(pickuptime);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(pickuptime);

		Date newDate = new Date(date2.getTime() + TimeUnit.HOURS.toMillis(1));
		Date newDate2 = new Date(date2.getTime() + TimeUnit.MINUTES.toMillis(20));

		MessagesClass msg1 = new MessagesClass(Messages.getOrderEarlyLate, agreeRestaurantrequset.order.getOrderNum());
		ClientUI.chat.accept(msg1);

		if (ChatClient.OrderEarlyLate.equals("Yes") && newDate2.before(date1)) {

			OrderHistory OH = new OrderHistory(agreeRestaurantrequset.idorder,
					RestaurantWorkerHomePage.WorkerUser.getIDRestaurant(), agreeRestaurantrequset.order.getId(), sClock,
					"Late");
			MessagesClass msg = new MessagesClass(Messages.ConfirmOrder, OH);
			ClientUI.chat.accept(msg);

		} else if (ChatClient.OrderEarlyLate.equals("No") && newDate.before(date1)) {

			OrderHistory OH = new OrderHistory(agreeRestaurantrequset.idorder,
					RestaurantWorkerHomePage.WorkerUser.getIDRestaurant(), agreeRestaurantrequset.order.getId(), sClock,
					"Late");
			MessagesClass msg = new MessagesClass(Messages.ConfirmOrder, OH);
			ClientUI.chat.accept(msg);

		} else {

			OrderHistory OH = new OrderHistory(agreeRestaurantrequset.idorder,
					RestaurantWorkerHomePage.WorkerUser.getIDRestaurant(), agreeRestaurantrequset.order.getId(), sClock,
					"Processing");
			MessagesClass msg = new MessagesClass(Messages.ConfirmOrder, OH);
			ClientUI.chat.accept(msg);

		}

		initialize(location, resources);
	}
}