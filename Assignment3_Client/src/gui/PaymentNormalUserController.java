package gui;

import java.net.URL;
import java.util.ResourceBundle;
import client.ChatClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * User confirms his information before the payment
 * User sees his W4C number and his employer name
 * @author asem
 *
 */
public class PaymentNormalUserController implements Initializable {

	/**
	 * Enters W4C number
	 */
	@FXML
	private TextField w4cnumtxt;

	/**
	 * Enter employer name
	 */
	@FXML
	private TextField employertxt;

	@FXML
	private Text errtxt;

	@FXML
	private Text employerlbl;

	/**
	 * go back to pick up or delivery page depends on where did the user come from
	 * @param event
	 * @throws Exception
	 */
	@FXML
	
	void BackFunction(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		if (PickUpFormController.Pickup == true) {
			PickUpFormController PUFC = new PickUpFormController();
			PUFC.start(primaryStage);
		}
		if (DeliveryInfoController.Delivery == true) {
			DeliveryInfoController DIC = new DeliveryInfoController();
			DIC.start(primaryStage);
		}
	}

	/**
	 * Confirm Details, Go to show all order information
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void SubmitFunction(ActionEvent event) throws Exception {
		// if w4c text field is empty
		if (String.valueOf(w4cnumtxt.getText()).equals("")) {
			errtxt.setText("Please enter code");
			return;
		}
		// if given wrong w4c code
		if (!Integer.valueOf(w4cnumtxt.getText()).equals(ChatClient.w4c.getCode())) {
			errtxt.setText("Wrong W4C code");
			return;
		}
		// if business user left employer text field empty
		if (ChatClient.user.getUserType().equals("Bussiness") && String.valueOf(employertxt.getText()).equals("")) {
			errtxt.setText("Please enter your employer");
			return;
		}

		// if business user entered wrong employer name
		if (ChatClient.user.getUserType().equals("Bussiness")
				&& !String.valueOf(employertxt.getText()).equals(ChatClient.Bussinessuser.getCompany())) {
			errtxt.setText("Wrong employer");
			return;
		}
		// need to check if employer exists
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		ShowInfoOfOrderController SIOO = new ShowInfoOfOrderController();
		SIOO.start(primaryStage);
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/PaymentNormalUserFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("QR payment");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 *Initialize all fields details
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// if user is not business they hide employer text field
		w4cnumtxt.setText(String.valueOf(ChatClient.w4c.getCode()));
		employerlbl.setText("");
		if (ChatClient.user.getUserType().equals("Bussiness")) {
			employerlbl.setText("Employer:");
			employertxt.setText(String.valueOf(ChatClient.Bussinessuser.getCompany()));
		}
		if (!ChatClient.user.getUserType().equals("Bussiness")) {
			employertxt.setVisible(false);
			employerlbl.setText("");
		}
		w4cnumtxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,3}?")) {
					w4cnumtxt.setText(oldValue);
				}
			}
		});
	}

}
