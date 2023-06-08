package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.ResourceBundle;
import common.Delivery;
import common.GroupDelivery;
import common.Messages;
import common.MessagesClass;
import client.ChatClient;
import client.ClientUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Customer Enter his delivery information (name,date,address,time,etc...)
 * Customer can choose his delivery type (Basic,Shared(Available only for business customers),Robot)
 * Each delivery type has in own cost and it is shown to the user
 * If user is business type and he chose the shared delivery option then he enter the size of the group of people that the delivery is for
 * @author asem
 *
 */
public class DeliveryInfoController implements Initializable {
	/**
	 * User picks date
	 */
	@FXML
	private DatePicker datetxt;

	/**
	 * User picks name for who is the delivery to
	 */
	@FXML
	private TextField nametxt;

	/**
	 * User enters address
	 */
	@FXML
	private TextField adresstxt;

	/**
	 * User enters phone number
	 */
	@FXML
	private TextField phonenumbertxt;

	/**
	 * Choose delivery type
	 */
	@FXML
	private ComboBox<String> deliverytxt;

	/**
	 * User can add comments to the delivery
	 */
	@FXML
	private TextArea opentxt;

	/**
	 * pick hours of wanted delivery
	 */
	@FXML
	private ComboBox<String> hourtxt;

	/**
	 * pick minutes of wanted delivery
	 */
	@FXML
	private ComboBox<String> minutetxt;

	/**
	 * If user some syntax error or leaves fields empty then pops message
	 */
	@FXML
	private Text errtxt;

	/**
	 * Available for shared delivery only
	 * choose number of participants for shared delivery
	 */
	@FXML
	private Spinner<Integer> partnersSpinner;

	@FXML
	private Text partnerstxt;

	@FXML
	private Text sharedtxt;

	public static boolean Delivery;

	public static String deliveryType;

	public static Delivery del;

	/**
	 * Generates random number for created shared group
	 */
	public static int randGroup;

	public static GroupDelivery gp;

	/**
	 *  go back to choose delivery type option
	 * @param event
	 */
	@FXML
	void BackBtn(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		TypeOfOrderController TOOC = new TypeOfOrderController();
		try {
			TOOC.start(primaryStage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * go to payment page
	 * if business user picked shared delivery option
	 * if yes => generate random number between 10000-99999 of the group so that other users can join
	 * save number of group and size to the data base
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void goToPayment(ActionEvent event) throws Exception {
		// if any field left empty pops a message to user
		if (nametxt.getText().equals("") || adresstxt.getText().equals("") || phonenumbertxt.getText().equals("")
				|| datetxt.getValue() == (null) || deliverytxt.getSelectionModel().isEmpty()
				|| hourtxt.getSelectionModel().isEmpty() || minutetxt.getSelectionModel().isEmpty()) {
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

		// take current date and hour
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date); // assigns calendar to given date
		int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int minutes = calendar.get(Calendar.MINUTE);

		// take picked date and hour from user
		String pickedDate = datetxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Date CurrentDate = sdformat.parse(NormalUserHomePageController.sClock);
		Date picked_Date = sdformat.parse(pickedDate);
		int hour = Integer.parseInt(hourtxt.getValue());
		int minute = Integer.parseInt(minutetxt.getValue());

		// check if picked date and hour is legal
		if ((hours < hour && CurrentDate.compareTo(picked_Date) == 0)
				|| (minutes < minute && hours == hour && CurrentDate.compareTo(picked_Date) == 0)
				|| CurrentDate.compareTo(picked_Date) < 0) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			if (ChatClient.user.getUserType().equals("Normal") || (ChatClient.user.getUserType().equals("Bussiness")
					&& !deliverytxt.getValue().equals("Shared"))) {
				Delivery = true;
				del = new Delivery(nametxt.getText(), phonenumbertxt.getText(), adresstxt.getText(),
						deliverytxt.getValue(), datetxt.getValue().toString(), hourtxt.getValue(), minutetxt.getValue(),
						opentxt.getText(), 0);
				PaymentNormalUserController PNUC = new PaymentNormalUserController();
				PNUC.start(primaryStage);
			}

			// check if business user picked shared delivery option
			// if yes -> generate random number between 10000-99999 of the group so that
			// other users can join
			// save number of group and size to the data base
			else if ((ChatClient.user.getUserType().equals("Bussiness") && deliverytxt.getValue().equals("Shared"))
					&& partnersSpinner.getValue().intValue() > 0) {

				Random r = new Random();
				randGroup = r.nextInt(99999 - 10000) + 10000;
				ChatClient.numCode = randGroup;
				gp = new GroupDelivery(randGroup, partnersSpinner.getValue().intValue());
				MessagesClass msg = new MessagesClass(Messages.partnersGroupNumber, gp);

				ClientUI.chat.accept(msg);

				Delivery = true;
				del = new Delivery(nametxt.getText(), phonenumbertxt.getText(), adresstxt.getText(),
						deliverytxt.getValue(), datetxt.getValue().toString(), hourtxt.getValue(), minutetxt.getValue(),
						opentxt.getText(), 0);
				PaymentNormalUserController PNUC = new PaymentNormalUserController();
				PNUC.start(primaryStage);
			}
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
			errtxt.setText("Enter a valid date");
			return;
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Delivery = false;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/DeliveryInfoFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Delivery Info");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 *initialize all delivery fields and properties so that user can't enter wrong details
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// phone number text field accept only number
		phonenumbertxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,10}?")) {
					phonenumbertxt.setText(oldValue);
				}
			}
		});

		// name text field accept only characters
		nametxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\sa-zA-Z*")) {
					nametxt.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			}
		});

		// initialize hours from 0-23
		int i;
		for (i = 0; i < 24; i++) {
			if (i < 10)
				hourtxt.getItems().add("0" + String.valueOf(i));
			else
				hourtxt.getItems().add(String.valueOf(i));
		}
		// initialize minutes from 0-59
		for (i = 0; i < 60; i++) {
			if (i < 10)
				minutetxt.getItems().add("0" + i);
			else
				minutetxt.getItems().add(String.valueOf(i));
		}

		// initialize delivery types
		deliverytxt.getItems().add("Basic 25$");
		if (ChatClient.user.getUserType().equals("Bussiness"))
			deliverytxt.getItems().add("Shared");
		deliverytxt.getItems().add("Robot");

		// if user is not business type set invisible
		
		partnerstxt.setVisible(false);
		partnersSpinner.setVisible(false);

		deliverytxt.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if (newValue.equals("Shared")) {
				deliveryType = "Shared";
				partnerstxt.setVisible(true);
				partnersSpinner.setVisible(true);
				sharedtxt.setVisible(true);
				SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,
						0);
				partnersSpinner.setValueFactory(valueFactory);

			} else {
				deliveryType = newValue;
				partnerstxt.setVisible(false);
				sharedtxt.setVisible(false);
				partnersSpinner.setVisible(false);
			}
		});

		// set shared group delivery prices according to number of people picked
		partnersSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {

			switch (partnersSpinner.getValue().intValue()) {
			case 0:
				sharedtxt.setText("25$");
				break;
			case 1:
				sharedtxt.setText("20$");
				break;
			case 2:
				sharedtxt.setText("15$");
				break;
			case 3:
				sharedtxt.setText("15$");
				break;
			default:
				sharedtxt.setText("15$");
				break;
			}
		});

	}

}
