package server;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import common.clientDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ServerPortFrameController implements Initializable {
	private URL location;
	private ResourceBundle resources;
	static EchoServer ech;
	private Label Host = new Label();
	private Label IP = new Label();
	private Label Status = new Label();
	private Label Hosttxt = new Label();
	private Label IPtxt = new Label();
	private Label Statustxt = new Label();
	public static boolean c = false;
	@FXML
	private Button extebt;

	@FXML
	private Button startserverbt;
	static ArrayList<String> DBInfo =new ArrayList<>();
	@FXML
	private Button importdatabt;

	@FXML
	private TextField serverip;

	@FXML
	private TextField DBtable;

	@FXML
	private TextField Bitemetable;

	@FXML
	private TextField DBpassword;
	@FXML
	TableView<clientDetails> tableServer;
	@FXML
	private TableColumn<clientDetails, String> colHostName;
	@FXML
	private TableColumn<clientDetails, String> colIP;
	@FXML
	private TableColumn<clientDetails, String> colStatus;
	private static ObservableList<clientDetails> clients = FXCollections.observableArrayList();
	private static ArrayList<clientDetails> clientArrayList = new ArrayList<clientDetails>();

	public void start(Stage primaryStage) throws Exception {
		IP.setText("IP: ");
		Host.setText("Host: ");
		Status.setText("Status: ");
		Hosttxt.setText(" ");
		IPtxt.setText(" ");
		Statustxt.setText(" ");

		FXMLLoader loader = new FXMLLoader();

		Parent root = loader.load(getClass().getResource("/server/serverFXML.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		try {
			serverip.setText(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBtable.setText("assignment3");
		Bitemetable.setText("biteme_data");
		DBpassword.setText("Aa123456");
		DBInfo.add(DBtable.getText());
		DBInfo.add(Bitemetable.getText());
		DBInfo.add(DBpassword.getText());

		importdatabt.setDisable(true);
		extebt.setDisable(true);

		colHostName.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("hostName"));
		colIP.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("ip"));
		colStatus.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("Status"));
		tableServer.setItems(clients);
	}

	public void startServerBtn(ActionEvent event) throws Exception {
		ServerUI.runServer(ServerUI.DEFAULT_PORT);
		ech = new EchoServer(5555);
		importdatabt.setDisable(false);
		extebt.setDisable(false);
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		mysqlConnection.deletealldata();
		mysqlConnection.LogOutAllAccounts();
//		mysqlConnection.DeleteAllReports();
		System.exit(0);
	}

	@FXML
	void importdata(ActionEvent event) {
		ech.importdata();
		Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
		importdatabt.setDisable(true);
		startserverbt.setDisable(true);
		alert.setTitle("Message");
		alert.setContentText("Import Data Done successfully. ");
		alert.getDialogPane().setPrefSize(400, 200);
		alert.showAndWait();
	}

	public void UpdateClient(InetAddress Host, String IP, String Status) {

		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clientDetails client = new clientDetails(Host.getHostName(), IP, Status);
				clients.add(client);
				// clientArrayList.add(new clientDetails(Host.toString(),IP,Status));
			}
		});
	}

	public void disconnect() {
		tableServer.getItems().clear();
	}
}