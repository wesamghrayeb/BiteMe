package server;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.lowagie.text.DocumentException;

import client.ChatClient;
import client.ClientController;
import common.Addition;
import common.BussinessUser;
import common.Delivery;
import common.GroupDelivery;
import common.Item;
import common.ItemAddition;
import common.Messages;
import common.MessagesClass;
import common.Normal;
import common.Order;
import common.OrderHistory;
import common.RestaurantManager;
import common.Resturaunt;
import common.User;
import common.Visa;
import common.W4CBussiness;
import common.W4CNormal;
import common.WorkerUser;
import common.clientDetails;
import common.clientsInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	static W4CNormal w4c = null;
	public int Value;
	/**
	 * save client information when connecting
	 */
	static ArrayList<clientsInfo> clientsGroup = new ArrayList<>();
	/**
	 * save current clock of machine
	 */
	public static String sClock;
	/**
	 * get month of machine
	 */
	public static int month;
	/**
	 * get year of machine
	 */
	public static int year;
	public static boolean flag;
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}

	public void clientConnected(ConnectionToClient client) {
		System.out.println("->Client Connected");
		try {

			UpdateClient(client.getInetAddress(), client.getInetAddress().getHostAddress(), "Connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client DisConnected");
		try {

			UpdateClient(client.getInetAddress(), client.getInetAddress().getHostAddress(), "Disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void UpdateClient(InetAddress HostName, String IP, String Status) {
		ServerUI.aFrame.UpdateClient(HostName, IP, Status);
	}
	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 * login case: checking if the user exist in DB, if yes send the user information to client
	 * PayWithRefund case: buy order with refund money
	 * GetRefund case: Take the refund if exist for specific user and send them to client
	 * RefundCustomer case: if the order is late, then refund %50 from the order price
	 * GetAllAvailableCompany case: take all the confirmed company and send them to client
	 * GetAllOrders case: Get all the orders and send them to client
	 * AddNewUserWithVisa case: add new User to DB
	 * CompanyRequest case: take all the company that not confirmed yet and send them to client
	 * CheckCompany case: check if specific company is confirmed
	 * GetAllUserFromUsersTable case: take all users and send them to client
	 * GetBranchManager case: take branch manager information and send them to client
	 * GetHRManager case: take HR manager information and send them to client
	 * GetCEO case: take CEO information and send them to client
	 * updatestatus case: update the Login status from 0 to 1
	 * W4C case: get W4C from DB and send it to client
	 * getallrestaurant case: get all the restaurants and send them to client
	 * UpdateStatusUser case: Update user status : (Frozen,Active,Locked)
	 * GetAllItems case: take all the items from the menu and send them to client
	 * deleteid : delete user from the DB
	 * GetAllWorker case: Get the workers from DB and send them to client
	 * DeleteWorker case: delete worker from restaurant
	 * AcceptNewNormalUser case: Add new normal user to DB
	 * getRestaurantManager case: get restaurant manager infromation and send them to client
	 * GetOrderItem case: Get all the items that includes in the menu and send them to client
	 * getTypeOfOrder case: take order type and send it to client
	 * updateOrderStatus case: update order status : (late,early)
	 * DeleteOrder case: Delete order from DB
	 * GetDeliveryData case : take the user place information and send them to client
	 * QEcreateW4C case: create QR code for new user
	 * UdateItem case : Update item in the menu
	 * newOrder case: add new order to the orders list
	 * newDelivery case: add new delivery to delivery list
	 * soldItems case: update all insert sold items for each restaurant
	 * RemoveItem case: Remove Item from menu
	 * RemoveItemAddition case: Remove the addition from specific item
	 * ConfirmedOrder case: Confirm order that the restaurant finished it
	 * OrdersReportMonthly case: Take  order report for specific month and send the report to the client
	 * PerformenceReportMonthly case: take performance report for specific month and send the report to the client
	 * InComeReportMonthly case : take InCome report for specific month and send the report to the client
	 * SendPDFToCEO case: Send PDF file to CEO PDF list
	 * DownloadPDFFromDB case: Download the PDF file to CEO computer
	 * getReportForHistogram case: Take specific report to create histogram
	 * getPDFList case: take the files list that sends by the managers
	 * GetConfirmedRestaurants case: take all the Confirmed restaurant and send them to client
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		System.out.println("Message received: " + ((MessagesClass) msg).getMsgType() + " from " + client);
		MessagesClass message = (MessagesClass) msg;
		User user = null;
		User user1 = null;
		int month;
		int year;
		RestaurantManager manager;
		int ResturantID;
		Visa visa = null;

		switch (message.getMsgType()) {
		//// general//////
		case Login:
			mysqlConnection.LoginUser=null;
			user = (User) message.getMsgData();
			try {
				String str = mysqlConnection.LogInChecker(user);
				message = new MessagesClass(Messages.loginSucceeded, mysqlConnection.LoginUser, str);
				if (mysqlConnection.LoginUser != null)
					mysqlConnection.updateClientStatus(mysqlConnection.LoginUser, 1);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			break;
		case PayWithRefund:
			mysqlConnection.PayWithRefund((String) message.getMsgData(), (double) message.getMsgData1(),
					(int) message.getMsgData3());
			message = new MessagesClass(Messages.PayWithRefund, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case getOrderEarlyLate:
			message = new MessagesClass(Messages.getOrderEarlyLate, mysqlConnection.getOrderEarlyLate((int)message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case GetRefund:
			message = new MessagesClass(Messages.GetRefund,
					mysqlConnection.GetRefund((String) message.getMsgData(), (int) message.getMsgData1()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case RefundCustomer:
			try {
				mysqlConnection.RefundCustomer((int) message.getMsgData(), (double) message.getMsgData1(),
						(String) message.getW4c());
			} catch (SQLException e14) {
				// TODO Auto-generated catch block
				e14.printStackTrace();
			}
			message = new MessagesClass(Messages.RefundCustomer, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case GetallAvailableCompany:

			try {
				message = new MessagesClass(Messages.GetallAvailableCompany, mysqlConnection.GetallAvailableCompany());
			} catch (SQLException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			break;
		case GetAllOreders:
			try {
				message = new MessagesClass(Messages.GetAllOreders,
						mysqlConnection.GetAllOrder((int) message.getMsgData()));
			} catch (NumberFormatException e13) {
				// TODO Auto-generated catch block
				e13.printStackTrace();
			} catch (SQLException e13) {
				// TODO Auto-generated catch block
				e13.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e13) {
				// TODO Auto-generated catch block
				e13.printStackTrace();
			}

			break;
		case AddNewUser:// add new user

			try {
				message = new MessagesClass(Messages.AddNewUser, mysqlConnection.AddNewUser((User) message.getMsgData(),
						message.getMsgData1(), (String) message.getW4c()));
			} catch (SQLException e12) {
				// TODO Auto-generated catch block
				e12.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			break;
		case AddNewUserwithvisa:
			try {
				message = new MessagesClass(Messages.AddNewUserwithvisa,
						mysqlConnection.AddNewUserwithvisa((User) message.getMsgData(), message.getMsgData1(),
								(Visa) message.getMsgData3(), (String) message.getW4c()));
			} catch (SQLException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}

			break;
		case getCompanyList:/// take company list from musql
			try {
				message = new MessagesClass(Messages.getCompanyList, mysqlConnection.getCompanyList());
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case CompanyConfirmed:
			String Cname1 = (String) message.getMsgData();
			try {
				mysqlConnection.companyConfirm(Cname1);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CompanyRequest:
			String Cname = (String) message.getMsgData();
			try {
				mysqlConnection.confirmCompane(Cname);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case CheckCompany:
			String Cname11 = (String) message.getMsgData();
			try {
				message = new MessagesClass(Messages.CheckCompany, mysqlConnection.companyChecker(Cname11));
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case GetAllUsersFromUsersTable://///// take all users

			try {
				message = new MessagesClass(Messages.GetAllUsersFromUsersTable,
						mysqlConnection.TakeAllUserThatNotConfiredyet());
			} catch (SQLException e10) {
				// TODO Auto-generated catch block
				e10.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e10) {
				// TODO Auto-generated catch block
				e10.printStackTrace();
			}
			break;
		case insidealldatafromBiteMeDB:
			ArrayList<User> userfromBM;
			try {
				mysqlConnection.insidealldatafromBiteMeDB();
			} catch (SQLException e9) {
				// TODO Auto-generated catch block
				e9.printStackTrace();
			}
			message = new MessagesClass(Messages.insidealldatafromBiteMeDB, null);

			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();

			}

			break;
		case GetBranchManager:
			user = (User) message.getMsgData();

			message = new MessagesClass(Messages.GotBranchManager, mysqlConnection.GetBranchManager(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case GetCEO:
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.GetCEO, mysqlConnection.CeoUser(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case GetHRManager:
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.GotHRManager, mysqlConnection.GetHRManager(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case getnormaluser:

			message = new MessagesClass(Messages.getnormaluser,
					mysqlConnection.Getnormaluser((User) message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;

		case GetNormalUser:
			Normal nuser = null;
			try {
				nuser = mysqlConnection.getNormalUser((User) message.getMsgData());
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			message = new MessagesClass(Messages.GetNormalUser, nuser);
			try {
				client.sendToClient(message);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;

		case updateStatus:
			// update the clieLoginnt status to 0 -> that mean he log out
			if ((User) message.getMsgData() != null)
				mysqlConnection.updateClientStatus((User) message.getMsgData(), (Integer) message.getMsgData1());
			try {
				client.sendToClient(new MessagesClass(Messages.LogedOut, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case W4C:
			w4c = mysqlConnection.getW4C((User) message.getMsgData());
			try {
				client.sendToClient(new MessagesClass(Messages.W4C, w4c));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case getallrestaurant:

			String location = (String) message.getMsgData();
			mysqlConnection.getAllResturaunt(location);
			try {
				client.sendToClient(new MessagesClass(Messages.getallrestaurant, mysqlConnection.getAllresturaunt));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case updateStatusofusers:
			// update the client status to 0 -> that mean he log out
			User normal = (User) message.getMsgData();

			try {
				mysqlConnection.UpdateStatusOfUsers(normal);
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.updateStatusofusers, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case GetAllitems:
			int resid1 = (int) message.getMsgData();
			try {
				client.sendToClient(new MessagesClass(Messages.GetAllitems, mysqlConnection.getallitems(resid1)));
			} catch (IOException e8) {
				// TODO Auto-generated catch block
				e8.printStackTrace();
			} catch (SQLException e8) {
				// TODO Auto-generated catch block
				e8.printStackTrace();
			}

			break;
		case GetAllitemsfromitem:/// get main dish
			int resid = (int) message.getMsgData();
			String type = (String) message.getMsgData1();
			try {
				try {
					client.sendToClient(new MessagesClass(Messages.GetAllitemsfromitem,
							mysqlConnection.getallmaindish(resid, type)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e7) {
				// TODO Auto-generated catch block
				e7.printStackTrace();
			}

			break;
		case GotW4C:
			int num = mysqlConnection.IDForW4C();
			message = new MessagesClass(Messages.GotW4C, num);
			try {
				client.sendToClient(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;

		case updateandinsidebussinesstousers:
			User userb;
			userb = (User) message.getMsgData();
			if ((User) message.getMsgData() != null)
				try {
					mysqlConnection.BussinessAccountHasBeenAccepted(userb, (W4CBussiness) message.getMsgData1());
				} catch (SQLException e4) {
					e4.printStackTrace();
				}
			try {
				client.sendToClient(new MessagesClass(Messages.updateandinsidebussinesstousers, null));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Disconnected:
			clientDisconnected(client);
			break;
		case clientConnected:
			clientConnected(client);

			break;

		case ClientMassage:
			message = new MessagesClass(Messages.ClientMassage, null);
			ServerPortFrameController.c = true;
			try {
				client.sendToClient(message);
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			break;
		case GettempData:
			String company = (String) message.getMsgData1();
			try {
				mysqlConnection.getTheRequestList(company);
				client.sendToClient(new MessagesClass(Messages.GettempData, mysqlConnection.requestsList));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
	
		case deleteid:
			try {
				mysqlConnection.deleteId((String) message.getMsgData(), (String) message.getMsgData1());
				client.sendToClient(new MessagesClass(Messages.deleteid, null));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case CreateNewNormalAccountWithOutVisa:
			user = (User) message.getMsgData();
			Normal NorUser;
			NorUser = (Normal) message.getMsgData1();
			try {
				message = new MessagesClass(Messages.loginerror,
						mysqlConnection.InsertNewNormalAccountWithOutVisa(user, NorUser));
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			break;

		case CreateNewNormalAccountWithVisa:
			user = (User) message.getMsgData1();
			visa = (Visa) message.getMsgData();
			Normal NorUser1;
			NorUser1 = (Normal) message.getMsgData2();
			try {
				mysqlConnection.InsertNewNormalAccountWithVisa(visa, user, NorUser1);
				message = new MessagesClass(Messages.CreateNewNormalAccountWithVisa, null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getthenormalendifainedacoount:
			try {
				mysqlConnection.GetTheRequestNormalAccount();
				client.sendToClient(new MessagesClass(Messages.getthenormalendifainedacoount,
						mysqlConnection.NormalUsersNotAccepted));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case Createaccepttresturaunt:
			Resturaunt res = (Resturaunt) message.getMsgData();

			try {
				client.sendToClient(new MessagesClass(Messages.Createaccepttresturaunt,
						mysqlConnection.Create_acceptRestaurant(res)));
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}

			break;
		case GetAllWorker:
			int RestaurantId = (int) message.getMsgData();

			try {
				client.sendToClient(
						new MessagesClass(Messages.GetAllWorker, mysqlConnection.GetAllWorkers(RestaurantId)));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;
		case AddNewWorker://///// add new worker in worker table and user
			WorkerUser WorkerUser = (WorkerUser) message.getMsgData();
			try {
				try {
					client.sendToClient(
							new MessagesClass(Messages.AddNewWorker, mysqlConnection.AddNewWorker(WorkerUser)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;

		case deleteworker://// removed worker from user and workerstable
			WorkerUser WorkerUser1 = (WorkerUser) message.getMsgData();
			try {
				try {
					mysqlConnection.RemoveWorker(WorkerUser1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.sendToClient(new MessagesClass(Messages.AddNewWorker, null));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;
		case editworkers:///// edit worker

			WorkerUser WorkerUser2 = (WorkerUser) message.getMsgData();
			try {
				mysqlConnection.EditWorker(WorkerUser2);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.editworkers, null));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;

		case acceptnewnormaluser:
			user = (User) message.getMsgData();
			try {
				mysqlConnection.AcceptNewNormalUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.acceptnewnormaluser, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case getallusers:
			try {
				mysqlConnection.getAllUsers();

				client.sendToClient(new MessagesClass(Messages.getallusers, mysqlConnection.getlistofnormalaccount));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// clientDisconnected(client);

			break;
		case GetbissnessUser:
			user = (User) message.getMsgData();
			if (mysqlConnection.GetBissnessUser(user) != null) {
				message = new MessagesClass(Messages.GetbissnessUser, mysqlConnection.GetBissnessUser(user));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				message = new MessagesClass(Messages.loginerror, null);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case RestaurantReport:
			month = message.getMonth();
			year = message.getYear();
			manager = (RestaurantManager) message.getMsgData();
			try {
				if (mysqlConnection.GetReportForRestaurant(manager, month, year) != null) {
					try {
						message = new MessagesClass(Messages.ReportList,
								mysqlConnection.GetReportForRestaurant(manager, month, year));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// mysqlConnection.updateClientStatus(normalUsers, 1);
					try {
						client.sendToClient(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					message = new MessagesClass(Messages.loginerror, null);
					try {
						client.sendToClient(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;
		case ReportForManager:
			month = message.getMonth();
			year = message.getYear();
			ResturantID = message.getResID();
			try {
				mysqlConnection.GetReportForManager(ResturantID, month, year);
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			message = new MessagesClass(Messages.ReportForManager, mysqlConnection.ReportListForManager);
			try {
				client.sendToClient(message);
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;

		case getRestaurantManager:// get resm
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.getRestaurantManager, mysqlConnection.GetRestaurantManager(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case getRestaurantWorker:// get resm//////////////////////////////////////////////////////////////////
			user = (User) message.getMsgData();

			if (mysqlConnection.GetRestaurantWorker(user) != null) {
				message = new MessagesClass(Messages.getRestaurantWorker, mysqlConnection.GetRestaurantWorker(user));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				message = new MessagesClass(Messages.loginerror, null);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case GetOrderItems:
			try {
				ArrayList<ItemAddition> ItemArray;
				ItemArray = mysqlConnection.GetallOrederItems((int) message.getMsgData());
				message = new MessagesClass(Messages.GetOrderItems,ItemArray);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;
		case getTypeOfOrder:
			message = new MessagesClass(Messages.getTypeOfOrder, null,
					mysqlConnection.GetTypeOfOrder((int) message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case updateOrderStatus:
			mysqlConnection.updateOrderStatus((int) message.getMsgData(), (String) message.getMsgData1());
			message = new MessagesClass(Messages.updateOrderStatus, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case DeleteOrder:
			mysqlConnection.deleteOrder((int) message.getMsgData());

			message = new MessagesClass(Messages.DeleteOrder, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CheckDelivery:
			message = new MessagesClass(Messages.CheckDelivery,
					mysqlConnection.CheckDelivery((int) message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case GetDeliveryDate:
			String date = mysqlConnection.GetDeliveryDate((int) message.getMsgData());
			System.out.println(date);
			message = new MessagesClass(Messages.GetDeliveryDate, null, date);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getuser:
			try {
				System.out.println((String) message.getMsgData() + "Echo");
				System.out.println(mysqlConnection.getuser((String) message.getMsgData()));
				message = new MessagesClass(Messages.getuser,
						mysqlConnection.getuser((String) message.getMsgData()));
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;
		case getrestaurantname:
			int idrestaurant = (int) message.getMsgData();
			try {

				message = new MessagesClass(Messages.getrestaurantname,
						mysqlConnection.getrestaurantname(idrestaurant));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			break;

		case GetAllRestaurants:
			message = new MessagesClass(Messages.GetAllRestaurants, mysqlConnection.getAllResturaunts());
			try {
				client.sendToClient(message);
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case QRcreateW4C:
			User user2 = (User) message.getMsgData();
			mysqlConnection.generate_qr(user2.getUserName(), String.valueOf(w4c.getCode()));
			message = new MessagesClass(Messages.QRcreateW4C, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case UpdateItem:
			try {
				mysqlConnection.UpdateItem((Item) message.getMsgData(), (String) message.getMsgData1());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.UpdateItem, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case getOrderID:
			int orderNum = mysqlConnection.getOrderID();
			message = new MessagesClass(Messages.getOrderID, orderNum);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getIND:
			int ind = mysqlConnection.getIND();
			message = new MessagesClass(Messages.getIND, ind);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case newOrder:
			mysqlConnection.newOrder((Order) message.getMsgData());
			message = new MessagesClass(Messages.newOrder, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case newDelivery:
			mysqlConnection.newDelivery((Delivery) message.getMsgData());
			message = new MessagesClass(Messages.newDelivery, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case soldItems:
			mysqlConnection.soldItems((Order) message.getMsgData());
			message = new MessagesClass(Messages.soldItems, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case SendToPerformencereports:
			mysqlConnection.SendToPerformencereports((String) message.getMsgData(),(String)message.getMsgData1());
			message = new MessagesClass(Messages.SendToPerformencereports, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
		case updateW4CforBussiness:
			mysqlConnection.updateW4CforBussiness((BussinessUser) message.getMsgData());
			message = new MessagesClass(Messages.updateW4CforBussiness, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case partnersGroupNumber:

			mysqlConnection.InsertGroup((GroupDelivery) message.getMsgData());

			clientsGroup.add(new clientsInfo(client, ((GroupDelivery) message.getMsgData()).getGroupNum()));
			message = new MessagesClass(Messages.partnersGroupNumber, null);

			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case GetRestaurantID:
			message = new MessagesClass(Messages.GetRestaurantID, mysqlConnection.GetRestaurantID((int)message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case sharedGroup:
			try {
				client.sendToClient(new MessagesClass(Messages.error, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getGroupNumber:
			boolean exists = false;
			int group_size = 0;
			try {
				exists = mysqlConnection.GetGroupNumber((int) message.getMsgData());
				group_size = mysqlConnection.GetGroupSize((int) message.getMsgData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.getGroupNumber, exists, group_size);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case updateTable:
			System.out.println("10");
			sendToAllClients(new MessagesClass(Messages.joinGroup, (clientDetails) message.getMsgData()));
			// sendToAllClients(new MessagesClass(Messages.joinGroup, null));
			System.out.println("11");
			break;

		case closeGroupDelivery:
			sendToAllClients(new MessagesClass(Messages.closeGroupDelivery, (int) message.getMsgData()));
			break;

		case order_items_additions:
			mysqlConnection.order_items_additions(message.getMsgData());
			message = new MessagesClass(Messages.order_items_additions, message.getMsgData());
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case RemoveItem:
			try {
				mysqlConnection.RemoveItem((Item) message.getMsgData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.RemoveItem, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case RemoveItemAddition:
			try {
				mysqlConnection.RemoveItemAddition((Item) message.getMsgData(), (String) message.getMsgData1());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // remove addition ftom item_addition and addition
			message = new MessagesClass(Messages.RemoveItemAddition, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case AddItems:
			try {
				System.out.println((String) message.getW4c() + "im heree in additems");
				mysqlConnection.AddItems((Item) message.getMsgData(), (Addition) message.getMsgData1(),
						(int) message.getMsgData3(), (String) message.getW4c());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.AddItems, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case getUserOrders:
			try {
				message = new MessagesClass(Messages.getUserOrders,
						mysqlConnection.getUserOrders((String) message.getMsgData1()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case ConfirmOrder:
			mysqlConnection.AddOrderToProccess((OrderHistory) message.getMsgData());
			message = new MessagesClass(Messages.ConfirmOrder, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case OrdersReportMonthly:
			String RestaurantName = (String) message.getMsgData();
			int yearly = (Integer) message.getMsgData1();
			int monthly = (Integer) message.getMsgData3();
			String Location = (String) message.getW4c();
			String OrderStatus = null;
			try {
				OrderStatus = mysqlConnection.GetOrderReport(RestaurantName, yearly, monthly, Location);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(mysqlConnection.ordersReport);
			message = new MessagesClass(Messages.OrdersReportMonthly, mysqlConnection.ordersReport, OrderStatus);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case PerformenceReportMonthly:
			String RestaurantName1 = (String) message.getMsgData();
			int yearly1 = (Integer) message.getMsgData3();
			int monthly1 = (Integer) message.getMsgData1();
			String OrderStatus1 = null;
			try {
				OrderStatus1 = mysqlConnection.GetPerformenceReport(RestaurantName1, yearly1, monthly1);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			message = new MessagesClass(Messages.PerformenceReportMonthly, mysqlConnection.PerfReport,
					OrderStatus1);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case InComeReportMonthly:
			String RestaurantNameInCome = (String) message.getMsgData();
			int YearInCome = (Integer) message.getMsgData1();
			int MonthInCome = (Integer) message.getMsgData3();
			String LocationInCome = (String) message.getW4c();
			String OrderStatus11 = null;
			try {
				OrderStatus11 = mysqlConnection.GetInComeReport(LocationInCome, RestaurantNameInCome, YearInCome,
						MonthInCome);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			message = new MessagesClass(Messages.InComeReportMonthly, mysqlConnection.incomearr, OrderStatus11);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case SendPDFToCEO:
			String Area1 = (String) message.getMsgData();
			int year11 = (Integer) message.getMsgData1();
			String Months11 = (String) message.getW4c();
			try {
				String MsgStr1 = mysqlConnection.SendPDFToCeo(Area1, year11, Months11);
				message = new MessagesClass(Messages.SendPDFToCEO, MsgStr1);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case DownlowndPDFFromDB:
			String mypath = (String) message.getMsgData();
			File file = new File(mypath);
			message = new MessagesClass(Messages.DownlowndPDFFromDB, file, 10);
			try {
				client.sendToClient(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case getReportForHistogram:
			String RestaurantName2 = (String) message.getMsgData();
			int year1 = (Integer) message.getMsgData1();
			String Months = (String) message.getW4c();
			try {
				String MsgStr2 = mysqlConnection.GetHistogram(RestaurantName2, year1, Months);
				System.out.println("qqqqqqqqqqqqqqqqqqqQ");
				System.out.println(mysqlConnection.HistogramArray);
				message = new MessagesClass(Messages.getReportForHistogram, mysqlConnection.HistogramArray, MsgStr2);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case getPDFLists:
			try {
				message = new MessagesClass(Messages.getPDFLists, mysqlConnection.PDFLists());
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case GetConfirmRestaurants:
			String location1 = (String) message.getMsgData();
			mysqlConnection.GetConfirmRestaurants(location1);
			try {
				client.sendToClient(new MessagesClass(Messages.GetConfirmRestaurants, mysqlConnection.getAllresturaunt));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case ShowTaxes:
			System.out.println("Error in Printing");
			int RestaurantID = (Integer)message.getMsgData();
			int TaxesYear = (Integer)message.getMsgData1();
			int TaxesMonth= (Integer)message.getMsgData3();
			System.out.println(RestaurantID);
			System.out.println(TaxesYear);
			System.out.println(TaxesMonth);
			String TaxesStatus=null;
			try {
				try {
					TaxesStatus = mysqlConnection.ShowMonthlyTaxes(RestaurantID,TaxesYear,TaxesMonth);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Here Im");
				System.out.println(TaxesStatus);
				client.sendToClient(new MessagesClass(Messages.ShowTaxes, TaxesStatus));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case getworker:
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.getworker, mysqlConnection.GetWorker(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;

		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		Date date = new Date();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		sClock = dtf.format(now);
		int minutsnow = now.getMinute();
		System.out.println(now.getHour());
		mysqlConnection.connectToDB();
		mysqlConnection.connectToBiteMeDB();
		LocalDateTime today1 = LocalDateTime.now();
		flag = true;
		Thread t1 = new Thread(() -> {
			while (true) {
				LocalDateTime today = LocalDateTime.now();
				Value = ServerUI.OpeningDate.compareTo(today);
				if(Value>0) {
					ServerUI.OpeningDate = LocalDateTime.now();
				}
				if (today.getDayOfMonth() == 1) {
					if (flag) {
						try {
							month = today.getMonthValue();
							year = today.getYear();
							if (month == 1) {
								month = 12;
								year--;
							} else
								month--;
							System.out.println(month);
							System.out.println(year);
							mysqlConnection.CreateInComeReport("North", month, year);
							mysqlConnection.CreateInComeReport("South", month, year);
							mysqlConnection.CreateInComeReport("Center", month, year);
							mysqlConnection.CreateOrdersReport("North", month, year);
							mysqlConnection.CreateOrdersReport("South", month, year);
							mysqlConnection.CreateOrdersReport("Center", month, year);
							try {
								mysqlConnection.InsertPDFtoDB("North", year, month);
								mysqlConnection.InsertPDFtoDB("Center", year, month);
								mysqlConnection.InsertPDFtoDB("South", year, month);
								mysqlConnection.InsertMonthlyTaxes("North",year,month);
								mysqlConnection.InsertMonthlyTaxes("South",year,month);
								mysqlConnection.InsertMonthlyTaxes("Center",year,month);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (today.getDayOfMonth() == 1)
					flag = false;
				else {
					flag=true;
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		});
		t1.start();
		
		
		
		System.out.println("Server listening for connections on port " + getPort());

		Thread t111 = new Thread(() -> {
			int minutsnow1 = now.getMonthValue();

			while (true) {
				LocalDateTime now1 = LocalDateTime.now();
				if (minutsnow1 == 13) {
					minutsnow1 = 1;
				}

				if (minutsnow1 == now1.getMonthValue()) {
					try {
						mysqlConnection.UpdateEveryMonth("M");

						minutsnow1++;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		t111.start();
		
		
		Thread t11 = new Thread(() -> {
			int minutsnow1 = now.getDayOfMonth();
			System.out.println(now.getDayOfMonth());
			while (true) {
				LocalDateTime now1 = LocalDateTime.now();
				if (now1.getDayOfMonth() == 1) {
					minutsnow1 = 1;
				}

				if (minutsnow1 == now1.getDayOfMonth()) {
					try {
						mysqlConnection.UpdateEveryDay("D");

						minutsnow1++;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		t11.start();

	}

	public static void importdata() {
		try {
			mysqlConnection.insidealldatafromBiteMeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
//End of EchoServer class
