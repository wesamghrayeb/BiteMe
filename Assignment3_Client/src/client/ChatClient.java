// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import gui.DeliveryInfoController;
import gui.ItemsAddition;
import gui.SharedDeliveryController;
import gui.TypeOfOrderController;
import gui.agreeRestaurantrequset;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************
	public static boolean awaitResponse = false;
	/**
	 * Get all restaurant list
	 */
	public static ArrayList<Resturaunt> restaurantList;
	/**
	 * get user
	 */
	public static User user;
	/**
	 * get w4c for normal
	 */
	public static W4CNormal w4c;
	/**
	 * save branchManager
	 */
	public static BranchManager branchManager;
	/**
	 * save normal user details
	 */
	public static Normal normaluser;
	/**
	 * list of all users
	 */
	public static ArrayList<User> getlistofnormalaccount;
	/**
	 * save list of restaurant reports
	 */
	public static ArrayList<RestaurantReport> arrList;
	/**
	 * List for all normal users
	 */
	public static ArrayList<Normal> ArrListToAcceptNewAccounts;
	/**
	 * save business user details
	 */
	public static BussinessUser Bussinessuser;
	/**
	 * get w4c code
	 */
	public static int W4CC;
	/**
	 * save restaurant manager
	 */
	public static RestaurantManager restaurantManager;
	/**
	 * get update error handle
	 */
	public static boolean updateError = false;
	/**
	 * get restaurant details
	 */
	public static Resturaunt Resturaunt;
	/**
	 * get list of all restaurants
	 */
	public static ArrayList<Resturaunt> getallresturant;
	/**
	 * list of all workers for restaurant
	 */
	public static ArrayList<WorkerUser> allworker;
	/**
	 * list of all items
	 */
	public static ArrayList<Item> Items;
	/**
	 * list of all items different constructor
	 */
	public static ArrayList<Item> allItems;
	/**
	 * get list of all users
	 */
	public static ArrayList<User> GetAllUsersFromUsersTable;
	/**
	 * get all companies list
	 */
	public static ArrayList<Company> AllCompany;
	/**
	 * get all companies list
	 */
	public static ArrayList<Company> CompanyList;
	/**
	 * save HR information
	 */
	public static HRUser HRmanager1;
	/**
	 * List of all business users
	 */
	public static ArrayList<BussinessUser> bussinessUser;
	/**
	 * save worker details
	 */
	public static WorkerUser WorkerUser;
	/**
	 * get error message handle
	 */
	public static String ErrorMessage;
	/**
	 * save ceo details
	 */
	public static CEOuser GetCEO;
	/**
	 * check if company is available
	 */
	public static boolean available;
	/**
	 * List of all orders
	 */
	public static ArrayList<Order> AllOrder;
	/**
	 * List of items additions
	 */
	public static ArrayList<ItemAddition> AllOrderItem;
	/**
	 * Save email details
	 */
	public static String Email;
	/**
	 * Check if delivery exists
	 */
	public static boolean deliveryExists;
	/**
	 * Get Histogram for ceo
	 */
	public static ArrayList<HistogramCEO> HistogramArray;
	/**
	 * save pdf values as list
	 */
	public static ArrayList<PDFList> PDFListValues;
	/**
	 * save pdf file
	 */
	public static File PDFFile;
	/**
	 * List of order reports
	 */
	public static ArrayList<OrdersReport> OrdersReportArray;
	/**
	 * List of income reports
	 */
	public static ArrayList<InComeReport> InComeReportArray;
	/**
	 * List of performance reports
	 */
	public static ArrayList<PerformenceReport> PerfArray;
	/**
	 * List of all orders for user
	 */
	public static ArrayList<OrderHistory> orderhis;
	/**
	 * get order number
	 */
	public static int orderNum;
	/**
	 * get index for delivery option
	 */
	public static int ind;
	/**
	 * check if shared group exists
	 */
	public static boolean group_exist;
	/**
	 * List of all clients
	 */
	public static ArrayList<String> clients;
	/**
	 * Get group code
	 */
	public static int numCode;
	private static URL location;
	private static ResourceBundle resources;
	/**
	 * Save list of all shared group members and details
	 */
	public static ArrayList<clientDetails> listClients;
	/**
	 * group code for joining
	 */
	public static int group_code;
	/**
	 * List of group order
	 */
	public static ArrayList<Order> groupOrder;
	/**
	 * get delivery date as string
	 */
	public static String DeliveryDate;
	/**
	 * get type of order (pickup, delivery)
	 */
	public static String TypeOfOrder;
	/**
	 * get refund amount
	 */
	public static Double getRefund;
	/**
	 * save group size
	 */
	public static int group_size;
	/**
	 * Check if order is early order or late order
	 */
	public static String OrderEarlyLate;
	/**
	 * save restaurant id
	 */
	public static int RestaurantID;
	
	/*
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 * ErrorMsg case: Get status message from the server
	 * getallrestaurant case: taking all the restaurant name from the DB who's in branch manager location
	 * GetRefund case: Take refund late delivery
	 * getOrderID case: taking the order number
	 * loginsucceeded case: taking the user who's loggedin from the server
	 * getuser case: taking the user email from DB
	 * getRestaurantWorker case: taking whole the workers from the DB
	 * getnormaluser case: taking the normal user information for the server
	 * getTypeOfOrder case: taking the order type from the server
	 * GetAllUsersFromUsersTable case: taking all the users 
	 * GetbissnessUser case: taking all the business users from the server
	 * GetCEO case: taking CEO information from the server
	 * getUserOrder case: taking all the orders for specific user 
	 * getRestaurantManager case: taking manager information who's logged in
	 * CheckCompany case: taking status company: Confirmed or not
	 * getrestaurantname case: taking restaurant name from the server
	 * GotBranchManager case: taking BranchManager user
	 * RestaurantRequest case: taking all the restaurant that not confirmed yet
	 * AddNewUser case: taking status after adding new user
	 * GetOrderItems case: taking all the orders ID
	 * CheckDelivery case: taking order type from the server
	 * GetallOrders case: Taking all the orders from the server
	 * GotW4c case: taking user W4C number from the server
	 * getCompanyList case: taking all the company from the server
	 * GetAllItems case: taking all the items from the menu
	 * OrdersReportMonthly case: taking wanted Order report from the server
	 * PerformenceReportMonthly case: taking wanted Performence report from the server
	 * InComeReportMonthly case: Taking wanted InCome report from the server
	 * SendPDFToCEO case: Status text for Sending quarter PDF to CEO
	 * getPDFLists case: taking wanted PDF file from the server
	 * DownloadPDFFromDB case : Downloading wanted PDF into CEO computer
	 * gerReportForHistogram case: Taking InCome and sold values from DB to create CEO histogram
	 * @param msg The message from the server.
	 */
	
	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;

		System.out.println("--> handleMessageFromServer");

		MessagesClass message = (MessagesClass) msg;
		switch (message.getMsgType()) {
		case getworker:
			WorkerUser= (WorkerUser) message.getMsgData();
			break;
		case PayWithRefund:
			break;
		case GetRefund:
			getRefund=(Double)message.getMsgData();
			break;
		case RefundCustomer:
			break;
		case GetRestaurantID:
			RestaurantID=(int)message.getMsgData();
			break;
		case getOrderID:
			orderNum = (int) message.getMsgData();
			break;

		case getIND:
			ind = (int) message.getMsgData();
			break;

		case newOrder:
			break;

		case newDelivery:
			break;

		case soldItems:
			break;
		case updateW4CforBussiness:
			break;

		case partnersGroupNumber:

			break;

		case sharedGroup:
			break;

		case getGroupNumber:
			group_exist = (boolean) message.getMsgData();
			group_size=(int)message.getMsgData1();
			break;

		case GetDeliveryDate:
			DeliveryDate = (String) message.getMsgData1();
			System.out.println(DeliveryDate);
			break;

		case updateTable:
			break;

		case order_items_additions:
			break;

		case closeGroupDelivery:
			group_code = (int) message.getMsgData();
			System.out.println(group_code);
			if (user.getUserType().equals("Bussiness")) {
				System.out.println("i'm bussiness");
				if (Bussinessuser.getGroup_code() == group_code) {
					Bussinessuser.setLock(0);
					TypeOfOrderController.currentThread().notifyAll();
				}
			}
			break;

		case joinGroup:
			if (user.getUserType().equals("Bussiness")
					&& DeliveryInfoController.randGroup == ((clientDetails) message.getMsgData()).getCode()) {
				SharedDeliveryController.clients.add((clientDetails) message.getMsgData());
			}
			break;

		case loginSucceeded:
			user = (User) message.getMsgData();
			ErrorMessage = (String) message.getMsgData1();
			break;
		case getuser:
			Email = (String) message.getMsgData();
			break;
		case insidealldatafromBiteMeDB:
			break;

		case getRestaurantWorker:
			WorkerUser = (WorkerUser) message.getMsgData();
			break;
		case getnormaluser:
			normaluser = (Normal) message.getMsgData();

			break;

		case getTypeOfOrder:
			TypeOfOrder = (String) message.getMsgData1();
			break;

		case updateOrderStatus:
			break;
		case GetAllUsersFromUsersTable:
			GetAllUsersFromUsersTable = (ArrayList<User>) message.getMsgData();
			break;
		case GetbissnessUser:
			Bussinessuser = (BussinessUser) message.getMsgData();
			break;
		case GetCEO:
			GetCEO = (CEOuser) message.getMsgData();
			break;
		case getUserOrders:
			orderhis = (ArrayList<OrderHistory>) message.getMsgData();
			break;
		case UpdateItem:/// update item
			break;
		case RemoveItem:/// remove item
			break;
		case AddItems:
			break;
		case getRestaurantManager:
			restaurantManager = (RestaurantManager) message.getMsgData();
			break;
		case CheckCompany://///// new
			ErrorMessage = (String) message.getMsgData();
			System.out.println(ErrorMessage + " the massege error is ");
			break;
		case getrestaurantname:
			Resturaunt = (Resturaunt) message.getMsgData();
			break;
		case GetallAvailableCompany:
			AllCompany = (ArrayList<Company>) message.getMsgData();
			break;
		case GotBranchManager:
			branchManager = (BranchManager) message.getMsgData();
			break;

		case GotHRManager:
			HRmanager1 = (HRUser) message.getMsgData();
			break;
		case ResturauntRequestSent:
			break;
		case AddNewUser:
			ErrorMessage = (String) message.getMsgData();
			break;
		case AddNewUserwithvisa:
			ErrorMessage = (String) message.getMsgData();
			break;
		case GettempData:
			bussinessUser = (ArrayList<BussinessUser>) message.getMsgData();
			break;
		case getallrestaurant:
			getallresturant = (ArrayList<Resturaunt>) message.getMsgData();
			break;
		case GetOrderItems:
			AllOrderItem = (ArrayList<ItemAddition>) message.getMsgData();
			break;
		case updateStatusofusers:
			break;

		case getOrderEarlyLate:
			OrderEarlyLate=(String)message.getMsgData();
			break;
		case DeleteOrder:
			break;

		case CheckDelivery:
			deliveryExists = (boolean) message.getMsgData();
			break;
		case GetAllOreders:
			AllOrder = (ArrayList<Order>) message.getMsgData();
			break;
		case GotW4C:
			W4CC = (Integer) message.getMsgData();
			break;
		case getCompanyList:
			CompanyList = (ArrayList<Company>) message.getMsgData();
			break;

		case getallusers:
			getlistofnormalaccount = (ArrayList<User>) message.getMsgData();
			break;
		case ReportForManager:
			arrList = (ArrayList<RestaurantReport>) message.getMsgData();
			break;
		case ReportList:
			arrList = (ArrayList<RestaurantReport>) message.getMsgData();
			break;
		case forgetPassword:
			updateError = (boolean) message.getMsgData();
			break;
		case getthenormalendifainedacoount:
			ArrListToAcceptNewAccounts = (ArrayList<Normal>) message.getMsgData();
			break;

		case acceptnewnormaluser:
			break;

		case deleteid:
			break;
		case updateandinsidebussinesstousers:
			break;
		case Createaccepttresturaunt:
			ErrorMessage = (String) message.getMsgData();
			break;
		case W4C:
			w4c = (W4CNormal) message.getMsgData();
			break;

		case GetAllRestaurants:
			restaurantList = new ArrayList<>();
			restaurantList = (ArrayList<Resturaunt>) message.getMsgData();
			break;

		case QRcreateW4C:
			break;
			
		case SendToPerformencereports:
			break;
		case GetAllWorker:
			allworker = (ArrayList<WorkerUser>) message.getMsgData();
			break;
		case AddNewWorker:

			ErrorMessage = (String) message.getMsgData();
			break;
		case deleteworker:
			break;
		case editworkers:// new
			break;
		case GetAllitems:/// new
			allItems = (ArrayList<Item>) message.getMsgData();

			break;
		case GetAllitemsfromitem:/// new

			Items = (ArrayList<Item>) message.getMsgData();
			break;
		case RemoveItemAddition://// new
			break;
		case ConfirmOrder:
			break;
		case OrdersReportMonthly:
			OrdersReportArray = (ArrayList<OrdersReport>) message.getMsgData();
			ErrorMessage = (String) message.getMsgData1();
			break;
		case PerformenceReportMonthly:
			PerfArray = (ArrayList<PerformenceReport>) message.getMsgData();
			ErrorMessage = (String) message.getMsgData1();
			break;
			
		case InComeReportMonthly:
			InComeReportArray = (ArrayList<InComeReport>) message.getMsgData();
			ErrorMessage = (String) message.getMsgData1();
			break;
		case SendPDFToCEO:
			ErrorMessage = (String) message.getMsgData();
			break;
		case getPDFLists:
			PDFListValues = (ArrayList<PDFList>) message.getMsgData();
			break;

		case DownlowndPDFFromDB:
			PDFFile = (File) message.getFile();
			break;
			
		case getReportForHistogram:
			ErrorMessage = (String) message.getMsgData1();
			HistogramArray = (ArrayList<HistogramCEO>) message.getMsgData();
			break;
		case GetConfirmRestaurants:
			getallresturant = (ArrayList<Resturaunt>) message.getMsgData();
			break;
		case ShowTaxes:
			ErrorMessage = (String)message.getMsgData();
		default:
			break;
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Object message) {
		awaitResponse = true;
		try {
			sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// wait for response
		while (awaitResponse) {
			try {

				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {

			sendToServer(new MessagesClass(Messages.Disconnected, null));

			closeConnection();

		} catch (IOException e) {
		}
		// System.exit(0);
	}
}
//End of ChatClient class
