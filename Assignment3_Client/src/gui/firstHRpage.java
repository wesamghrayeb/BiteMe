package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.HRUser;
import common.Messages;
import common.MessagesClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author wesam
 *
 */
public class firstHRpage implements Initializable {

	/**
	 * static HRuser variable
	 */
	public static HRUser HRManager;
	/**
	 * The username of the HR
	 */
	@FXML
	private Text username;

	/**
	 * Button to send the request to the branch manager
	 */
	@FXML
	private Button request;
	
	/**
	 * start the HRFirstHomePage.fxml gui with title FirstPage
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/HRFirstHomePage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("FirstPage");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	/**
	 * Button action that close the present stage and start the HrHomePageConroller
	 * @param event
	 */
	@FXML
	void NewBussinessBT(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		HrHomePageController aFrame = new HrHomePageController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * initialize the needed parameters before opening the page
	 * take the current HRManager from the ChatClient
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MessagesClass msg = new MessagesClass(Messages.GetHRManager, ChatClient.user);
		ClientUI.chat.accept(msg);
		HRManager = ChatClient.HRmanager1;
		System.out.print(ChatClient.HRmanager1.getCompnay());
		MessagesClass msg1 = new MessagesClass(Messages.CheckCompany, ChatClient.HRmanager1.getCompnay());
		ClientUI.chat.accept(msg1);
		String str = (String) ChatClient.ErrorMessage;
		if (str != null) {
			if (str.equals("companyExist")) {
				request.setVisible(false);
			}
		}
	}

	/**
	 * Button action that close the present stage and update the status of the user to 0 and start the LogInForm
	 * @param event
	 */
	@FXML
	void logOut(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user,0));
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
	 * Send to the ClientUI message that have Message.CompanyRequest and the company of the current HR
	 * @param event
	 */
	@FXML
	void requestBtn(ActionEvent event) {
		ClientUI.chat.accept(new MessagesClass(Messages.CompanyRequest, ChatClient.HRmanager1.getCompnay()));
	}

}
