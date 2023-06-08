package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.RestaurantManager;
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
 * this method to show taxes
 * @author asem
 *
 */
public class ShowMonthlyTaxesController implements Initializable{

    @FXML
    private ComboBox<Integer> Year;

    @FXML
    private ComboBox<Integer> Month;

    @FXML
    private Text StatusText;

    /**
     * this method show taxes
     * @param event
     */
    @FXML
    void ShowTaxes(ActionEvent event) {
    	if(Year==null) {
    		StatusText.setText("Please enter year");
    		return;
    	}
    	if(Month==null) {
    		StatusText.setText("Please enter month");
    		return;
    	}
		MessagesClass msg = new MessagesClass(Messages.ShowTaxes,RestaurantManagerHomePage.restaurantmanager.getIDRestaurant(),Year.getValue(),Month.getValue(),null);
		ClientUI.chat.accept(msg);
		String str = ChatClient.ErrorMessage;
		System.out.println(str);
		if(str!=null) {
			if(str.equals("NotPassed")) {
				StatusText.setText("This month not passed yet");
	    		return;
			}
			if(str.equals("NotOpend")) {
				StatusText.setText("The restaurant was close in this month");
	    		return;
			}
			String[] splitStr = str.split("\\s+");
			StatusText.setText("The tax is " + splitStr[2]  + "% \nBefore the tax the InCome was: " + splitStr[0] +" \n After the tax the InCome is: "+ splitStr[1]);
			return;
		}
    }
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/ShowMonthlyTaxes.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Show taxes");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
    /**
     * return back to RestaurantManagerHomePage (permitted worker) 
     * @param event
     */
    @FXML
    void BackBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		RestaurantManagerHomePage aFrame1 = new RestaurantManagerHomePage();
		Stage primaryStage11 = new Stage();
		try {
			aFrame1.start(primaryStage11);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 *initialize function for drawing needed thing in before opening the page
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Month.getItems().removeAll(Month.getItems());
		Month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2023, 2022, 2021, 2020, 2019, 2018);
		
	}

}
