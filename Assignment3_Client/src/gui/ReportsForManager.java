package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import common.HistogramController;
import common.InComeReport;
import common.Messages;
import common.MessagesClass;
import common.OrdersReport;
import common.PerformenceReport;
import common.ReportCreator;
import javafx.fxml.Initializable;

/**
 * @author Bashar Bashir
 * Report branchmanager class
 *
 */
public class ReportsForManager implements Initializable { 

	/**
	 * Comobox for choosing Report type : (InCome,PerformanceReport,Order)
	 */
	@FXML
	private ComboBox<String> Reports;

	/**
	 * BranchManager combobox for choosing report month
	 */
	@FXML 
	private ComboBox<Integer> Month;

	/**
	 * BrnachManager combobox for choosing report year
	 */
	@FXML
	private ComboBox<Integer> Year;

	/**
	 * Text for appearing if there is any wrong thing
	 */
	@FXML
	private Text StatusText; 
  
	/**
	 * BranchManager combobox for choosing report  Restaurant name
	 */
	@FXML
	private ComboBox<String> Restaurant;

	/**
	 * @param ActionEvent
	 * Get the Report from DB that the manager wanted
	 * Create Pie Chart for Performence  and Order report
	 * Create BarChart for InCome Report
	 */
	@FXML
	void GetReport(ActionEvent event) {
		if(Reports==null) {
			StatusText.setText("Please enter Report type");
			return;
		}
		if(Month==null) {
			StatusText.setText("Please enter month");
			return;
		}
		if(Year==null) {
			StatusText.setText("Please enter year");
			return;
		}
		if(Restaurant==null) {
			StatusText.setText("Please enter restaurant");
			return;
		}
		if(Reports.getValue().equals("InComeReport")) {
			MessagesClass msg = new MessagesClass(Messages.InComeReportMonthly,Restaurant.getValue(),Year.getValue(),Month.getValue(),ChatClient.branchManager.getLocation());
			ClientUI.chat.accept(msg);
			String str = (String)ChatClient.ErrorMessage;
			System.out.println(str);
			if(str!=null) {
				if(str.equals("NotPassed")) {
					StatusText.setText("This month not passed yet");
					return;
				}
				if(str.equals("NotOpend")) {
					StatusText.setText("In this month the restaurant was close");
					return;
				}
				if(str.equals("NoSales")) {
					StatusText.setText("In the month the restaurant has no sales");
					return;
				}
			}
			StatusText.setText("");
			HistogramController.InComeArray=ChatClient.InComeReportArray;
			try {
				new HistogramController().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(Reports.getValue().equals("OrdersReport")) {
			MessagesClass msg = new MessagesClass(Messages.OrdersReportMonthly,Restaurant.getValue(),Year.getValue(),Month.getValue(),ChatClient.branchManager.getLocation());
			ClientUI.chat.accept(msg); 
			String str = (String)ChatClient.ErrorMessage;
			if(str!=null) {
				if(str.equals("NotPassed")) {
					StatusText.setText("This month not passed yet");
					return;
				}
				if(str.equals("NotOpend")) {
					StatusText.setText("In this month the restaurant was close");
					return;
				}
				if(str.equals("NoSales")) {
					StatusText.setText("In the month the restaurant has no sales");
					return;
				}
			}
			StatusText.setText("");
			ReportCreator.OrderArray=ChatClient.OrdersReportArray;
			ReportCreator.Type="Order";
			new ReportCreator().start(new Stage());
		}
		if(Reports.getValue().equals("PerformenceReport")) {
			System.out.println(Restaurant.getValue()+" "+Month.getValue()+" "+Year.getValue());
			MessagesClass msg = new MessagesClass(Messages.PerformenceReportMonthly,Restaurant.getValue(),Month.getValue(),Year.getValue(),null);
			ClientUI.chat.accept(msg);
			String str = (String)ChatClient.ErrorMessage;
			if(str!=null) {
				if(str.equals("NotPassed")) {
					StatusText.setText("This month not passed yet");
					return;
				}
				if(str.equals("NotOpend")) {
					StatusText.setText("In this month the restaurant was close");
					return;
				}
				if(str.equals("NoSales")) {
					StatusText.setText("In the month the restaurant has no sales");
					return;
				}
			}
			StatusText.setText("");
			ReportCreator.Perf=ChatClient.PerfArray;
			ReportCreator.Type="Performencereports";
			new ReportCreator().start(new Stage()); 
		}	
		
	}

	/**
	 * @param event
	 * @throws IOException
	 * Back for branch manager home page
	 */
	@FXML
	void BackBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/BranchManager_Home_Page.fxml").openStream());
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param primaryStage
	 * @throws Exception
	 * Start function for drawing the needed page
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/ReportsForManager.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 *Initialize for drawing needed things before opening the page
	 *Set years in combobox
	 *set months in combobox
	 *set all the confirm restaurants in combobox
	 *set reports type in combobox
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Month.getItems().removeAll(Month.getItems());
		Month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2023, 2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014);
		Reports.getItems().removeAll(Reports.getItems());
		Reports.getItems().addAll("InComeReport", "OrdersReport", "PerformenceReport");
		MessagesClass msg = new MessagesClass(Messages.GetConfirmRestaurants,ChatClient.branchManager.getLocation());
		ClientUI.chat.accept(msg);
		Restaurant.getItems().removeAll(Restaurant.getItems());
		for(int i=0;i<ChatClient.getallresturant.size();i++) {
			Restaurant.getItems().addAll(ChatClient.getallresturant.get(i).getResturaunt_Name());
		}
	}

}