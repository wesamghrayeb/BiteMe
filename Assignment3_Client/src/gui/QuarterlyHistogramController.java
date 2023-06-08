package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.HistogramForCEO;
import common.Messages;
import common.MessagesClass;
import common.Resturaunt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Bashar Bashir
 * Controller class for creating Quarterly Histogram
 */
public class QuarterlyHistogramController implements Initializable { 

	/**
	 * BranchManager combobox for choosing report  Restaurant name
	 */
	@FXML
    private ComboBox<String> RestaurantName;
  
	/**
	 * BrnachManager combobox for choosing report Year
	 */
	@FXML
	private ComboBox<Integer> Year;

	
	/** 
	 * BranchManager combobox for choosing report quarter
	 */
	@FXML
	private ComboBox<String> Months;

	/**
	 * Text for appearing if there is any wrong thing
	 */
	@FXML
	private Text StatusText;

	/**
	 * @param primaryStage
	 * @throws Exception
	 * Start function for drawing the needed page
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/QuarterlyHistogram.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setResizable(false);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Histogram");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param event
	 * @throws Exception
	 * Function for drawing histogram
	 */
	@FXML
	void GetHistogram(ActionEvent event) throws Exception {
		if (RestaurantName.getValue() == null) {
			StatusText.setText("Please enter area");
			return;
		}
		if (Year.getValue() == null) {
			StatusText.setText("Please enter year");
			return;
		}
		if (Months.getValue() == null) {
			StatusText.setText("Please enter months");
			return;
		}
		MessagesClass msg = new MessagesClass(Messages.getReportForHistogram, RestaurantName.getValue(),Year.getValue(), Months.getValue());
		ClientUI.chat.accept(msg);
		String str = (String) ChatClient.ErrorMessage;
		if (str != null) {
			if (str.equals("NotPassed")) {
				StatusText.setText("In this quaterly not passed yet");
				return;
			}
			if (str.equals("NotOpend")) {
				StatusText.setText("In this quaterly the restaurant was close");
				return;
			}
		}
		HistogramForCEO.HistogramArray = ChatClient.HistogramArray;
		HistogramForCEO.Month = Months.getValue();
		HistogramForCEO.year = Year.getValue();
		new HistogramForCEO().start(new Stage());
	}

	/**
	 *Initialize for drawing needed things before opening the page
	 *Set years in combobox
	 *set months in combobox
	 *set all the confirm restaurants in combobox
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Resturaunt> AllRestauratns = new ArrayList<>();;
		Months.getItems().removeAll(Months.getItems());
		Months.getItems().addAll("1,2,3", "4,5,6", "7,8,9", "10,11,12");
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014);
		System.out.println(ChatClient.getallresturant);
		MessagesClass msg = new MessagesClass(Messages.GetConfirmRestaurants,"North");
		ClientUI.chat.accept(msg);
		System.out.println(ChatClient.getallresturant);
		AllRestauratns.addAll(ChatClient.getallresturant);
		MessagesClass msg1 = new MessagesClass(Messages.GetConfirmRestaurants,"Center");
		ClientUI.chat.accept(msg1);
		AllRestauratns.addAll(ChatClient.getallresturant);
		MessagesClass msg2 = new MessagesClass(Messages.GetConfirmRestaurants,"South");
		ClientUI.chat.accept(msg2);
		AllRestauratns.addAll(ChatClient.getallresturant);
		RestaurantName.getItems().removeAll(RestaurantName.getItems());
		for (int i = 0; i < AllRestauratns.size(); i++)
			RestaurantName.getItems().addAll(AllRestauratns.get(i).getResturaunt_Name());
	}

	/**
	 * @param ActionEvent
	 * function for logout from Quarter page
	 */
	@FXML
	void logoutceo(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		CeoHomePageController.CEOStage.show();
	}

}