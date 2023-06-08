package gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.OrderHistory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Show all order information and status that were made by the user 
 * User can see the status of his order(Late,Ready,Delivery on it's way)
 * User confirm the order after he picks it up or when it is delivered
 * @author asem
 *
 */
public class OrderHistoryController implements Initializable {
	ObservableList<OrderHistory> listM;
	ObservableList<OrderHistory> dataList;
	@FXML
	private TableView<OrderHistory> orederTable;

	@FXML
	private TableColumn<OrderHistory, Integer> Ordernumbercl;

	@FXML
	private TableColumn<OrderHistory, String> ResName;

	@FXML
	private TableColumn<OrderHistory, Double> PriceCL;

	@FXML
	private TableColumn<OrderHistory, String> estimatedtimecl;

	@FXML
	private TableColumn<OrderHistory, String> Statuscl;

	@FXML
	private Button ConfirmOrder;

	@FXML
	private Text errtxt;
	public static int idorder;
	static OrderHistory order;
	URL location;
	ResourceBundle resources;
	

	public void start(Stage primaryStage) throws Exception {
		
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/OrdersHistoryFXML.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Orders History");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML
	void BackToHomePage(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

		NormalUserHomePageController aFrame = new NormalUserHomePageController();
		Stage primaryStage = new Stage();
		aFrame.start(primaryStage);
	}

	@FXML
	void ConfirmOrder(ActionEvent event) throws ParseException {
		if (!order.getStatuscl().equals("Delivery On it's way")&&!order.getStatuscl().equals("Ready") && !order.getStatuscl().equals("Late (Ready)") && !order.getStatuscl().equals("Late (Delivery On it's way)")) {
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Order not ready yet");
			return;
		}
		// check if order is delivery
		// if delivery delete from delivery table
		MessagesClass msg = new MessagesClass(Messages.CheckDelivery, idorder);
		ClientUI.chat.accept(msg);

		// returns pickup time from orders table
		msg = new MessagesClass(Messages.GetDeliveryDate, idorder);
		ClientUI.chat.accept(msg);

		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date CurrentDate = sdformat.parse(NormalUserHomePageController.sClock);

		Date PickUpTime = sdformat.parse(order.getEstimatedtimecl());

		
		msg = new MessagesClass(Messages.GetRestaurantID, idorder);
		ClientUI.chat.accept(msg);
		
		if (order.getStatuscl().equals("Late (Ready)") || order.getStatuscl().equals("Late (Delivery On it's way)") )// PickUpTime<CurrentDate
		{

			msg = new MessagesClass(Messages.RefundCustomer, ChatClient.RestaurantID, order.getPriceCL() * 0.5,
					ChatClient.user.getID());
			ClientUI.chat.accept(msg);
		}

		// delete order from order table && order_item && order_item_addition

		msg = new MessagesClass(Messages.DeleteOrder, idorder);
		ClientUI.chat.accept(msg);
		
		msg = new MessagesClass(Messages.SendToPerformencereports, order.getResName(),order.getStatuscl());
		ClientUI.chat.accept(msg);
		
		initialize(location, resources);
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MessagesClass msg1 = new MessagesClass(Messages.getUserOrders, null, ChatClient.user.getID());
		ClientUI.chat.accept(msg1);
		listM = FXCollections.observableArrayList(ChatClient.orderhis);/** array list of normal users */
		Ordernumbercl.setCellValueFactory(new PropertyValueFactory<OrderHistory, Integer>("Ordernumbercl"));
		ResName.setCellValueFactory(new PropertyValueFactory<OrderHistory, String>("ResName"));
		PriceCL.setCellValueFactory(new PropertyValueFactory<OrderHistory, Double>("PriceCL"));
		estimatedtimecl.setCellValueFactory(new PropertyValueFactory<OrderHistory, String>("estimatedtimecl"));
		Statuscl.setCellValueFactory(new PropertyValueFactory<OrderHistory, String>("Statuscl"));
		orederTable.setItems(listM);
		settext();
	}

	private void settext() {
		orederTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				order = orederTable.getItems().get(orederTable.getSelectionModel().getSelectedIndex());
				idorder = order.getOrdernumbercl();
				// MessagesClass msg11 = new MessagesClass(Messages.getuser, null,
				// w.getUserid());
				// ClientUI.chat.accept(msg11);

			}
		});
	}

}
