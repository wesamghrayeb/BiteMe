package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import client.ChatClient;
import common.Pickup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PickUpFormController implements Initializable {

	@FXML
	private Text errtxt;

	@FXML
	private DatePicker datetxt;

	@FXML
	private ComboBox<String> hourtxt;

	@FXML
	private ComboBox<String> minutetxt;

	public static Pickup pickup;

	public static boolean Pickup;

	@FXML
	//go back to choose delivery type option
	void Back(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		TypeOfOrderController TOOC = new TypeOfOrderController();
		TOOC.start(primaryStage);
	}

	@FXML
	//go to payment page
	void Go_to_payment(ActionEvent event) throws Exception {
		
		//if any field left empty pops a message to user
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
			errtxt.setText("Enter Information");
			return;
		}
		
		//take current date and hour
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date); // assigns calendar to given date
		int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int minutes = calendar.get(Calendar.MINUTE);
		
		//take picked date and hour from user
		String pickedDate = datetxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Date CurrentDate = sdformat.parse(NormalUserHomePageController.sClock);
		Date picked_Date = sdformat.parse(pickedDate);
		int hour = Integer.parseInt(hourtxt.getValue());
		int minute = Integer.parseInt(minutetxt.getValue());
		
		//check if picked date and hour is legal
		if ((hours < hour && CurrentDate.compareTo(picked_Date) == 0) || (minutes < minute && hours == hour && CurrentDate.compareTo(picked_Date) == 0)
				|| CurrentDate.compareTo(picked_Date) < 0) {

			pickup = new Pickup(ChatClient.user, datetxt.getValue().toString(), hourtxt.getValue(),
					minutetxt.getValue());
			Pickup = true;
		} 
		else {
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

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		PaymentNormalUserController PNUC = new PaymentNormalUserController();
		PNUC.start(primaryStage);

	}

	public void start(Stage primaryStage) throws Exception {
		Pickup = false;
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/PickUpFormFXML.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("PickUp");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//initialize hours from 0-23
		int i;
		for (i = 0; i < 24; i++) {
			if (i < 10)
				hourtxt.getItems().add("0" + String.valueOf(i));
			else
				hourtxt.getItems().add(String.valueOf(i));
		}
		
		//initialize minutes from 0-59
		for (i = 0; i < 60; i++) {
			if (i < 10)
				minutetxt.getItems().add("0" + i);
			else
				minutetxt.getItems().add(String.valueOf(i));
		}
	}

}
