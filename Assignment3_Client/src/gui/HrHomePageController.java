package gui;

import java.net.URL;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import common.W4CBussiness;
import common.BussinessUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * HR controller to accept account and give w4c balance
 * @author asem
 *
 */
public class HrHomePageController implements Initializable {

	String sqltable = "bussinessuser";
	/**
	 * text for the Hr name
	 */
	@FXML
	private Text HRusername;
	/**
	 * checkbox for the daily amount
	 */
	@FXML
    private CheckBox dailycheckbox;

    /**
     * checkbox for the monthly amount
     */
    @FXML
    private CheckBox monthlycheckbox;

    /**
     * text to put the amount value
     */
    @FXML
    private TextField Amountval;
	/**
	 * table for the Bussiness Users info
	 */
	@FXML
	private TableView<BussinessUser> table;

	/**
	 * ID column
	 */
	@FXML
	private TableColumn<BussinessUser, String> IDcl;

	/**
	 * first name column
	 */
	@FXML
	private TableColumn<BussinessUser, String> firstnamecl;

	/**
	 * last name column
	 */
	@FXML
	private TableColumn<BussinessUser, String> lastnamecl;

	/**
	 * email column
	 */
	@FXML
	private TableColumn<BussinessUser, String> emailcl;

	/**
	 * phone number column
	 */
	@FXML
	private TableColumn<BussinessUser, String> phonecl;
	@FXML
	private TableColumn<BussinessUser, String> usernamecl;

	/**
	 * company column
	 */
	@FXML
	private TableColumn<BussinessUser, String> passwordcl;

	/**
	 * text for the error message
	 */
	@FXML
	private Text statustext;
	
	@FXML
	private TableColumn<BussinessUser, String> districtcl;
	private static ObservableList<BussinessUser> temptable = FXCollections.observableArrayList();
	ObservableList<BussinessUser> listM;
	ObservableList<BussinessUser> dataList;
	private URL location;
	private ResourceBundle resources;
	/**
	 * Text field for the ID
	 */
	@FXML
	private TextField getID;
	/**
	 * Take the info about the specific user nad make for them amount
	 * for the daily/monthly (the HR decides) ceiling fot the business user
	 * @param event
	 */
	@FXML
	void acceptbt(ActionEvent event) {
		String ID;
		ID = getID.getText();
		if(Amountval.getText().equals(""))
		{
			statustext.setText("Please enter amount");
			return;
		}
		if (!ID.isEmpty()) {
			if(monthlycheckbox.isSelected()&&dailycheckbox.isSelected())
			{
				statustext.setText("Error, Please Choose One.");
				return;
			}
			
			if(dailycheckbox.isSelected()) {
			User user = new User(ID, null, null, null, 0, 0,null);
			W4CBussiness w4cb = new W4CBussiness(0, null,Integer.parseInt(ID),0.0,Double.parseDouble(Amountval.getText()),"D");
 			MessagesClass msg1 = new MessagesClass(Messages.updateandinsidebussinesstousers, user,w4cb);
			ClientUI.chat.accept(msg1);
			statustext.setText("Updated");
			initialize(location, resources);}
			if(monthlycheckbox.isSelected())
			{
				User user = new User(ID, null, null, null, 0, 0,null);
				W4CBussiness w4cb = new W4CBussiness(0, null,Integer.parseInt(ID),0.0,Double.parseDouble(Amountval.getText()),"M");
				System.out.println(user);
	 			MessagesClass msg1 = new MessagesClass(Messages.updateandinsidebussinesstousers, user,w4cb);
				ClientUI.chat.accept(msg1);
				statustext.setText("Updated");
				initialize(location, resources);
			}
		} else
		{
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					statustext.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			statustext.setText("Please put ID .");
		}
	}

	/**
	 * button to delete the clicked user in the table
	 * @param event
	 */
	@FXML
	void deletbt(ActionEvent event) {
		if (getID.getText().isEmpty()) {
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					statustext.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			statustext.setText("Please,Put ID.");
		} else {
			MessagesClass msg1 = new MessagesClass(Messages.deleteid, getID.getText(), sqltable);
			ClientUI.chat.accept(msg1);
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					statustext.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			statustext.setText("Deleted");
			table.getItems().clear();
			initialize(location, resources);
		}
	}

	/**
	 * Start the HRHomePage gui with title Manager home page
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		// FXMLLoader loader = new FXMLLoader();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/HRHomePage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Button action that close the present stage and start the firstHRpage
	 * @param event
	 */
	@FXML
	void logOut(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		firstHRpage aFrame = new firstHRpage();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 *Initialize the needed parameters before opening the page
	 *take the current users that belong to the same HR company that filter up helped with ChatClient
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Amountval.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,4}?")) {
					Amountval.setText(oldValue);
				}
			}
		});

		MessagesClass msg1 = new MessagesClass(Messages.GettempData, null,firstHRpage.HRManager.getCompnay());
		ClientUI.chat.accept(msg1);
		System.out.println(ChatClient.bussinessUser);
		listM = FXCollections.observableArrayList(ChatClient.bussinessUser);
		IDcl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("ID"));
		firstnamecl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("FirstName"));
		lastnamecl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("LastName"));
		emailcl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("PhoneNumber"));
		passwordcl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("Company"));
		table.setItems(listM);
		settext();
	}

	/**
	 * Update the table after accept or delete the user
	 * @param iD
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param email
	 * @param company
	 */
	public void Updatetable(String iD, String firstName, String lastName, String phoneNumber, String email, 
			String company) {

		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				BussinessUser temp = new BussinessUser(iD, firstName, lastName, phoneNumber, email, 0,company, 0);
				System.out.println("updated: " + temp);

			}
		});
	}

	/**
	 * Function for taking the business user on the mouse click
	 */
	private void settext() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				BussinessUser w = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				getID.setText(w.getID());
			}

		});

	}

}