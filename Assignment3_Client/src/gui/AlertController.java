package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Alert page when customer wants to pay
 * If customer has refund then they can pay via refund
 * If customer wants to pay with W4C card then pays via W4C card
 * If customer wants to pay with visa then pays via visa
 * Customer can start paying with W4C and if W4C doesn't have enough balance then alert pops to customer so he can confirm/cancel to pay via visa
 * @author asem
 *
 */
public class AlertController implements Initializable {

	@FXML
	private Button W4C;
	@FXML
	private Button visaorw4cbtn;

	/**
	 * Customer pays with Refund money if he have in his balance
	 * If he doens't have enough money in his refund he can continue to pay in his W4C card
	 * If W4C is not enough then he can continue with VISA
	 * @param event
	 */
	@FXML
	void RefundFunction(ActionEvent event) {
		if (ChatClient.user.getUserType().equals("Bussiness")) {
			if (ShowInfoOfOrderController.BussinessPrice >= 0) {
				System.out.println(ShowInfoOfOrderController.BussinessPrice);
				System.out.println(ChatClient.Bussinessuser.getID());
				MessagesClass msg = new MessagesClass(Messages.PayWithRefund, ChatClient.Bussinessuser.getID(),
						ShowInfoOfOrderController.BussinessPrice, RestaurantController.restaurant.getResturauntID());
				ClientUI.chat.accept(msg);
			}
			if (ShowInfoOfOrderController.BussinessPrice < 0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Payment");
				alert.setHeaderText("Ops you don't have enough money in your refund");
				alert.setContentText("Pay with refund and W4C or cancel");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					if (ChatClient.Bussinessuser.getW4c().getMoney()
							+ ChatClient.getRefund >= AdditionsController.OverAllSum) {
						ChatClient.Bussinessuser.getW4c().setMoney(ChatClient.Bussinessuser.getW4c().getMoney()
								- AdditionsController.OverAllSum + ChatClient.getRefund);
						MessagesClass msg = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
						ClientUI.chat.accept(msg);
						ShowInfoOfOrderController.BussinessPrice = 0;
						MessagesClass msg1 = new MessagesClass(Messages.PayWithRefund, ChatClient.Bussinessuser.getID(),
								ShowInfoOfOrderController.BussinessPrice,
								RestaurantController.restaurant.getResturauntID());
						ClientUI.chat.accept(msg1);
					}

					if (ChatClient.Bussinessuser.getW4c().getMoney()
							+ ChatClient.getRefund <= AdditionsController.OverAllSum) {

						Alert alert1 = new Alert(AlertType.CONFIRMATION);
						alert1.setTitle("Payment");
						alert1.setHeaderText("Ops you don't have enough money in your W4C balance");
						alert1.setContentText("Pay with W4C and the rest with your credit card?");
						Optional<ButtonType> result1 = alert1.showAndWait();
						if (result1.get() == ButtonType.OK) {
							ChatClient.Bussinessuser.getW4c().setMoney(0);
							MessagesClass msg = new MessagesClass(Messages.updateW4CforBussiness,
									ChatClient.Bussinessuser);
							ClientUI.chat.accept(msg);
							ShowInfoOfOrderController.BussinessPrice = 0;
							MessagesClass msg1 = new MessagesClass(Messages.PayWithRefund,
									ChatClient.Bussinessuser.getID(), ShowInfoOfOrderController.BussinessPrice,
									RestaurantController.restaurant.getResturauntID());
							ClientUI.chat.accept(msg1);

						} else {
							return;
						}

					}

				}
				if (result.get() == ButtonType.CANCEL) {
					return;
				}

			}

		}
		if (ChatClient.user.getUserType().equals("Normal")) {
			double price = ChatClient.getRefund - AdditionsController.OverAllSum;
			System.out.println(price);
			if (price < 0) {
				price = 0;

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Payment");
				alert.setHeaderText("Ops you don't have enough money in your refund");
				alert.setContentText("Pay with refund and visa or cancel");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

				}
				if (result.get() == ButtonType.CANCEL) {
					return;
				}
			}

			MessagesClass msg = new MessagesClass(Messages.PayWithRefund, ChatClient.user.getID(), price,
					RestaurantController.restaurant.getResturauntID());
			ClientUI.chat.accept(msg);

		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		if (TypeOfOrderController.ingruop == true)
			TypeOfOrderController.inPayment = true;
		if (TypeOfOrderController.ingruop == false) {
			ShowInfoOfOrderController.ShowInfoOfOrderStage.close();
			Stage primaryStage = new Stage();
			PaymentSuccessfulController PSC = new PaymentSuccessfulController();

			try {
				PSC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Customer can pay with VISA 
	 * @param event
	 */
	@FXML
	void VisaFunction(ActionEvent event) {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		if (TypeOfOrderController.ingruop == true)
			TypeOfOrderController.inPayment = true;
		if (TypeOfOrderController.ingruop == false) {
			ShowInfoOfOrderController.ShowInfoOfOrderStage.close();
			Stage primaryStage = new Stage();
			PaymentSuccessfulController PSC = new PaymentSuccessfulController();
			try {
				PSC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/AlertFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Choose Payment");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Customer pay with his W4C card
	 * @param event
	 */
	@FXML
	void W4CFunction(ActionEvent event) {
		if (ChatClient.user.getUserType().equals("Bussiness")) {
			if (ShowInfoOfOrderController.BussinessPrice >= 0) {
				ShowInfoOfOrderController.PayWithRefund = true;
			}
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			if (TypeOfOrderController.ingruop == false)
				TypeOfOrderController.inPayment = true;
		}
		if (ChatClient.user.getUserType().equals("Bussiness")
				&& AdditionsController.OverAllSum > ChatClient.Bussinessuser.getW4c().getMoney()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Payment");
			alert.setHeaderText("Ops you don't have enough money in your W4C balance");
			alert.setContentText("Pay with W4C and the rest with your credit card?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				ChatClient.Bussinessuser.getW4c().setMoney(0);
				MessagesClass msg = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
				ClientUI.chat.accept(msg);
			} else {
				return;
			}
		}
		// if business user have the required money in his w4c card -> update w4c
		// balance
		if (ChatClient.user.getUserType().equals("Bussiness")
				&& AdditionsController.OverAllSum < ChatClient.Bussinessuser.getW4c().getMoney()) {
			ChatClient.Bussinessuser.getW4c()
					.setMoney(ChatClient.Bussinessuser.getW4c().getMoney() - AdditionsController.OverAllSum);
			MessagesClass msg = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
			ClientUI.chat.accept(msg);
		}
		if (TypeOfOrderController.ingruop == true)
			TypeOfOrderController.inPayment = true;
		if (TypeOfOrderController.ingruop == false) {
			ShowInfoOfOrderController.ShowInfoOfOrderStage.close();
			Stage primaryStage = new Stage();
			PaymentSuccessfulController PSC = new PaymentSuccessfulController();
			try {
				PSC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *Initialize all data to specific user type
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (ChatClient.user.getUserType().equals("Bussiness")) {
			visaorw4cbtn.setDisable(true);
			visaorw4cbtn.setVisible(false);
			W4C.setDisable(false);
			W4C.setVisible(true);

		}

		if (ChatClient.user.getUserType().equals("Normal")) {

			visaorw4cbtn.setDisable(false);
			visaorw4cbtn.setVisible(true);
			W4C.setDisable(true);
			W4C.setVisible(false);
		}

	}
}
