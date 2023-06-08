package gui;

import java.io.IOException;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author moham
 *
 */
public class LogInForm {

	/**
	 * user text in order to put user name
	 */
	@FXML
	private TextField usertxt;
	/**
	 * status text error message
	 */
	@FXML
	private Text statustext;

	/**
	 * password text in order to put user password
	 */
	@FXML
	private PasswordField passwordtxt;

	/**
	 * @param event=action event of button exit
	 * @throws IOException this method to close login page when clicked in button
	 */
	@FXML
	void ExitBt(ActionEvent event) throws IOException {
		System.out.println("Close LogIn Page");
		ClientUI.chat.client.quit();
		System.exit(0);
	}

	@FXML
	void alertbt(ActionEvent event) {
		Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
		alert.setTitle("Message");
		alert.setContentText("Hi, You are on LogIn Page Please Put Your Username and Password to Executing a Order ");
		alert.getDialogPane().setPrefSize(400, 200);
		alert.showAndWait();
		// System.out.println(updatelist.toString());

	}

	public static User BUser;

	/**
	 * @param event action event of singin
	 * @throws IOException This button method that take user name and password and
	 *                     check the user if registered in bite me or not and and
	 *                     open the correct page by user position in bite me when
	 *                     clicked
	 */
	@FXML
	void singin(ActionEvent event) throws IOException {
		ChatClient.user = null;
		if (usertxt.getText().isEmpty()) {// if username empty
			statustext.setText("Please Enter your username");// error message
			return;
		}
		if (passwordtxt.getText().isEmpty()) {// if password field is empty
			statustext.setText("Please Enter you password");// error message
			return;
		}

		User user = null;
		user = new User(usertxt.getText(), passwordtxt.getText());
		MessagesClass msg = new MessagesClass(Messages.Login, user);// send message to client and take information about
																	// the user
		ClientUI.chat.accept(msg);
		String Status = (String) ChatClient.ErrorMessage;
		if (Status != null) {
			if (Status.equals("LoggedIn")) {
				Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						statustext.setText("");
					}
					
				}));
				fiveseconds.setCycleCount(Timeline.INDEFINITE);
				fiveseconds.play();
				statustext.setText("Your account is logged in");
				return;
			}

			if (Status.equals("Locked")) {
				Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						statustext.setText("");
					}
					
				}));
				fiveseconds.setCycleCount(Timeline.INDEFINITE);
				fiveseconds.play();
				statustext.setText("Your account is locked");
				return;
			}
			if (Status.equals("NotExist")) {
				Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						statustext.setText("");
					}
					
				}));
				fiveseconds.setCycleCount(Timeline.INDEFINITE);
				fiveseconds.play();
				statustext.setText("This account is not exist");
				return;
			}
		}
		if (ChatClient.user != null) {
			BUser = ChatClient.user;
			FXMLLoader loader = new FXMLLoader();
			Stage primaryStage = new Stage();
			user = ChatClient.user;
			switch (ChatClient.user.getUserType()) {// by the type of users
			case "Normal":
				NormalUserHomePageController aFrame = new NormalUserHomePageController();// go to user page
				Stage primaryStage1 = new Stage();
				try {
					aFrame.start(primaryStage1);
				} catch (Exception e) {
					e.printStackTrace();

				}
				break;

			case "Bussiness":
				NormalUserHomePageController business = new NormalUserHomePageController();// go to user page
				Stage businesspage = new Stage();
				try {
					business.start(businesspage);
				} catch (Exception e) {
					e.printStackTrace();

				}
				break;

			case "BranchManager":
				BranchManagerHomePageController aFrame1 = new BranchManagerHomePageController();// go to branch manager
																								// page

				Stage primaryStage11 = new Stage();
				try {
					aFrame1.start(primaryStage11);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "HR":
				firstHRpage aFrame22 = new firstHRpage();
				Stage primaryStage22 = new Stage();
				try {
					aFrame22.start(primaryStage22);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "RestaurantManager":
				MessagesClass msg1 = new MessagesClass(Messages.getRestaurantManager, ChatClient.user);
				ClientUI.chat.accept(msg1);
				System.out.println(ChatClient.restaurantManager);
				if (ChatClient.restaurantManager != null) {
					RestaurantManagerHomePage aFrame221 = new RestaurantManagerHomePage();
					Stage primaryStage221 = new Stage();
					try {
						aFrame221.start(primaryStage221);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					statustext.setText("Your Restaurant not Registered yet, request from Branch Manager");																			
					ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
					return;
				}

				break;
			case "Worker":
				MessagesClass msg11 = new MessagesClass(Messages.getworker, ChatClient.user);
				ClientUI.chat.accept(msg11);
				
				if (ChatClient.WorkerUser != null) {
				RestaurantWorkerHomePage aFrame21 = new RestaurantWorkerHomePage();// go to worker page
				Stage primaryStage21 = new Stage();
				try {
					aFrame21.start(primaryStage21);
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				else {
					statustext.setText("Your Restaurant not Registered yet, request from Branch Manager");																			
				ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
				return;}
				break;
			case "CEO":
				CeoHomePageController aFrame12 = new CeoHomePageController();// go to ceo page
				Stage primaryStage12 = new Stage();
				try {
					aFrame12.start(primaryStage12);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				statustext.setText("Your Account not Registered yet, request from Branch Manager");// error message
				ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
				return;
			}
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();

		}
	}

	/**
	 * @param primaryStage to open the qui
	 * @throws Exception start method
	 */
	public void start1(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LogIn.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setTitle("LogIn Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}
}
