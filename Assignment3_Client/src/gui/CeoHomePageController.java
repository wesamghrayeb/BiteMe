package gui;

import java.net.URL;
import java.util.ResourceBundle;
import common.CEOuser;
import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Bashar Bashir Controller class for CEO home-page.
 */
public class CeoHomePageController implements Initializable {

	/**
	 * CEO Stage for home page
	 */
	public static Stage CEOStage;

	/**
	 * Class for saving CEO infromation
	 */
	public static CEOuser GetCEO;

	/**
	 * Text for writing CEO name
	 */
	@FXML
	private Text name;

	/**
	 * Stage for CEO report page
	 */
	public static Stage CEO;

	/**
	 * Button for getting histogram
	 */
	@FXML
	private Button histogram;

	
	/**
	 * @param event from ActionEvent type
	 * Function for opening histogram page : button function
	 */
	@FXML
	void Histogram(ActionEvent event) {
		CEOStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		CEOStage.hide();
		QuarterlyHistogramController aFrame = new QuarterlyHistogramController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param event from ActionEvent type
	 * Function for opening PDF list
	 */
	@FXML
	void PDFFiles(ActionEvent event) {
		CEOStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		CEOStage.hide();
		QuaterPDFCEOController aFrame = new QuaterPDFCEOController();
		CEO = new Stage();
		try {
			aFrame.start(CEO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param primaryStage
	 * @throws Exception
	 * Start function for drawing the needed page
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/CEO_Home_Page.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("CEO Home Page");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show(); 
 
	}

	/**
	 * @param event from ActionEvent type
	 * logout function from CEO home page
	 * 
	 */
	@FXML
	void logoutceo(ActionEvent event) {
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
		ChatClient.GetCEO = null;
	}

	/**
	 *initialize function for drawing needed thing in before opening the page
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}