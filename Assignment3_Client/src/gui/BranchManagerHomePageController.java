package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.BranchManager;
import common.Messages;
import common.MessagesClass;
import javafx.animation.Animation;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author moham
 *
 */
public class BranchManagerHomePageController implements Initializable {

	/**
	 * branchmanager to put all the information of the branch and save it
	 */
	public static BranchManager branchManager;
	/**
	 * use to clock text
	 */
	@FXML
	private Text clock = new Text();
	public static String sClock;
	@FXML
	private Button SendPDF;

	@FXML
	private Button SendToCEO;

	@FXML
	private ComboBox<Integer> Year;

	@FXML
	private ComboBox<String> Months;
	@FXML
	private Text StatusText;

	/**
	 * to display the location of manager
	 */
	@FXML
	private Text locationofmanager;

	/**
	 * pathtext the path
	 */
	@FXML
	private Text pathtext;

	static BranchManager getBranchManager() {
		return branchManager;
	}

	public static void setBranchManager(BranchManager branchManager) {
		BranchManagerHomePageController.branchManager = branchManager;
	}

	@FXML
	private Text managername;

	/**
	 * @param primaryStage to start the qui and display it
	 * @throws Exception this method to display the gui and start it
	 */
	@FXML
	void SendPDFBtn(ActionEvent event) {
		MessagesClass msg = new MessagesClass(Messages.SendPDFToCEO, ChatClient.branchManager.getLocation(),
				Year.getValue(), Months.getValue());
		ClientUI.chat.accept(msg);
		String str = (String) ChatClient.ErrorMessage;
		if (str != null) {
			if(str.equals("NotPassed")) {
				StatusText.setText("This month not passed yet");
				return;
			}
			if(str.equals("NotOpend")) {
				StatusText.setText("In this month the restaurant was close");
				return;
			}
			if(str.equals("NoSales")) {
				StatusText.setText("In is month the restaurant has no sales");
				return;
			}
			if(str.equals("Updated")) {
				StatusText.setText("The PDF sent to CEO");
			}
		}
		SendPDF.setVisible(false);
		Year.setVisible(false);
		Months.setVisible(false);
	}

	@FXML
	void SendToCEOBtn(ActionEvent event) {
		SendPDF.setVisible(true);
		Year.setVisible(true);
		Months.setVisible(true);
	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/BranchManager_Home_Page.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}



	/**
	 * @param event report action button to go to report page
	 */
	@FXML
	void report(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ReportsForManager aFrame = new ReportsForManager();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param event action of button inside to list to confirm the company
	 */
	@FXML
	void confirmCompany(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ConfirmCompanyController aFrame = new ConfirmCompanyController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event logout button
	 * @throws IOException return back to login page
	 */
	@FXML
	void logout(ActionEvent event) throws IOException {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		stage.close();
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
		LogInForm aFrame = new LogInForm(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start1(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param event use to accept account button confirm restaurant and accept it
	 */
	@FXML
	void AcceptRestaurant(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		AcceptRestaurat aFrame = new AcceptRestaurat(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param event action of change button switch to ChangeUserPermissionsPage and
	 *              change the status (active frozen ....)
	 */
	@FXML
	void ChangeUserPermissions(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ChangeUserPermissionsPage aFrame = new ChangeUserPermissionsPage();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event use to accept account button to see the external users that
	 *              registered and manager choose with type to be
	 */
	@FXML
	void CreateNewNormalAccount(ActionEvent event) {
		ClientUI.chat.accept(new MessagesClass(Messages.GotW4C, null));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		CreateNewNormalAccountController aFrame = new CreateNewNormalAccountController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *Initialize all the parameters and javaFx fields of the controller
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Months.getItems().removeAll(Months.getItems());
		Months.getItems().addAll("1,2,3", "4,5,6", "7,8,9", "10,11,12");
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2023, 2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014);
		SendPDF.setVisible(false);
		Year.setVisible(false);
		Months.setVisible(false);
		pathtext.setText("LogIn->BranchManagerHomePage");
		MessagesClass msg = new MessagesClass(Messages.GetBranchManager,
				ChatClient.user);/** sed to server to get all the data */
		ClientUI.chat.accept(msg);
		branchManager = ChatClient.branchManager;
		locationofmanager.setText(branchManager.getLocation());/** put the location */
		managername.setText(ChatClient.branchManager.getFistName());
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		/** clock */
		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				clock.setText(LocalDateTime.now().format(format));
				sClock = String.valueOf(LocalDateTime.now().format(format));
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

}
