package server;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.mysql.cj.conf.ConnectionUrl.Type;
import javafx.application.*;

import client.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import common.Addition;
import common.BranchManager;
import common.CEOuser;
import common.Category;
import common.Company;
import common.Delivery;
import common.GroupDelivery;
import common.HRUser;
import common.HistogramCEO;
import common.HistogramController;
import common.InComeReport;
import common.Item;
import common.ItemAddition;
import common.Menu;
import common.Normal;
import common.Order;
import common.OrderHistory;
import common.OrdersReport;
import common.PDFList;
import common.PerformenceReport;
import common.ReportCreator;
import common.RestaurantManager;
import common.RestaurantReport;
import common.Resturaunt;
import common.User;
import common.Visa;
import common.W4CBussiness;
import common.W4CNormal;
import common.BussinessUser;
import common.WorkerUser;
import gui.NormalUserHomePageController;
import gui.RestaurantWorkerHomePage;

/**
 * this class to get all information from DB
 * 
 * @author asem
 *
 */
public class mysqlConnection implements Initializable {
	public static User user;
	public static User user1 = null;
	public static ArrayList<String> list;
	public static ArrayList<BussinessUser> requestsList;
	public static ArrayList<User> getlistofnormalaccount;
	public static ArrayList<User> usersfromBiteMeDB;
	public static ArrayList<Item> Itemss;
	public static ArrayList<Item> AllItems;
	public static ArrayList<Company> companys;
	public static ArrayList<Resturaunt> getAllresturaunt;
	public static RestaurantManager restaurantManager;
	public static WorkerUser WorkerUser;
	public static BranchManager branchManager;
	public static HRUser HRManager;
	public static Normal Normaluser;
	public static ArrayList<RestaurantReport> ReportList;
	public static ArrayList<RestaurantReport> ReportListForManager;
	public static Connection conn;
	public static Resturaunt Resturaunt;
	public static ArrayList<Normal> NormalUsersNotAccepted;
	public static BussinessUser BussinessUser;
	public static CEOuser CEOuser1;
	public static ArrayList<User> TakeAllUserThatNotConfiredyet;
	public static ArrayList<User> userfrombitemedata;
	public static ArrayList<Company> CompanyList;
	public static ArrayList<Order> AllOrder;
	public static ArrayList<Item> Allitemsoforders;
	public static ArrayList<ItemAddition> itemsandAddition;
	public static ArrayList<OrdersReport> ordersReport;
	public static ArrayList<InComeReport> incomearr;
	public static ArrayList<PerformenceReport> PerfReport;
	public static ArrayList<HistogramCEO> HistogramArray;
	public static User LoginUser;
	public static int ReportIDCounter = 1;

	/**
	 * @return return connect
	 */
	@SuppressWarnings("deprecation")
	public static Connection connectToDB() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			String s1 = "jdbc:mysql://localhost/" + ServerPortFrameController.DBInfo.get(0) + "?serverTimezone=IST";
			String s2 = "root";
			String s3 = ServerPortFrameController.DBInfo.get(2);
			conn = DriverManager.getConnection(s1, s2, s3);
			System.out.println("SQL connection succeed");
			return conn;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}

	public static Connection connectToBiteMeDB() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
//		"jdbc:mysql://localhost/assigment2?serverTimezone=IST", "root","912000bashar"
		try {
			String s1 = "jdbc:mysql://localhost/" + ServerPortFrameController.DBInfo.get(1) + "?serverTimezone=IST";
			String s2 = "root";
			String s3 = ServerPortFrameController.DBInfo.get(2);
			conn = DriverManager.getConnection(s1, s2, s3);
			System.out.println("SQL connection succeed To BiteMe DB");
			return conn;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	/**
	 * Get monthly taxes for each given restaurant
	 * 
	 * @param RestaurantID
	 * @param TaxesYear
	 * @param TaxesMonth
	 * @return
	 * @throws SQLException
	 */
	public static String ShowMonthlyTaxes(int RestaurantID, int TaxesYear, int TaxesMonth) throws SQLException {
		LocalDateTime today = LocalDateTime.now();
		if (TaxesYear > today.getYear()) {
			return "NotPassed";
		}
		if (TaxesMonth > today.getMonthValue() && TaxesYear == today.getYear()) {
			return "NotPassed";
		}
		if (TaxesYear < ServerUI.OpeningDate.getYear())
			return "NotOpend";
		if (TaxesYear == ServerUI.OpeningDate.getYear() && TaxesMonth < ServerUI.OpeningDate.getMonthValue()) {
			return "NotOpend";
		}
		String RestaurantName = null;
		double Taxes = 0;
		int ID = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.restaurant where IDRestaurant='" + RestaurantID + "'");
		if (rs.next() == true) {
			RestaurantName = rs.getString(2);
			Taxes = rs.getDouble(5);
		}
		Statement stmt5 = conn.createStatement();
		ResultSet rs5 = stmt5.executeQuery("SELECT * FROM assignment3.taxesincomemonthly where RestaurantName='"
				+ RestaurantName + "' and Year='" + TaxesYear + "' and Month ='" + TaxesMonth + "'");
		if (rs5.next()) {
			return String.valueOf(rs5.getDouble(4)) + " " + String.valueOf(rs5.getDouble(5)) + " "
					+ String.valueOf(Taxes);
		}
		return "NotOpend";
	}
	
	/**
	 * Insert the taxes every month for every restaurant
	 * @param Location
	 * @param year
	 * @param month
	 * @throws SQLException
	 */
	public static void InsertMonthlyTaxes(String Location, int year, int month) throws SQLException {
		String RestaurantName = null;
		double Taxes;
		String Type = "InCome";
		int ID = 0;
		double InCome = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM assignment3.restaurant where location='" + Location + "' and confirm ='" + 1 + "'");
		while (rs.next() == true) {
			InCome = 0;
			RestaurantName = rs.getString(2);
			Taxes = rs.getDouble(5);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.reports where Location='" + Location
					+ "' and Year='" + year + "' and Month ='" + month + "' and Type ='" + Type + "'");
			if (rs1.next() == true) {
				ID = rs1.getInt(5);
				Statement stmt2;
				stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.incomereports where ID='" + ID + "'");
				while (rs2.next()) {
					InCome += rs2.getDouble(3);
				}
				double LastInCome = (1 - ((double) Taxes / 100.0)) * InCome;
				String query2 = " insert into assignment3.taxesincomemonthly (RestaurantName,Year,Month,InComeBeforeTaxes,InComeAfterTaxes)"
						+ " values (?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
				preparedStmt2.setString(1, RestaurantName);
				preparedStmt2.setInt(2, year);
				preparedStmt2.setInt(3, month);
				preparedStmt2.setDouble(4, InCome);
				preparedStmt2.setDouble(5, LastInCome);
				preparedStmt2.execute();
			}
		}
	}


	/**
	 * Given orderNum
	 * 
	 * @param orderNum
	 * @return RestaurantID if exists else -1
	 */
	public static int GetRestaurantID(int orderNum) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from assignment3.order where orderNum=" + orderNum);
			if (rs.next()) {
				return rs.getInt(2);
			}
		} catch (SQLException ex) {/* handle any errors */
			ex.printStackTrace();
		}
		return -1;
	}

	/**
	 * Getting ready InCome report from DB for wanted Restaurant name,year,month
	 * 
	 * @param LocationInCome
	 * @param RestaurantNameInCome
	 * @param YearInCome
	 * @param MonthInCome
	 * @return
	 * @throws SQLException
	 */
	public static String GetInComeReport(String LocationInCome, String RestaurantNameInCome, int YearInCome,
			int MonthInCome) throws SQLException {
		LocalDateTime today = LocalDateTime.now();
		incomearr = new ArrayList<>();
		if (YearInCome > today.getYear()) {
			return "NotPassed";
		}
		if (MonthInCome > today.getMonthValue() && YearInCome == today.getYear()) {
			return "NotPassed";
		}
		boolean flag = false;
		Statement stmt;
		stmt = conn.createStatement();
		String type = "InCome";
		ResultSet rs1 = stmt.executeQuery("SELECT * FROM assignment3.reports where Location='" + LocationInCome
				+ "' and Year='" + YearInCome + "' and Month ='" + MonthInCome + "' and Type ='" + type + "'");
		while (rs1.next() == true) {
			int ID = rs1.getInt(5);
			Statement stmt1;
			stmt1 = conn.createStatement();
			ResultSet rs2 = stmt1.executeQuery("SELECT * FROM assignment3.incomereports where ID='" + ID
					+ "' and RestaurantName ='" + RestaurantNameInCome + "'");
			while (rs2.next() == true) {
				flag = true;
				InComeReport income = new InComeReport(rs2.getString(2), rs2.getDouble(3), rs2.getInt(4));
				incomearr.add(income);
			}
		}
		if (flag==true)
			return "Done";
		if(YearInCome<ServerUI.OpeningDate.getYear())
			return "NotOpend";
		if(YearInCome==ServerUI.OpeningDate.getYear() && MonthInCome<ServerUI.OpeningDate.getMonthValue()) {
			return "NotOpend";
		}
		return "NoSales";
	}

	/**
	 * Getting ready Order report from DB for wanted Restaurant name,year,month
	 * 
	 * @param RestaurantName
	 * @param Year
	 * @param Month
	 * @param Location
	 * @return report status
	 * @throws SQLException
	 */
	public static String GetOrderReport(String RestaurantName, int Year, int Month, String Location)
			throws SQLException {
		LocalDateTime today = LocalDateTime.now();
		if (Year > today.getYear()) {
			return "NotPassed";
		}
		if (Month > today.getMonthValue() && Year == today.getYear()) {
			return "NotPassed";
		}
		ordersReport = new ArrayList<OrdersReport>();
		boolean flag = false;
		Statement stmt;
		stmt = conn.createStatement();
		String type = "Order";
		ResultSet rs1 = stmt.executeQuery("SELECT * FROM assignment3.reports where Location='" + Location
				+ "' and Year='" + Year + "' and Month ='" + Month + "' and Type ='" + type + "'");
		while (rs1.next() == true) {
			int ID = rs1.getInt(5);
			Statement stmt1;
			stmt1 = conn.createStatement();
			ResultSet rs2 = stmt1.executeQuery("SELECT * FROM assignment3.ordersreport where ID='" + ID
					+ "' and RestaurantName ='" + RestaurantName + "'");
			while (rs2.next() == true) {
				flag = true;
				OrdersReport orderReport = new OrdersReport(rs2.getString(2), rs2.getString(3), rs2.getInt(4));
				ordersReport.add(orderReport);
			}
			System.out.println(ordersReport.toString());
		}
		if (flag==true)
			return "Done";
		if(Year<ServerUI.OpeningDate.getYear())
			return "NotOpend";
		if(Year==ServerUI.OpeningDate.getYear() && Month<ServerUI.OpeningDate.getMonthValue()) {
			return "NotOpend";
		}
		return "NoSales";
	}

	/**
	 * Getting ready Performence report from DB for wanted Restaurant
	 * name,year,month
	 * 
	 * @param RestaurantName
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public static String GetPerformenceReport(String RestaurantName, int year, int month) throws SQLException {
		PerfReport = new ArrayList<PerformenceReport>();
		LocalDateTime today = LocalDateTime.now();
		if (year > today.getYear()) {
			return "NotPassed";
		}
		if (month > today.getMonthValue() && year == today.getYear()) {
			return "NotPassed";
		}
		Statement stmt;
		System.out.println(year+" "+month+" "+RestaurantName);
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.performencereports where RestaurantName='"
				+ RestaurantName + "' and Year='" + year + "' and Month ='" + month + "'");
		while (rs.next()) {
			PerformenceReport a = new PerformenceReport(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
					rs.getInt(5));
			PerfReport.add(a);
			return "Done";
		}
		if(year<ServerUI.OpeningDate.getYear())
			return "NotOpend";
		if(year==ServerUI.OpeningDate.getYear() && month<ServerUI.OpeningDate.getMonthValue()) {
			return "NotOpend";
		}
		return "NoSales";	}

	/**
	 * The function taking all the PDF files that was sent by the managers Inserting
	 * the PDF files in arraylist and returning it
	 * 
	 * @return ArrayList from PDFList type
	 * @throws SQLException
	 */
	public static ArrayList<PDFList> PDFLists() throws SQLException {
		System.out.println("First");
		ArrayList<PDFList> PDFListArray = new ArrayList<>();
		Statement stmt1;
		stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.quarterlypdf where Sent='" + 1 + "'");
		while (rs1.next()) {
			PDFList P = new PDFList(rs1.getString(1), rs1.getInt(2), rs1.getString(3));
			PDFListArray.add(P);
		}
		return PDFListArray;
	}

	/**
	 * The function receives location,year and quarter Send the PDF report file by
	 * the year and the quarter wanted to CEO PDF list return if the report quarter
	 * of the report passed or not
	 * 
	 * @param location
	 * @param year
	 * @param Months
	 * @return Quarter status
	 * @throws SQLException
	 */
	public static String SendPDFToCeo(String location, int year, String Months) throws SQLException {
		LocalDateTime today = LocalDateTime.now();
		Statement stmt1;
		stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.quarterlypdf where Location='" + location
				+ "' and Year='" + year + "' and Month ='" + Months + "'");
		if (rs1.next()) {
			String query = "update assignment3.quarterlypdf set Sent = ? where Location = ? and Year = ? and Month = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, 1);
			preparedStmt.setString(2, location);
			preparedStmt.setInt(3, year);
			preparedStmt.setString(4, Months);
			preparedStmt.executeUpdate();
			return "Updated";
		}
		int month1 = 0;
		if (Months.equals("1,2,3")) {
			month1 = 1;
		}
		if (Months.equals("4,5,6")) {
			month1 = 4;
		}
		if (Months.equals("7,8,9")) {
			month1 = 7;
		}
		if (Months.equals("10,11,12")) {
			month1 = 10;
		}
		if (year > today.getYear()) {
			return "NotPassed";
		}
		if (month1+2 > today.getMonthValue() && year == today.getYear()) {
			return "NotPassed";
		}
		if(year<ServerUI.OpeningDate.getYear())
			return "NotOpend";
		if(year==ServerUI.OpeningDate.getYear() && month1+2<ServerUI.OpeningDate.getMonthValue()) {
			return "NotOpend";
		}
		return "NoSales";
	}
	
	/**
	 * get the worker
	 * @param user
	 * @return
	 */
	static WorkerUser GetWorker(User user){
 	  Statement stmt, stmt1;
		int status;
		try {
			stmt1 = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.restaurantWorker where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.restaurant where IDRestaurant='"
						+ Integer.parseInt(rs.getString(6)) + "'and confirm=1");
				if (rs1.next() == true) {
					WorkerUser = new WorkerUser(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)));

					return WorkerUser;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
     }

	/**
	 * Checking if the month is passing quarter if the month is passin quarter, the
	 * function inserting the three PDF file reports to DB saving the PDF report in
	 * specific path
	 * 
	 * @param Location
	 * @param year
	 * @param month
	 * @throws Exception
	 */
	public static void InsertPDFtoDB(String Location, int year, int month) throws Exception {
		if (month % 3 != 0) {
			return;
		}
		ArrayList<OrdersReport> OReport = new ArrayList<>();
		ArrayList<PerformenceReport> PReport = new ArrayList<>();
		ArrayList<InComeReport> IReport = new ArrayList<>();
		String StringMonth = null;
		if (month == 12) {
			StringMonth = "10,11,12";
		}
		if (month == 9) {
			StringMonth = "7,8,9";
		}
		if (month == 6) {
			StringMonth = "4,5,6";
		}
		if (month == 3) {
			StringMonth = "1,2,3";
		}
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.restaurant where Location='" + Location + "'");
		while (rs.next()) {
			String RestaurantName = rs.getString(2);
			System.out.println(RestaurantName);
			GetInComeReport(Location, RestaurantName, year, month - 2);
			IReport.addAll(incomearr);
			GetInComeReport(Location, RestaurantName, year, month - 1);
			IReport.addAll(incomearr);
			GetInComeReport(Location, RestaurantName, year, month);
			IReport.addAll(incomearr);
			GetOrderReport(RestaurantName, year, month - 2, Location);
			OReport.addAll(ordersReport);
			GetOrderReport(RestaurantName, year, month - 1, Location);
			OReport.addAll(ordersReport);
			GetOrderReport(RestaurantName, year, month - 2, Location);
			OReport.addAll(ordersReport);
			GetPerformenceReport(RestaurantName, year, month - 2);
			PReport.addAll(PerfReport);
			GetPerformenceReport(RestaurantName, year, month - 1);
			PReport.addAll(PerfReport);
			GetPerformenceReport(RestaurantName, year, month);
			PReport.addAll(PerfReport);
		}
		String str = Location + String.valueOf(year) + String.valueOf(month);
		CreatePDF(PReport, IReport, OReport, str, StringMonth, year, Location);
		Thread.sleep(6000);
		String query2 = " insert into assignment3.quarterlypdf (Location,Year,Month,PDF,Sent)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
		preparedStmt2.setString(1, Location);
		preparedStmt2.setInt(2, year);
		preparedStmt2.setString(3, StringMonth);
		InputStream inputStream = new FileInputStream(new File("C:\\G5BiteMe\\Report" + str + ".pdf"));
		preparedStmt2.setBlob(4, inputStream);
		preparedStmt2.setInt(5, 0);
		preparedStmt2.execute();
	}

	/**
	 * The function three reports,path name,quarter,year and locaion Creating PDF
	 * Checking if there is any order in the quarter Create Pie Chart for
	 * Performence report Creating Pie Chart for Orders report Creating BarChart for
	 * InCome report Insert the three charts to PDF
	 * 
	 * @param PReport
	 * @param IReport
	 * @param OReport
	 * @param LYM
	 * @param StringMonth
	 * @param year
	 * @param Location
	 * @throws Exception
	 */
	public static void CreatePDF(ArrayList<PerformenceReport> PReport, ArrayList<InComeReport> IReport,
			ArrayList<OrdersReport> OReport, String LYM, String StringMonth, int year, String Location)
			throws Exception {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String pdfFilePath = "C:\\G5BiteMe\\Report" + LYM + ".pdf";
				OutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(pdfFilePath));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Document document = new Document();
				PdfWriter writer = null;
				try {
					writer = PdfWriter.getInstance(document, fos);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				document.open();
				if (OReport.size() > 0) {
					try {
						document.add(new Paragraph("Quarter Report \n Months(" + StringMonth + ") / Year (" + year
								+ ") \n District (" + Location + ")"));
						document.add(new Paragraph("\n"));
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						document.newPage();
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PdfContentByte pdfContentByte = writer.getDirectContent();
					int width = 400;
					int height = 300;
					PdfTemplate pdfTemplate = pdfContentByte.createTemplate(width, height);
					Graphics2D graphics2d = pdfTemplate.createGraphics(width, height, new DefaultFontMapper());
					java.awt.geom.Rectangle2D rectangle2d = new java.awt.geom.Rectangle2D.Double(0, 0, width, height);
					ReportCreator.OrderArray = OReport;
					ReportCreator.Type = "Order";
					new ReportCreator().start(new Stage());
					ReportCreator.frame.setVisible(false);
					ReportCreator.chart.draw(graphics2d, rectangle2d);
					graphics2d.dispose();
					pdfContentByte.addTemplate(pdfTemplate, 40, 500);
					try {
						document.newPage();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PdfContentByte pdfContentByte1 = writer.getDirectContent();
					int width1 = 400;
					int height1 = 300;
					PdfTemplate pdfTemplate1 = pdfContentByte1.createTemplate(width1, height1);
					Graphics2D graphics2d1 = pdfTemplate1.createGraphics(width1, height1, new DefaultFontMapper());
					java.awt.geom.Rectangle2D rectangle2d1 = new java.awt.geom.Rectangle2D.Double(0, 0, width1,
							height1);
					ReportCreator.Perf = PReport;
					ReportCreator.Type = "Performencereports";
					new ReportCreator().start(new Stage());
					ReportCreator.frame.setVisible(false);
					ReportCreator.chart.draw(graphics2d1, rectangle2d1);
					graphics2d1.dispose();
					pdfContentByte1.addTemplate(pdfTemplate1, 40, 500);
					try {
						document.newPage();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PdfContentByte pdfContentByte11 = writer.getDirectContent();
					int width11 = 400;
					int height11 = 300;
					PdfTemplate pdfTemplate11 = pdfContentByte11.createTemplate(width11, height11);
					Graphics2D graphics2d11 = pdfTemplate11.createGraphics(width11, height11, new DefaultFontMapper());
					java.awt.geom.Rectangle2D rectangle2d11 = new java.awt.geom.Rectangle2D.Double(0, 0, width11,
							height11);
					HistogramController.InComeArray = IReport;
					try {
						new HistogramController().start(new Stage());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					HistogramController.frame.setVisible(false);
					HistogramController.jFreeChart.draw(graphics2d11, rectangle2d11);
					graphics2d11.dispose();
					pdfContentByte11.addTemplate(pdfTemplate11, 40, 500);
				} else {
					try {
						document.add(new Paragraph("In this Quarter there is no sales"));
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				document.close();

			}

		});
	}

	/**
	 * Creating InCome and sold report for specific quarter set the values into
	 * Arraylist from HistogramCEO type return if the report quarter of the report
	 * passed or not
	 * 
	 * @param RestaurantName
	 * @param year1
	 * @param months
	 * @return
	 * @throws SQLException
	 */
	public static String GetHistogram(String RestaurantName, int year1, String months) throws SQLException {
		LocalDateTime today = LocalDateTime.now();
		HistogramArray = new ArrayList<HistogramCEO>();
		boolean flag = false;
		int month1 = 0;
		if (months.equals("1,2,3")) {
			month1 = 1;
		}
		if (months.equals("4,5,6")) {
			month1 = 4;
		}
		if (months.equals("7,8,9")) {
			month1 = 7;
		}
		if (months.equals("10,11,12")) {
			month1 = 10;
		}
		if (year1 > today.getYear()) {
			return "NotPassed";
		}
		if (month1+2 > today.getMonthValue() && year1 == today.getYear()) {
			return "NotPassed";
		}
		if(year1<ServerUI.OpeningDate.getYear())
			return "NotOpend";
		if(year1==ServerUI.OpeningDate.getYear() && month1+2<ServerUI.OpeningDate.getMonthValue()) {
			return "NotOpend";
		}
		HistogramArray = new ArrayList<>();
		String area = null;
		int RestaurantID = 0;
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM assignment3.restaurant where RestaurantName='" + RestaurantName + "'");
		if (rs.next()) {
			area = rs.getString(3);
			RestaurantID = rs.getInt(1);
		}
		String type = "InCome";
		double InCome = 0;
		int Sold = 0;
		int ID = 0;
		for (int i = 0; i < 3; i++) {
			InCome = 0;
			Sold = 0;
			Statement stmt2;
			stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.reports where Location='" + area
					+ "' and Month='" + month1 + "' and Year='" + year1 + "' and Type='" + type + "'");
			while (rs2.next()) {
				ID = rs2.getInt(5);
				Statement stmt3;
				stmt3 = conn.createStatement();
				ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.incomereports where ID='" + ID + "'");
				while (rs3.next()) {
					InCome += rs3.getDouble(3);
				}
				Statement stmt4;
				stmt4 = conn.createStatement();
				ResultSet rs4 = stmt4.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='"
						+ RestaurantID + "' and Month='" + month1 + "' and Year='" + year1 + "'");
				while (rs4.next()) {
					Sold += rs4.getInt(3);
				}
				HistogramCEO Element = new HistogramCEO(RestaurantName, InCome, Sold);
				HistogramArray.add(Element);
				month1++;
			}
		}
		return "Done";

	}

	/**
	 * The function receives Location,Restaurant Name,Year,Month Creating ArrayList
	 * from InComeReport type Set the report values in the arraylist return if the
	 * report month of the report passed or not
	 * 
	 * @param LocationInCome
	 * @param RestaurantNameInCome
	 * @param YearInCome
	 * @param MonthInCome
	 * @return Report status
	 * @throws SQLException
	 */
	static void CreateInComeReport(String location, int month, int year) throws SQLException {
		boolean flag = false;
		incomearr = new ArrayList<InComeReport>();
		double income = 0;
		int Day = 0;
		try {
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			Statement stmt3 = conn.createStatement();
			ResultSet rs1 = stmt1
					.executeQuery("SELECT * FROM assignment3.restaurant where location='" + location + "'");
			while (rs1.next() == true) {
				flag = true;
				income = 0;
				int IDRestaurant = rs1.getInt(1);
				String RestaurantName = rs1.getString(2);
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='"
						+ IDRestaurant + "' and Month='" + month + "' and Year='" + year + "'");
				while (rs2.next() == true) {
					income = 0;
					int ItemID = Integer.parseInt(rs2.getString(2));
					int sold = Integer.parseInt(rs2.getString(3));
					Day = rs2.getInt(6);
					ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + ItemID + "'");
					if (rs3.next() == true) {
						income = sold * rs3.getDouble(3);
					}
					InComeReport ICR = new InComeReport(RestaurantName, income, Day);
					incomearr.add(ICR);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag) {
			String query2 = " insert into assignment3.reports (Type,Location,Year,Month,ID)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, "InCome");
			preparedStmt2.setString(2, location);
			preparedStmt2.setInt(3, year);
			preparedStmt2.setInt(4, month);
			preparedStmt2.setInt(5, ReportIDCounter);
			preparedStmt2.execute();

			for (int i = 0; i < incomearr.size(); i++) {
				String query1 = " insert into assignment3.incomereports (ID,RestaurantName,InCome,Day)"
						+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
				preparedStmt1.setInt(1, ReportIDCounter);
				preparedStmt1.setString(2, incomearr.get(i).getRestaurantName());
				preparedStmt1.setDouble(3, incomearr.get(i).getIncome());
				preparedStmt1.setInt(4, incomearr.get(i).getDay());
				preparedStmt1.execute();
			}
			ReportIDCounter++;
		}

	}

	/**
	 * The function receives Location,Restaurant Name,Year,Month Creating ArrayList
	 * from OrdersReport type Set the report values in the arraylist return if the
	 * report month of the report passed or not
	 * 
	 * @param LocationInCome
	 * @param RestaurantNameInCome
	 * @param YearInCome
	 * @param MonthInCome
	 * @return Report status
	 * @throws SQLException
	 */
	static void CreateOrdersReport(String location, int month, int year) throws SQLException {
		boolean flag = false;
		boolean flag2 = false;
		Statement stmt1;
		Statement stmt2;
		Statement stmt3;
		ordersReport = new ArrayList<OrdersReport>();
		try {
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			ResultSet rs1 = stmt1
					.executeQuery("SELECT * FROM assignment3.restaurant where location='" + location + "'");
			while (rs1.next() == true) {
				flag = true;
				int IDRestaurant = rs1.getInt(1);
				String RestaurantName = rs1.getString(2);
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='"
						+ IDRestaurant + "' and Month='" + month + "' and Year='" + year + "'");
				while (rs2.next() == true) {
					int sold = rs2.getInt(3);
					int ItemID = Integer.parseInt(rs2.getString(2));
					ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + ItemID + "'");
					while (rs3.next() == true) {
						String Catalog = rs3.getString(4);
						OrdersReport Order = new OrdersReport(RestaurantName, Catalog, sold);
						for (int i = 0; i < ordersReport.size(); i++) {
							if (ordersReport.get(i).getRestaurantName().equals(Order.getRestaurantName())
									&& ordersReport.get(i).getCatalog().equals(Order.getCatalog())) {
								ordersReport.get(i).setSold(ordersReport.get(i).getSold() + Order.getSold());
								flag2 = true;
							}
						}
						if (!flag2)
							ordersReport.add(Order);
						flag2 = false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag) {
			String query2 = " insert into assignment3.reports (Type,Location,Year,Month,ID)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, "Order");
			preparedStmt2.setString(2, location);
			preparedStmt2.setInt(3, year);
			preparedStmt2.setInt(4, month);
			preparedStmt2.setInt(5, ReportIDCounter);
			preparedStmt2.execute();
			for (int i = 0; i < ordersReport.size(); i++) {
				String query1 = " insert into assignment3.ordersreport (ID,RestaurantName,Catalog,Sold)"
						+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
				preparedStmt1.setInt(1, ReportIDCounter);
				preparedStmt1.setString(2, ordersReport.get(i).getRestaurantName());
				preparedStmt1.setString(3, ordersReport.get(i).getCatalog());
				preparedStmt1.setInt(4, ordersReport.get(i).getSold());
				preparedStmt1.execute();
			}
			ReportIDCounter++;
		}

	}

	/**
	 * this class add every month the value that hr give it to user
	 * 
	 * @param M the type monthly or daily
	 * @throws SQLException
	 */
	public static void UpdateEveryMonth(String M) throws SQLException {
		PreparedStatement ps;
		double money = 0.0;
		double amount = 0.0;
		double val = 0.0;

		Statement stmt = conn.createStatement();
		try {

			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.w4cbussiness where Type='" + M + "'");
			while (rs.next()) {
				String ResId = rs.getString(3);
				money = rs.getDouble(2);
				val = rs.getDouble(5);
				amount = money + val;
				if (money == val) {
					ps = conn.prepareStatement("UPDATE assignment3.w4cbussiness SET money= ? WHERE IDuser = ?");
					ps.setDouble(1, val);
					ps.setString(2, ResId);
					ps.execute();
					amount = 0.0;
					money = 0.0;
					val = 0.0;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * this class add every day the value that hr give it to user
	 * 
	 * @param D the type monthly or daily
	 * @throws SQLException
	 */
	public static void UpdateEveryDay(String D) throws SQLException {
		PreparedStatement ps;
		double money = 0.0;
		double amount = 0.0;
		double val = 0.0;

		Statement stmt = conn.createStatement();
		try {

			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.w4cbussiness where Type='" + D + "'");
			while (rs.next()) {
				String ResId = rs.getString(3);
				money = rs.getDouble(2);
				val = rs.getDouble(5);
				amount = money + val;
				if (money != val) {
					ps = conn.prepareStatement("UPDATE assignment3.w4cbussiness SET money= ? WHERE IDuser = ?");
					ps.setDouble(1, val);
					ps.setString(2, ResId);
					ps.execute();
					amount = 0.0;
					money = 0.0;
					val = 0.0;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * User pays with refund money updating its value in DB
	 * 
	 * @param userid
	 * @param Price
	 * @param restaurantID
	 */
	public static void PayWithRefund(String userid, double Price, int restaurantID) {
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement("UPDATE assignment3.refund SET Value= ? WHERE UserID = ? and RestaurantID = ?");
			ps.setDouble(1, Price);
			ps.setString(2, userid);
			ps.setInt(3, restaurantID);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * this method put all confirmed restaurant in array list and send it to client
	 * 
	 * @param Location
	 */
	static void GetConfirmRestaurants(String Location) {
		getAllresturaunt = new ArrayList<Resturaunt>();
		try {
			Statement ps = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * FROM assignment3.restaurant where Location='" + Location + "' and confirm='" + 1 + "'");
			Resturaunt request;
			while (RS.next()) {
				request = new Resturaunt(Integer.parseInt(RS.getString(1)), RS.getString(2), RS.getString(3));
				getAllresturaunt.add(request);

			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get amount of refund
	 * 
	 * @param userid
	 * @param restaurantid
	 * @return amount of refund money if exists else -1
	 */
	public static double GetRefund(String userid, int restaurantid) {
		ResultSet rs = null;
		String query = "select * from assignment3.refund where UserID=" + userid + " and RestaurantID=" + restaurantid;
		Statement st;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next() == true) {
				return rs.getDouble(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * this method updating w4c
	 * 
	 * @param user =>business user that we want to update w4c
	 */
	public static void updateW4CforBussiness(BussinessUser user) {
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement("UPDATE assignment3.w4cbussiness SET money= ? WHERE IDuser = ?");
			ps.setDouble(1, user.getW4c().getMoney());
			ps.setString(2, user.getID());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int GetGroupSize(int number) {
		ResultSet rs = null;
		int group_Size = 0;
		String query = "select * from assignment3.group_delivery where code=" + number;
		Statement st;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next())
				group_Size = rs.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group_Size;
	}

	/**
	 * @param number
	 * @return true if group exists else false
	 * @throws SQLException
	 */
	public static boolean GetGroupNumber(int number) throws SQLException {
		ResultSet rs = null;
		String query = "select * from assignment3.group_delivery where code=" + number;
		Statement st;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs.next();
	}

	/**
	 * Start group delivery for the creator of shared delivery
	 * 
	 * @param gp
	 */
	public static void InsertGroup(GroupDelivery gp) {
		String sql = "insert into assignment3.group_delivery (code,group_size) values (?,?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, gp.getGroupNum());
			preparedStmt.setInt(2, gp.getGroupSize());
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * update each sold item of order to its restaurant
	 * 
	 * @param solditems
	 */
	public static void soldItems(Order solditems) {
		LocalDateTime today = LocalDateTime.now();
		Statement st;
		ResultSet rs = null;
		String sql;
		int soldupdate = 0;
		int updatesold = 0;
		for (int i = 0; i < solditems.getItems().size(); i++) {
			String query2 = "select quantity from assignment3.menu where IDRestaurant="
					+ solditems.getRes().getResturauntID() + " and Item_ID=" + solditems.getItems().get(i).getItem_ID();
			try {
				st = conn.createStatement();
				rs = st.executeQuery(query2);
				if (rs.next() == true) {
					updatesold = rs.getInt(1);
					updatesold -= solditems.getItems().get(i).getQuantity();
					sql = "UPDATE assignment3.menu SET quantity= ? where IDRestaurant="
							+ solditems.getRes().getResturauntID() + " and Item_ID="
							+ solditems.getItems().get(i).getItem_ID();
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, updatesold);
					preparedStmt.execute();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String query = "select Sold from assignment3.solditem where IDRestaurant="
					+ solditems.getRes().getResturauntID() + " and ItemID=" + solditems.getItems().get(i).getItem_ID()
					+ " and Month=" + solditems.getMonth() + " and Year=" + solditems.getYear() + " and Day ="
					+ today.getDayOfMonth();
			try {
				st = conn.createStatement();
				rs = st.executeQuery(query);
				if (rs.next() == true) {
					soldupdate = rs.getInt(1);
					soldupdate += solditems.getItems().get(i).getQuantity();
					sql = "UPDATE assignment3.solditem SET Sold= ? where IDRestaurant="
							+ solditems.getRes().getResturauntID() + " and ItemID="
							+ solditems.getItems().get(i).getItem_ID() + " and Month=" + solditems.getMonth()
							+ " and Year=" + solditems.getYear() + " and Day =" + today.getDayOfMonth();
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, soldupdate);
					preparedStmt.execute();

				} else {
					sql = "insert into assignment3.solditem (IDRestaurant,ItemID,Sold,Month,Year,Day) values (?,?,?,?,?,?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, solditems.getRes().getResturauntID());
					preparedStmt.setInt(2, solditems.getItems().get(i).getItem_ID());
					preparedStmt.setInt(3, solditems.getItems().get(i).getQuantity());
					preparedStmt.setInt(4, solditems.getMonth());
					preparedStmt.setInt(5, solditems.getYear());
					preparedStmt.setInt(6, today.getDayOfMonth());
					preparedStmt.execute();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Creates new delivery for user and order
	 * 
	 * @param del
	 */
	public static void newDelivery(Delivery del) {
		String query = " insert into assignment3.delivery (deliveryNum,orderNum,name,phonenumber,address,deliveryType,deliveryDate,clientTxt)"
				+ " values (?, ?, ?, ?, ?, ?,?,?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, del.getDeliveryNum());
			preparedStmt.setInt(2, del.getOrderNum());
			preparedStmt.setString(3, del.getFirstname());
			preparedStmt.setString(4, del.getPhonenumber());
			preparedStmt.setString(5, del.getAddress());
			preparedStmt.setString(6, del.getDeliveryType());
			preparedStmt.setString(7, del.getDate() + " " + del.getHour() + ":" + del.getMinute());
			preparedStmt.setString(8, del.getClientText());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create new Order to be processed in the restaurant
	 * 
	 * @param order
	 */
	public static void newOrder(Order order) {
		String query = " insert into assignment3.order (orderNum,RestaurantID,userID,submitDate,totalPrice,pickupTime,EarlyBooking)"
				+ " values (?, ?, ?, ?, ?, ?,?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, order.getOrderNum());
			preparedStmt.setInt(2, order.getRes().getResturauntID());
			preparedStmt.setString(3, order.getUser().getID());
			preparedStmt.setString(4, order.getCurrentDateAndTime());
			preparedStmt.setDouble(5, order.getTotalPrice());
			preparedStmt.setString(6, order.getDate() + " " + order.getHour() + ":" + order.getMinute());
			preparedStmt.setString(7, order.getEarlyBooking());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "insert into assignment3.order_items (orderNum,item_ID,quantity) values(?,?,?)";
		try {
			for (int i = 0; i < order.getItems().size(); i++) {
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, order.getOrderNum());
				preparedStmt.setInt(2, order.getItems().get(i).getItem_ID());
				preparedStmt.setInt(3, order.getItems().get(i).getQuantity());
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save order details (item,addition)
	 * 
	 * @param itemAddition
	 */
	public static void order_items_additions(Object itemAddition) {
		ArrayList<Item> item_addition = (ArrayList<Item>) itemAddition;
		String query = "insert into assignment3.order_item_addition (orderNum,item_ID,name) values (?,?,?)";

		for (Item i : item_addition) {
			try {
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, i.getOrderNum());
				preparedStmt.setInt(2, i.getItem_ID());
				preparedStmt.setString(3, i.getAdditions_names());
				// preparedStmt.setInt(4, i.getIndex());
				preparedStmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this method to get the order number
	 * 
	 * @return order number
	 */
	public static int getOrderID() {
		Statement st;
		ResultSet rs = null;
		int orderNum = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT COUNT(*) FROM assignment3.order");
			if (rs.next()) {
				orderNum = rs.getInt(1);
				if (orderNum == 0)
					return orderNum;
			}
			rs = st.executeQuery("SELECT * FROM assignment3.order ORDER BY orderNum DESC LIMIT 1");
			if (rs.next()) {
				orderNum = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return orderNum;
	}

	/**
	 * @return last index of order to increase next index for next order
	 */
	public static int getIND() {
		Statement st;
		ResultSet rs = null;
		int ind = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT COUNT(*) FROM assignment3.order_item_addition");
			if (rs.next()) {
				ind = rs.getInt(1);
				if (ind == 0)
					return ind;
			}
			rs = st.executeQuery("SELECT * FROM assignment3.order_item_addition ORDER BY ind DESC LIMIT 1");
			if (rs.next()) {
				ind = rs.getInt(4);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ind;
	}

	/**
	 * This method when i click is start in server it take all data from bite me
	 * user table and department the data to users table and restaurant branch
	 * manager case : take all the information of user (branch manager ) and put
	 * them in branch manager table in assignment3 CEO manager case : take all the
	 * information of user (CEO ) and put them in CEO table in assignment3 worker
	 * manager case : take all the information of user (worker ) and put them in
	 * worker table in assignment3 restaurantManager manager case : take all the
	 * information of user (restaurantManager ) and put them in restaurantManager
	 * table in assignment3 restaurant case : take all the information of restaurant
	 * and put them in restaurant table in assignment3 company case : take all the
	 * information of company and put them in company table in assignment3
	 * 
	 * @throws SQLException
	 */
	public static void insidealldatafromBiteMeDB() throws SQLException {
		userfrombitemedata = new ArrayList<User>();
		Statement ps = conn.createStatement();
		Statement ps1 = conn.createStatement();
		Visa uservisa = null;
		ResultSet RS = ps.executeQuery("SELECT * From biteme_data.users");
		User user = null;
		while (RS.next()) {
			try {
			try {
				user = new User(RS.getString(1), RS.getString(4), RS.getString(5), RS.getString(8), RS.getString(2),
						RS.getString(6), RS.getString(7), RS.getString(3), RS.getString(14), RS.getString(13), 0,
						Integer.parseInt(RS.getString(11)), RS.getString(12), Integer.parseInt(RS.getString(10)),
						Integer.parseInt(RS.getString(15)));
				userfrombitemedata.add(user);
				if(user.getUserType().equals("null")) {
				uservisa=new Visa(RS.getString(1),RS.getString(17),Integer.parseInt(RS.getString(18)),Integer.parseInt(RS.getString(19)),RS.getString(16),Integer.parseInt(RS.getString(20)));
				}
				String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
						+ " values (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, user.getID());
				preparedStmt.setString(2, user.getUserName());
				preparedStmt.setString(3, user.getPassword());
				preparedStmt.setString(4, user.getUserType());
				preparedStmt.setInt(5, 0);
				preparedStmt.setInt(6, user.getConfirm());
				preparedStmt.setString(7, user.getStatus());
				preparedStmt.execute();
			} catch (Exception e) {
				System.out.println("importing data ...");
			}
			switch (user.getUserType()) {
			case "BranchManager":
				try {
					String query1 = " insert into assignment3.branchmanager (ID,FirstName,LastName,Email,PhoneNumber,location)"
							+ " values (?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
					preparedStmt1.setString(1, user.getID());
					preparedStmt1.setString(2, user.getFirstname());
					preparedStmt1.setString(3, user.getLastname());
					preparedStmt1.setString(4, user.getEmail());
					preparedStmt1.setString(5, user.getPhonenumber());
					preparedStmt1.setString(6, user.getLocation());
					preparedStmt1.execute();
				} catch (Exception e) {
					System.out.println("Branch Manager Imported");
				}
				break;
			case "Restaurant":
				try {
					String query8 = " insert into assignment3.restaurant (IDRestaurant,RestaurantName,location,confirm)"
							+ " values (?, ?, ?, ?)";
					PreparedStatement preparedStmt8 = conn.prepareStatement(query8);
					preparedStmt8.setInt(1, user.getIdrestaurant());
					preparedStmt8.setString(2, user.getFirstname());
					preparedStmt8.setString(3, user.getLocation());
					preparedStmt8.setInt(4, user.getConfirm());
					preparedStmt8.execute();
				} catch (Exception e) {
					System.out.println("Restaurant Imported");
				}
				break;

			case "Company":
				try {
					String query9 = " insert into assignment3.company (companyname,confirm)" + " values (?, ?)";
					PreparedStatement preparedStmt9 = conn.prepareStatement(query9);
					preparedStmt9.setString(1, user.getCompany());
					preparedStmt9.setInt(2, user.getConfirm());
					preparedStmt9.execute();
				} catch (Exception e) {
					System.out.println("Company Imported");
				}
				break;
			case "Normal":
				try {
					String query2 = " insert into assignment3.normaluser (ID,FirstName,LastName,Email,Phone,VisaIsAvailable)"
							+ " values (?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
					preparedStmt2.setString(1, user.getID());
					preparedStmt2.setString(2, user.getFirstname());
					preparedStmt2.setString(3, user.getLastname());
					preparedStmt2.setString(4, user.getEmail());
					preparedStmt2.setString(5, user.getPhonenumber());
					preparedStmt2.setInt(6, user.getVisaavailable());
					preparedStmt2.execute();
				} catch (Exception e) {
					System.out.println("Normal Imported");
				}
				break;
			case "Bussiness":
				try {
					String query3 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,Email,PhoneNumber,Company)"
							+ " values (?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt3 = conn.prepareStatement(query3);
					preparedStmt3.setString(1, user.getID());
					preparedStmt3.setString(2, user.getFirstname());
					preparedStmt3.setString(3, user.getLastname());
					preparedStmt3.setString(4, user.getEmail());
					preparedStmt3.setString(5, user.getPhonenumber());
					preparedStmt3.setString(6, user.getCompany());
					preparedStmt3.execute();
				} catch (Exception e) {
					System.out.println("Bussiness Imported");
				}
				break;
			case "CEO":
				try {
					String query4 = " insert into assignment3.ceouser (ID,FirstName,LastName,Email,PhoneNumber)"
							+ " values (?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt4 = conn.prepareStatement(query4);
					preparedStmt4.setString(1, user.getID());
					preparedStmt4.setString(2, user.getFirstname());
					preparedStmt4.setString(3, user.getLastname());
					preparedStmt4.setString(4, user.getEmail());
					preparedStmt4.setString(5, user.getPhonenumber());
					preparedStmt4.execute();
				} catch (Exception e) {
					System.out.println("CEO Imported");
				}
				break;
			case "RestaurantManager":
				try {
					String query5 = " insert into assignment3.restaurantmanager (ID,FirstName,LastName,Email,PhoneNumber,IDRestaurant)"
							+ " values (?, ?, ?, ?, ?,?)";
					PreparedStatement preparedStmt5 = conn.prepareStatement(query5);
					preparedStmt5.setString(1, user.getID());
					preparedStmt5.setString(2, user.getFirstname());
					preparedStmt5.setString(3, user.getLastname());
					preparedStmt5.setString(4, user.getEmail());
					preparedStmt5.setString(5, user.getPhonenumber());
					preparedStmt5.setInt(6, user.getIdrestaurant());
					preparedStmt5.execute();
				} catch (Exception e) {
					System.out.println("Restaurant Manager Imported");
				}
				break;
			case "HR":
				try {
					Statement stmt;
					String query6 = " insert into assignment3.hruser (ID,FirstName,LastName,Email,PhoneNumber,Company)"
							+ " values (?, ?, ?, ?, ?,?)";
					PreparedStatement preparedStmt6 = conn.prepareStatement(query6);
					preparedStmt6.setString(1, user.getID());
					preparedStmt6.setString(2, user.getFirstname());
					preparedStmt6.setString(3, user.getLastname());
					preparedStmt6.setString(4, user.getEmail());
					preparedStmt6.setString(5, user.getPhonenumber());
					preparedStmt6.setString(6, user.getCompany());
					preparedStmt6.execute();
				} catch (Exception e) {
					System.out.println("HR Imported");
				}
				break;
			case "Worker":
				try {
					String query7 = " insert into assignment3.restaurantworker (ID,FirstName,LastName,Email,PhoneNumber,restaurantWorker)"
							+ " values (?, ?, ?, ?, ?,?)";
					PreparedStatement preparedStmt7 = conn.prepareStatement(query7);
					preparedStmt7.setString(1, user.getID());
					preparedStmt7.setString(2, user.getFirstname());
					preparedStmt7.setString(3, user.getLastname());
					preparedStmt7.setString(4, user.getEmail());
					preparedStmt7.setString(5, user.getPhonenumber());
					preparedStmt7.setInt(6, user.getIdrestaurant());
					preparedStmt7.execute();
				} catch (Exception e) {
					System.out.println("Worker Imported");
				}
				break;
			case "null":
				String query8 = " insert into assignment3.visa (userID,Number,CVV,year,CardHolderName,Month,confirm)"
						+ " values (?, ?, ?, ?, ?,?,?)";
				PreparedStatement preparedStmt8 = conn.prepareStatement(query8);
				preparedStmt8.setString(1, uservisa.getUserID());
				preparedStmt8.setString(2,uservisa.getNumber() );
				preparedStmt8.setInt(3, uservisa.getCVV());
				preparedStmt8.setInt(4, uservisa.getYear());
				preparedStmt8.setString(5, uservisa.getCardHolderName());
				preparedStmt8.setInt(6,uservisa.getMonth() );
				preparedStmt8.setInt(7, 0);
				preparedStmt8.execute();
				break;
			}} catch (Exception e) {
				System.out.println("importing data ...");
			}
			}
		}
	

	/**
	 * This method to remove all Data that we brought it from Bite me to assignment3
	 * all business and normal user and companies ,restaurant not confirmed else
	 * remove all data
	 * 
	 * @throws SQLException
	 */
	public static void deletealldata() throws SQLException {
		Statement ps = conn.createStatement();
		PreparedStatement st, st1, st2 = null, st3 = null;
		ResultSet rs = ps.executeQuery("SELECT * FROM assignment3.users where confirm=0");
		while (rs.next() == true) {
			String ID = rs.getString(1);
			String type = rs.getString(4);

			if (type.equals("Normal")) {
				st = conn.prepareStatement("DELETE FROM assignment3.normaluser  where ID='" + ID + "'");
				st.executeUpdate();
			} else if (type.equals("Bussiness")) {
				st = conn.prepareStatement("DELETE FROM assignment3.bussinessuser  where ID='" + ID + "'");
				st.executeUpdate();
			} else if (type.equals("BranchManager")) {
				st = conn.prepareStatement("DELETE FROM assignment3.branchmanager");
				st.executeUpdate();
			} else if (type.equals("CEO")) {
				st = conn.prepareStatement("DELETE FROM assignment3.ceouser");
				st.executeUpdate();
			} else if (type.equals("RestaurantManager")) {
				st = conn.prepareStatement("DELETE FROM assignment3.restaurantmanager");
				st.executeUpdate();
			} else if (type.equals("HR")) {
				st = conn.prepareStatement("DELETE FROM assignment3.hruser");
				st.executeUpdate();
			} else if (type.equals("Worker")) {
				st = conn.prepareStatement("DELETE FROM assignment3.restaurantworker");
				st.executeUpdate();
			} else if (type.equals("Company")) {
				st = conn.prepareStatement("DELETE FROM assignment3.company WHERE confirm = ?");
				st.setInt(1, 0);
				st.executeUpdate();
			} else if (type.equals("Restaurant")) {
				st = conn.prepareStatement("DELETE FROM assignment3.restaurant WHERE confirm = ?");
				st.setInt(1, 0);
				st.executeUpdate();
			}
			st = conn.prepareStatement("DELETE FROM assignment3.visa  where confirm=0");
			st.executeUpdate();
		}
		st = conn.prepareStatement("DELETE FROM assignment3.users WHERE confirm = ?");
		st.setInt(1, 0);
		st.executeUpdate();
	}
	
	/**
	 * Getting the visa where the userID= id
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static Visa getvisa(String id) throws SQLException
	{
		Visa visauser = null;
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT * From assignment3.visa where userID= '"+id+"'");
		if(RS.next())
		{
			visauser=new Visa(RS.getString(1),RS.getString(2),Integer.parseInt(RS.getString(3)),Integer.parseInt(RS.getString(4)),RS.getString(5),Integer.parseInt(RS.getString(6)));
		}
		return visauser;
	}

	/**
	 * This method update the is logged in to all users IsLoggedIn =0 when exiting
	 * the server
	 * 
	 * @throws SQLException
	 */
	public static void LogOutAllAccounts() throws SQLException {
		PreparedStatement ps;

		ps = conn.prepareStatement("UPDATE assignment3.users SET IsLoggedIn= ? ");
		ps.setInt(1, 0);
		ps.executeUpdate();
	}

	/**
	 * This method to add the users to table after branch manager confirmed it and
	 * choose the type add w4c and information that we get from Bite me user table
	 * to assignment3
	 * 
	 * @param user     user that branch choose it
	 * @param msgData1 w4c of user
	 * @param table    that we need to add
	 * @return string (if exists or locked)
	 * @throws SQLException
	 */
	public static String AddNewUser(User user, Object msgData1, String table) throws SQLException {
		PreparedStatement st;
		try {
			Statement stmt = conn.createStatement();

			ResultSet rs1 = stmt
					.executeQuery("SELECT * FROM assignment3.bussinessuser where ID='" + user.getID() + "'");
			if (rs1.next() == true) {
				return "IDAlreadyExists";
			}
			ResultSet rs3 = stmt.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + user.getID() + "'");
			if (rs3.next() == true) {
				return "IDAlreadyExists";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (table.equals("normaluser")) {
			W4CNormal w4c = (W4CNormal) msgData1;
			String code = String.valueOf(w4c.getCode());
			String query1 = " insert into assignment3.normaluser (ID,FirstName,LastName,Email,Phone,VisaIsAvailable)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, user.getID());
			preparedStmt1.setString(2, user.getFirstname());
			preparedStmt1.setString(3, user.getLastname());
			preparedStmt1.setString(4, user.getEmail());
			preparedStmt1.setString(5, user.getPhonenumber());
			preparedStmt1.setInt(6, user.getVisaavailable());
			preparedStmt1.execute();

			String query6 = " insert into assignment3.w4cnormal (code,IDuser)" + " values (?, ?)";
			PreparedStatement preparedStmt6 = conn.prepareStatement(query6);
			preparedStmt6.setString(1, code);
			preparedStmt6.setString(2, w4c.getUser().getID());
			preparedStmt6.execute();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? , UserType=? WHERE ID = ?");
			ps.setInt(1, 1);
			ps.setString(2, "Active");
			ps.setString(3, user.getUserType());
			ps.setString(4, user.getID());
			ps.executeUpdate();
		} else {
			W4CBussiness w4c = (W4CBussiness) msgData1;
			String code = String.valueOf(w4c.getCode());
			String query2 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,Email,PhoneNumber,Company)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, user.getID());
			preparedStmt2.setString(2, user.getFirstname());
			preparedStmt2.setString(3, user.getLastname());
			preparedStmt2.setString(4, user.getEmail());
			preparedStmt2.setString(5, user.getPhonenumber());
			preparedStmt2.setString(6, user.getCompany());
			preparedStmt2.execute();

			String query4 = " insert into assignment3.w4cbussiness (code,money,IDuser,Type,Value)"
					+ " values (?, ?,?,?, ?)";
			PreparedStatement preparedStmt4 = conn.prepareStatement(query4);
			preparedStmt4.setString(1, code);
			preparedStmt4.setDouble(2, w4c.getMoney());
			preparedStmt4.setString(3, w4c.getUser().getID());
			preparedStmt4.setString(4, w4c.getType());
			preparedStmt4.setDouble(5, w4c.getValue());
			preparedStmt4.execute();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? , UserType=? WHERE ID = ?");
			ps.setInt(1, 1);
			ps.setString(2, "Frozen");
			ps.setString(3, user.getUserType());
			ps.setString(4, user.getID());
			ps.executeUpdate();
		}

		return "updated";
	}

	/**
	 * branch manager put visa information and add it to user id in visa table in
	 * assignment3 and in this method calling AddNewUser method to insert the user
	 * 
	 * @param user     user that we want to add visa
	 * @param msgData1 w4c
	 * @param visa     visa info
	 * @param table    table that we need to add
	 * @return return error text
	 * @throws SQLException
	 */
	public static String AddNewUserwithvisa(User user, Object msgData1, Visa visa, String table) throws SQLException {
		String str = AddNewUser(user, msgData1, table);
	
		PreparedStatement ps,ps1;
		ps1 = conn.prepareStatement("UPDATE assignment3.visa SET confirm= ? WHERE userID = ?");
		ps1.setInt(1, 1);
		ps1.setString(2, user.getID());
		ps1.executeUpdate();

		ps = conn.prepareStatement("UPDATE assignment3.normaluser SET VisaIsAvailable= ? WHERE ID = ?");
		ps.setInt(1, 1);
		ps.setString(2, user.getID());
		ps.executeUpdate();

		return str;
	}

	public static void confirmCompane(String cname) throws SQLException {
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.company where companyname='" + cname + "'");
		if (rs.next() != true) {
			String query1 = " insert into assignment3.company (companyname,confirm)" + " values (?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, cname);
			preparedStmt1.setInt(2, 0);
			preparedStmt1.execute();
		}
	}

	public static void companyConfirm(String cname) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.company SET confirm= ? WHERE companyname = ?");
		ps.setInt(1, 1);
		ps.setString(2, cname);
		ps.executeUpdate();
	}

	public static ArrayList<Company> getCompanyList() {
		Statement stmt;
		CompanyList = new ArrayList<Company>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.company where confirm='" + 0 + "'");
			while (rs.next() == true) {
				Company company = new Company(rs.getString(1), rs.getInt(2));
				CompanyList.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CompanyList;
	}

	/**
	 * this method add to arraylist all the user that not confirmed yet and type
	 * =null in branch manager to add it
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<User> TakeAllUserThatNotConfiredyet() throws SQLException {
		TakeAllUserThatNotConfiredyet = new ArrayList<>();
		Visa visa;
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT * From assignment3.users where UserType= 'null'");
		User users;
		while (RS.next()) {
			String id = RS.getString(1);
		visa=	getvisa(id);
			for (int i = 0; i < userfrombitemedata.size(); i++) {
				if (id.equals(userfrombitemedata.get(i).getID())) {
					 userfrombitemedata.get(i).setVisa(visa);
					TakeAllUserThatNotConfiredyet.add(userfrombitemedata.get(i));
					
				}
			}
		}
		return TakeAllUserThatNotConfiredyet;
	}

	/**
	 * this method get all companies that confirmed
	 * 
	 * @return array list of AvailableCompany
	 * @throws SQLException
	 */
	public static ArrayList<Company> GetallAvailableCompany() throws SQLException {
		companys = new ArrayList<Company>();
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT* From assignment3.company where confirm=1");
		Company company;
		while (RS.next()) {
			company = new Company(RS.getString(1), Integer.parseInt(RS.getString(2)));
			companys.add(company);
		}
		return companys;

	}

	/**
	 * this method to get all the orders of restaurant id
	 * 
	 * @param id restaurant id
	 * @return return array list of all orders in specific restaurant
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static ArrayList<Order> GetAllOrder(int id) throws NumberFormatException, SQLException {
		AllOrder = new ArrayList<Order>();
		Statement ps = conn.createStatement();
		Statement pk = conn.createStatement();
		Statement pe = conn.createStatement();

		ResultSet RS = ps.executeQuery("SELECT* From assignment3.order where RestaurantID='" + id + "'");
		Order order;
		while (RS.next()) {
			String iduser = RS.getString(3);
			ResultSet Rk = pk.executeQuery("SELECT* From assignment3.normaluser where ID='" + iduser + "'");
			if (Rk.next()) {
				String username = Rk.getString(2);
				order = new Order(Integer.parseInt(RS.getString(1)), Double.parseDouble(RS.getString(5)),
						RS.getString(4), RS.getString(6), username);
				order.setId(iduser);
				if (RS.getString(8) == null)
					order.setStatus("");
				else
					order.setStatus(RS.getString(8));
				AllOrder.add(order);
			} else {
				ResultSet Re = pe.executeQuery("SELECT* From assignment3.bussinessuser where ID='" + iduser + "'");
				if (Re.next()) {
					String username = Re.getString(2);
					order = new Order(Integer.parseInt(RS.getString(1)), Double.parseDouble(RS.getString(5)),
							RS.getString(4), RS.getString(6), username);
					order.setId(iduser);

					if (RS.getString(8) == null)
						order.setStatus("");
					else
						order.setStatus(RS.getString(8));
					AllOrder.add(order);
				}
			}
		}
		return AllOrder;
	}

	/**
	 * thois method to update the item that restaurantmanager choose it to update
	 * 
	 * @param item item that we want to update
	 * @param str
	 * @throws SQLException
	 */
	public static void UpdateItem(Item item, String str) throws SQLException {
		PreparedStatement ps, ps1;
		ps1 = conn.prepareStatement("UPDATE assignment3.menu SET quantity= ? WHERE Item_ID = ?");
		ps1.setInt(1, item.getQuantity());
		ps1.setInt(2, item.getItem_ID());
		ps1.executeUpdate();
		ps = conn.prepareStatement("UPDATE assignment3.item SET Item_name= ?, Item_price=? WHERE Item_ID = ?");
		ps.setString(1, item.getItem_Name());
		ps.setDouble(2, item.getPrice());
		ps.setInt(3, item.getItem_ID());
		ps.executeUpdate();

	}

	/**
	 * ' this method to remove item from restaurant
	 * 
	 * @param item item taht we want to remove
	 * @throws SQLException
	 */
	public static void RemoveItem(Item item) throws SQLException {
		PreparedStatement st, st1;
		st1 = conn.prepareStatement("DELETE FROM assignment3.menu WHERE Item_ID = ?");
		st1.setInt(1, item.getItem_ID());
		st1.executeUpdate();
		st = conn.prepareStatement("DELETE FROM assignment3.item WHERE Item_ID = ?");
		st.setInt(1, item.getItem_ID());
		st.executeUpdate();

	}

	/**
	 * this method to remove addition of specific item
	 * 
	 * @param item     that me want to remove addition
	 * @param addition we want to remove
	 * @throws SQLException
	 */
	public static void RemoveItemAddition(Item item, String addition) throws SQLException {
		PreparedStatement st, st1;
		st = conn.prepareStatement("DELETE FROM assignment3.item_addition WHERE name=? and Item_ID = ?");
		st.setString(1, addition);
		st.setInt(2, item.getItem_ID());
		st.executeUpdate();

		st1 = conn.prepareStatement("DELETE FROM assignment3.addition WHERE name = ?");
		st1.setString(1, addition);
		st1.executeUpdate();

	}

	/**
	 * this method add item and addition to specific restaurant that
	 * restaurantmanager choose
	 * 
	 * 
	 * @param item     that restaurantmanager want to add
	 * @param addition that restaurantmanager want to add
	 * @param resid    restaurant id
	 * @param str      category
	 * @throws SQLException
	 */
	public static void AddItems(Item item, Addition addition, int resid, String str) throws SQLException {
		if (addition == null) {
			String query = " insert into assignment3.item (item_ID,Item_name,Item_price,category)"
					+ " values (?, ?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, item.getItem_ID());
			preparedStmt.setString(2, item.getItem_Name());
			preparedStmt.setDouble(3, item.getPrice());
			preparedStmt.setString(4, str);
			preparedStmt.execute();
			String query1 = " insert into assignment3.menu (IDRestaurant,item_ID,quantity)" + " values (?, ?,?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setInt(1, resid);
			preparedStmt1.setInt(2, item.getItem_ID());
			preparedStmt1.setInt(3, item.getQuantity());
			preparedStmt1.execute();
		} else {
			String query = " insert into assignment3.addition (name)" + " values (?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, addition.getName());
			preparedStmt.execute();
			String query2 = " insert into assignment3.item_addition (item_ID,name)" + " values (?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setInt(1, item.getItem_ID());
			preparedStmt2.setString(2, addition.getName());
			preparedStmt2.execute();

		}
	}

	/**
	 * @param id
	 * @return get worker for restaurant
	 * @throws Exception
	 */
	public static ArrayList<WorkerUser> GetAllWorkers(int id) throws Exception {
		ArrayList<WorkerUser> allworker = new ArrayList<WorkerUser>();
		Statement ps = conn.createStatement();
		Statement ps1 = conn.createStatement();
		WorkerUser WorkerUser;
		ResultSet RS = ps
				.executeQuery("SELECT * From assignment3.restaurantworker where restaurantWorker='" + id + "'");
		while (RS.next()) {
			String idres = RS.getString(1);
			ResultSet RS1 = ps1.executeQuery("SELECT * From assignment3.users where ID='" + idres + "'");
			if (RS1.next()) {
				WorkerUser = new WorkerUser(RS1.getString(1), RS.getString(2), RS.getString(3), RS1.getString(2),
						RS1.getString(3), RS.getString(4), RS.getString(5), id);
				WorkerUser.setRestaurantName(getrestaurantname(id).getResturaunt_Name());
				allworker.add(WorkerUser);

			}
		}
		return allworker;
	}

	/**
	 * this method to update status of user
	 * 
	 * @param user1
	 * @throws SQLException
	 */
	public static void UpdateStatusOfUsers(User user1) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET status= ? WHERE ID = ?");
		ps.setString(1, user1.getStatus());
		ps.setString(2, user1.getID());
		ps.executeUpdate();
	}

	/**
	 * this method adding w4c and confirm the status to active and confirm it
	 * 
	 * @param user         that hr confirmed it changing his status to active
	 * @param W4CBussiness hr add w4c
	 * @throws SQLException
	 */
	public static void BussinessAccountHasBeenAccepted(User user, W4CBussiness W4CBussiness) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? WHERE ID = ?");
		ps.setString(1, "1");
		ps.setString(2, "Active");
		ps.setString(3, user.getID());
		ps.executeUpdate();
		PreparedStatement pz;
		pz = conn.prepareStatement("UPDATE assignment3.w4cbussiness SET money= ? , Type= ? ,Value=?  WHERE IDuser = ?");
		pz.setDouble(1, W4CBussiness.getValue());
		pz.setString(2, W4CBussiness.getType());
		pz.setDouble(3, W4CBussiness.getValue());
		pz.setString(4, user.getID());
		pz.executeUpdate();
	}

	/**
	 * @return w4c code to be created randomly when generating new account
	 */
	static int IDForW4C() {
		Random rand = new Random();
		Statement stmt, stmt1;
		try {
			while (true) {
				int W4C = rand.nextInt((999 - 100) + 1) + 100;
				stmt = conn.createStatement();
				stmt1 = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.w4cnormal where code= '" + W4C + "'");
				if (!rs.next()) {
					ResultSet rs1 = stmt
							.executeQuery("SELECT * FROM assignment3.w4cbussiness where code= '" + W4C + "'");
					if (!rs1.next()) {
						System.out.println(W4C);
						return W4C;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 5;
	}

//////////////////////
	/**
	 * If user need to be refunded create new or update a row with userID and
	 * restaurant for future buying
	 * 
	 * @param RestaurantNumber
	 * @param refund
	 * @param userID
	 * @throws SQLException
	 */
	public static void RefundCustomer(int RestaurantNumber, double refund, String userID) throws SQLException {
		Statement stmt;
		PreparedStatement rs2;
		stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.refund where UserID = '" + userID
				+ "' and RestaurantID = " + RestaurantNumber);
		if (rs.next() == true) {
			double newprice = rs.getDouble(3);
			newprice = +refund;
			rs2 = conn.prepareStatement("UPDATE assignment3.refund SET Value= ? WHERE UserID = ? and RestaurantID= ? ");
			rs2.setDouble(1, newprice);
			rs2.setString(2, userID);
			rs2.setInt(3, RestaurantNumber);
			rs2.executeUpdate();
		} else {
			String query1 = " insert into assignment3.refund (UserID,RestaurantID,Value) values (?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, userID);
			preparedStmt1.setInt(2, RestaurantNumber);
			preparedStmt1.setDouble(3, refund);
			preparedStmt1.execute();
		}
		///////////

	}

	/**
	 * This method check if user exists in DB or not and also if he loggedin and
	 * check the status
	 * 
	 * @param username that we inside to login
	 * @return return string to server to know in client if user is exists or not
	 *         and status
	 * @throws SQLException
	 */
	static String LogInChecker(User username) throws SQLException {
		Statement stmt, stmt1;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.users where UserName='" + username.getUserName()
				+ "' and Password='" + username.getPassword() + "'");
		if (rs.next() == true) {
			if (rs.getInt(5) == 1)
				return "LoggedIn";

			if (rs.getString(7).equals("Locked")) {
				return "Locked";
			}
			LoginUser = new User(rs.getString(1), username.getUserName(), rs.getString(3), rs.getString(4),
					Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
			return "Entered";
		}
		return "NotExist";
	}

	/**
	 * check if the password and the user name is correct , if yes then send the
	 * right message
	 * 
	 * @param username
	 * @return User
	 */
	static User selectuserfromNormalUserTable(User username) {

		Statement stmt;
		int status;
		boolean valid = false;
		String usernameStatus;
		String Status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + username.getID() + "'");
			if (rs.next() == true) {
				Status = rs.getString(10);
				user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));

				usernameStatus = username.getUserName();
				status = rs.getInt("IsLoggedIn");
			}
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Accepts new Normal account
	 * 
	 * @param username
	 * @throws SQLException
	 */
	public static void AcceptNewNormalUser(User username) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? WHERE ID = ?");
		ps.setInt(1, 1);
		ps.setString(2, "Active");
		ps.setString(3, username.getID());
		ps.executeUpdate();
	}

	/**
	 * Update Client status in the database column IsLoggedIn = 1 if user enters the
	 * application column IsLoggedIn = 0 if user exits the application used for the
	 * functionality of the application so that users can't enter more than once
	 * 
	 * @param user
	 * @param status
	 */
	public static void updateClientStatus(User user, int status) {
		PreparedStatement ps, rs;
		try {
			ps = conn.prepareStatement("UPDATE assignment3.users SET IsLoggedIn= ? WHERE UserName = ?");
			ps.setInt(1, status);
			ps.setString(2, user.getUserName());

			if (ps.executeUpdate() != 0) {
				System.out.println("update " + user.getUserName() + " status to:" + status);

			} else {
				rs = conn.prepareStatement("UPDATE bitemeuser SET IsLoggedIn= ? WHERE UserName = ?");
				rs.setInt(1, status);
				rs.setString(2, user.getUserName());
				rs.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes users or restaurants from given tables
	 * 
	 * @param id
	 * @param sqltable
	 * @throws SQLException
	 */
	public static void deleteId(String id, String sqltable) throws SQLException {
		PreparedStatement st, st1, st2 = null, st3 = null;
		if (sqltable.equals("normaluser")) {
			st = conn.prepareStatement("DELETE FROM assignment3.normaluser WHERE ID = ?");
			st.setString(1, id);
			st.executeUpdate();
			st1 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
			st1.setString(1, id);
			st1.executeUpdate();
		}
		if (sqltable.equals("restaurant")) {
			st = conn.prepareStatement("DELETE FROM assignment3.restaurant WHERE IDRestaurant = ?");
			st.setInt(1, Integer.parseInt(id));
			st.executeUpdate();
		} else
			st3 = conn.prepareStatement("DELETE FROM assignment3.bussinessuser WHERE ID = ?");
		st3.setString(1, id);
		st3.executeUpdate();
		st2 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
		st2.setString(1, id);
		st2.executeUpdate();
	}

	/**
	 * This method to get all bussiness users there status is frozen and below to
	 * company name to show in table view in hr home page
	 * 
	 * @param company company name
	 * @return boolean
	 */
	static boolean getTheRequestList(String company) {
		requestsList = new ArrayList<BussinessUser>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps
					.executeQuery("SELECT * From assignment3.users where UserType='Bussiness' and status='Frozen'");
			BussinessUser request;
			while (RS.next()) {
				String id = RS.getString(1);
				ResultSet RS1 = ps1.executeQuery(
						"SELECT * From assignment3.bussinessuser where ID='" + id + "'and Company='" + company + "'");
				if (RS1.next()) {
					request = new BussinessUser(RS1.getString(1), RS1.getString(2), RS1.getString(3), RS1.getString(4),
							RS1.getString(5), 0, RS1.getString(6), 0);
					requestsList.add(request);
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * this method put all normal user that confirmed in array list to show them in
	 * table view
	 * 
	 * @return boolean if exists
	 */
	static boolean getAllUsers() {
		getlistofnormalaccount = new ArrayList<User>();
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * from assignment3.users where UserType= 'Normal' and confirm = 1");
			User request;
			while (rs.next()) {
				request = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
				getlistofnormalaccount.add(request);
				System.out.print(getlistofnormalaccount);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * get all data from bite me DB table and put all request in array list
	 */
	static void putdatafrombitemeDB() {
		usersfromBiteMeDB = new ArrayList<User>();
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * from biteme_data.bitemeuser");
			User request;
			while (rs.next()) {
				request = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
				usersfromBiteMeDB.add(request);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method Received location of branch manager and get all restaurant
	 * according to location from DB
	 */
	static void getAllResturaunt(String location) {
		getAllresturaunt = new ArrayList<Resturaunt>();
		try {
			Statement ps = conn.createStatement();
			ResultSet RS = ps.executeQuery(
					"SELECT * from assignment3.restaurant where location='" + location + "' and confirm=0");

			Resturaunt request;
			while (RS.next()) {
				request = new Resturaunt(Integer.parseInt(RS.getString(1)), RS.getString(2), RS.getString(3));
				getAllresturaunt.add(request);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method get all restaurant from DB
	 */
	static void getAllResturaunt() {
		getAllresturaunt = new ArrayList<Resturaunt>();
		try {
			Statement ps = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * from assignment3.restaurant");

			Resturaunt request;
			while (RS.next()) {
				request = new Resturaunt(Integer.parseInt(RS.getString(1)), RS.getString(2), RS.getString(3));
				getAllresturaunt.add(request);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given restaurantID, month and year give back report to restaurant
	 * 
	 * @param restaurantID
	 * @param month
	 * @param year
	 * @return List of restaurant reports
	 * @throws SQLException
	 */
	public static ArrayList<RestaurantReport> GetReportForManager(int restaurantID, int month, int year)
			throws SQLException {
		ReportListForManager = new ArrayList<RestaurantReport>();
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;
		int month2 = month + 2;
		int ItemID;
		int sold;
		int quantity1 = 0;
		RestaurantReport rsp;
		try {
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmt4 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='" + restaurantID
					+ "' and Month>='" + month + "' and Month <='" + month2 + "' and Year='" + year + "'");
			while (rs2.next() == true) {
				ItemID = Integer.parseInt(rs2.getString(2));
				sold = Integer.parseInt(rs2.getString(3));
				ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + ItemID + "'");

				ResultSet rs4 = stmt4.executeQuery("SELECT * FROM assignment3.menu where IDRestaurant='" + restaurantID
						+ "' and Item_ID= '" + ItemID + "'");
				if (rs4.next() == true) {
					quantity1 = Integer.parseInt(rs4.getString(3));
				}
				while (rs3.next() == true) {
					rsp = new RestaurantReport(ItemID, rs3.getString(2), quantity1, Float.parseFloat(rs3.getString(3)),
							sold, sold * Float.parseFloat(rs3.getString(3)));
					ReportListForManager.add(rsp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ReportListForManager;
	}

	/**
	 * this method received business user and return w4c
	 * 
	 * @param user type business
	 * @return
	 */
	public static W4CBussiness getW4CBussiness(User user) {
		W4CBussiness w4c = null;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.w4cbussiness where IDuser=" + user.getID());
			if (rs.next()) {
				w4c = new W4CBussiness(rs.getInt(1), user, rs.getDouble(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return w4c;
	}

	/**
	 * this method received user type restaurant manager information id and getting
	 * all information (restaurant name , firstname lastname ...)
	 * 
	 * @param user that we take it from users table
	 * @return RestaurantManager
	 */
	static RestaurantManager GetRestaurantManager(User user) {
		Statement stmt, stmt1;
		int status;
		try {
			stmt1 = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.restaurantmanager where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.restaurant where IDRestaurant='"
						+ Integer.parseInt(rs.getString(6)) + "'and confirm=1");
				if (rs1.next() == true) {
					restaurantManager = new RestaurantManager(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)));

					return restaurantManager;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * this method received order number and delete all order from different tables
	 * in DB
	 * 
	 * @param orderid order number
	 */
	public static void deleteOrder(int orderid) {
		Statement stmt;
		try {
			String SQL = "delete from assignment3.order where orderNum=?";
			PreparedStatement pstmt = null;

			// get a connection and then in your try catch for executing your delete...

			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
			SQL = "delete from assignment3.order_items where orderNum=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
			SQL = "delete from assignment3.order_item_addition where orderNum=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
			SQL = "delete from assignment3.processingorders where orderNum=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param orderid
	 * @return true if delivery exists else false
	 */
	public static boolean CheckDelivery(int orderid) {
		String sql = "select COUNT(*) from assignment3.delivery where orderNum=" + orderid;
		Statement st = null;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next() == true) {
				String SQL = "delete from assignment3.delivery where orderNum=?";
				PreparedStatement pstmt = null;
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, orderid);
				pstmt.executeUpdate();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param orderid
	 * @return date of delivery for given order
	 */
	public static String GetDeliveryDate(int orderid) {
		Statement stmt;
		String date = "";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from assignment3.order where orderNum=" + orderid);
			if (rs.next())
				date = rs.getString(6);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * this method received user (worker ) and return all information of worker from
	 * assignment3 restaurant worker table
	 * 
	 * @param user worker information from users table
	 * @return worker for restaurant
	 */
	static WorkerUser GetRestaurantWorker(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.restaurantworker where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				WorkerUser = new WorkerUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), Integer.parseInt(rs.getString(6)));
				return WorkerUser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * this method return the name of item Received item id and return string of
	 * item name
	 * 
	 * @param itemid
	 * @return item name
	 * @throws SQLException
	 */
	private static String getitemname(int itemid) throws SQLException {
		Statement stmt, stmt1;
		stmt1 = conn.createStatement();
		String str = null;
		ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + itemid + "'");
		if (rs1.next() == true) {
			str = rs1.getString(2);
		}
		return str;
	}

//////////////////////////////////////
	public static String GetTypeOfOrder(int orderid) {
		String sql = "select * from assignment3.delivery where orderNum=" + orderid;
		Statement st = null;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				return "Delivery";
			return "Pick Up";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Pick Up";
	}

	/**
	 * update order status accordingly
	 * 
	 * @param orderid
	 * @param status
	 */
	public static void updateOrderStatus(int orderid, String status) {

		PreparedStatement ps;
		String stat = "";
		try {
			String sql = "select * from assignment3.processingorders where orderNum=" + orderid;
			Statement st = null;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				stat = rs.getString(6);

			if (stat.equals("Late")) {
				stat = stat + " (" + status + ")";
				ps = conn.prepareStatement("UPDATE assignment3.processingorders SET Status= ? WHERE orderNum = ?");
				ps.setString(1, stat);
				ps.setInt(2, orderid);
				ps.executeUpdate();
			} else {
				ps = conn.prepareStatement("UPDATE assignment3.processingorders SET Status= ? WHERE orderNum = ?");
				ps.setString(1, status);
				ps.setInt(2, orderid);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method received order number and return array list item addition that
	 * include items delivery and all information
	 * 
	 * @param ordernum specific order
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<ItemAddition> GetallOrederItems(int ordernum) throws SQLException {
		Statement stmt, stmt1;

		stmt = conn.createStatement();
		stmt1 = conn.createStatement();
		ArrayList<ItemAddition> itemsandAddition1 = new ArrayList<>();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM assignment3.order_item_addition where orderNum='" + ordernum + "'");
		while (rs.next()) {
			int itemid = rs.getInt(2);
			String str = rs.getString(3);
			ItemAddition a = new ItemAddition(getitemname(itemid), str, GetTypeOfOrder(ordernum),
					GetDelivery(ordernum));
			itemsandAddition1.add(a);
		}
		return itemsandAddition1;
	}

	/**
	 * @param orderid
	 * @return delivery object of given order number
	 */
	public static Delivery GetDelivery(int orderid) {
		String sql = "select * from assignment3.delivery where orderNum=" + orderid;
		Statement st = null;
		Delivery Delivery = null;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				Delivery = new Delivery(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
				return Delivery;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Delivery;
	}

	/**
	 * this method received id of user and get the email
	 * 
	 * @param id of user
	 * @return string use email
	 * @throws SQLException
	 */
	public static String getuser(String id) throws SQLException {
		Statement stmt, stmt1, stmt2;
		String mail = "";
		stmt = conn.createStatement();
		stmt1 = conn.createStatement();
		stmt2 = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.users where ID='" + id + "'");
		if (rs.next() == true) {
			String type = rs.getString(4);

			switch (type) {
			case "Normal":
				ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + id + "'");
				if (rs1.next() == true) {
					mail = rs1.getString(4);
					return mail;
				}
				break;
			case "Bussiness":
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.bussinessuser where ID='" + id + "'");
				if (rs2.next() == true) {
					mail = rs2.getString(4);
					return mail;
				}
				break;
			}
		}
		return mail;
	}

	/**
	 * this method to get BranchManager information when given user id
	 * 
	 * @param user
	 * @return BranchManager
	 */
	static BranchManager GetBranchManager(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.branchmanager where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				branchManager = new BranchManager(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				return branchManager;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	/**
	 * this method to get CEOuser information when given user id
	 * 
	 * @param user
	 * @return CEOuser
	 */
	static CEOuser CeoUser(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.ceouser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				CEOuser1 = new CEOuser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				return CEOuser1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	/**
	 * this method to get business information when given user id
	 * 
	 * @param user
	 * @return bussiness user
	 */
	static BussinessUser GetBissnessUser(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.bussinessuser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				W4CBussiness w4c = getW4CBussiness(user);
				BussinessUser = new BussinessUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), w4c);
				return BussinessUser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding BissessUser!!!");
		return null;
	}

	/**
	 * getting restaurant information
	 * 
	 * @param id restaurant id
	 * @return restaurant information
	 * @throws SQLException
	 */
	static Resturaunt getrestaurantname(int id) throws SQLException {
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT *  FROM assignment3.restaurant where IDRestaurant= " + id);
		if (rs.next() == true) {
			Resturaunt = new Resturaunt(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3));
			return Resturaunt;
		}
		return null;

	}

	/**
	 * this method send specific userid to DB and get hr user information
	 * 
	 * @param user
	 * @return hr user
	 */
	static HRUser GetHRManager(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * From assignment3.hruser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				HRManager = new HRUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6));
				return HRManager;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	/**
	 * this method to get normal user from DB
	 * 
	 * @param user
	 * @return normal user
	 */
	public static Normal getNormalUser(User user) throws SQLException {
		Normal nuser = null;

		Statement st;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("Select * from assignment3.normaluser where ID=" + user.getID());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (rs.next()) {
			try {
				nuser = new Normal(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nuser;

	}

	/**
	 * this method to get normal user from DB
	 * 
	 * @param user
	 * @return normal user
	 */
	public static Normal Getnormaluser(User user) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * From assignment3.normaluser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				Normaluser = new Normal(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), Integer.parseInt(rs.getString(6)));
				return Normaluser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;
	}

	/**
	 * @param restaurantManager
	 * @param month
	 * @param year
	 * @return List of reports
	 * @throws SQLException
	 */
	public static ArrayList<RestaurantReport> GetReportForRestaurant(RestaurantManager restaurantManager, int month,
			int year) throws SQLException {
		ReportList = new ArrayList<RestaurantReport>();
		Statement stmt;
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;

		int IDRestaurant;
		int ItemID;
		int sold;
		int quantity1 = 0;
		RestaurantReport rsp;
		try {
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmt4 = conn.createStatement();

			System.out.println(restaurantManager.getUserName());

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM assignment3.restaurantmanager where ID='" + restaurantManager.getUserID() + "'");
			if (rs.next() == true) {
				IDRestaurant = Integer.parseInt(rs.getString(6));
				System.out.println(IDRestaurant);
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='"
						+ IDRestaurant + "' and Month='" + month + "' and Year='" + year + "'");
				while (rs2.next() == true) {
					ItemID = Integer.parseInt(rs2.getString(2));
					sold = Integer.parseInt(rs2.getString(3));
					ResultSet rs4 = stmt4.executeQuery("SELECT * FROM assignment3.menu where IDRestaurant='"
							+ IDRestaurant + "' and Item_ID= '" + ItemID + "'");
					if (rs4.next() == true) {
						quantity1 = Integer.parseInt(rs4.getString(3));
					}
					ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + ItemID + "'");
					while (rs3.next() == true) {
						rsp = new RestaurantReport(ItemID, rs3.getString(2), quantity1,
								Float.parseFloat(rs3.getString(3)), sold, sold * Float.parseFloat(rs3.getString(3)));
						ReportList.add(rsp);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ReportList;
	}

	/**
	 * add new worker to restaurant
	 * 
	 * @param WorkerUser
	 * @return if succeeded or not
	 * @throws SQLException
	 */
	public static String AddNewWorker(WorkerUser WorkerUser) throws SQLException {
		Statement stmt;

		stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM assignment3.users where UserName='" + WorkerUser.getUserName() + "'");
		if (rs.next() == true) {
			return "UserNameAlreadyExists";
		}
		ResultSet rs1 = stmt.executeQuery("SELECT * FROM assignment3.users where ID='" + WorkerUser.getUserID() + "'");
		if (rs1.next() == true) {
			return "IDAlreadyExists";
		}
		String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, WorkerUser.getUserID());
		preparedStmt.setString(2, WorkerUser.getUserName());
		preparedStmt.setString(3, WorkerUser.getPassword());
		preparedStmt.setString(4, "Worker");
		preparedStmt.setInt(5, 0);
		preparedStmt.setInt(6, 1);
		preparedStmt.setString(7, "Active");
		preparedStmt.execute();

		String query1 = " insert into assignment3.restaurantworker (ID,FirstName,LastName,Email,PhoneNumber,restaurantWorker)"
				+ " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		preparedStmt1.setString(1, WorkerUser.getUserID());
		preparedStmt1.setString(2, WorkerUser.getFirstName());
		preparedStmt1.setString(3, WorkerUser.getLastName());
		preparedStmt1.setString(4, WorkerUser.getEmail());
		preparedStmt1.setString(5, WorkerUser.getPhoneNumber());
		preparedStmt1.setInt(6, WorkerUser.getIDRestaurant());
		preparedStmt1.execute();
		return "Updated";

	}

	/**
	 * this method to remove worker info from DB
	 * 
	 * @param WorkerUser
	 * @throws SQLException
	 */
	public static void RemoveWorker(WorkerUser WorkerUser) throws SQLException {
		PreparedStatement st, st1;
		st = conn.prepareStatement("DELETE FROM assignment3.restaurantworker  WHERE ID = ?");
		st.setString(1, WorkerUser.getUserID());
		st.executeUpdate();
		st1 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
		st1.setString(1, WorkerUser.getUserID());
		st1.executeUpdate();
	}

	/**
	 * this method to update the worker that we get from client
	 * 
	 * @param WorkerUser
	 * @throws SQLException
	 */
	public static void EditWorker(WorkerUser WorkerUser) throws SQLException {
		PreparedStatement ps, rs;

		ps = conn.prepareStatement(
				"UPDATE assignment3.restaurantworker SET FirstName= ?,LastName=?,Email=?,PhoneNumber=?  WHERE ID = ?");
		ps.setString(1, WorkerUser.getFirstName());
		ps.setString(2, WorkerUser.getLastName());
		ps.setString(3, WorkerUser.getEmail());
		ps.setString(4, WorkerUser.getPhoneNumber());
		ps.setString(5, WorkerUser.getUserID());
		ps.executeUpdate();
		rs = conn.prepareStatement("UPDATE assignment3.users SET UserName= ?,Password=? WHERE ID = ?");
		rs.setString(1, WorkerUser.getUserName());
		rs.setString(2, WorkerUser.getPassword());
		rs.setString(3, WorkerUser.getUserID());
		rs.executeUpdate();

	}

	/**
	 * @param res restaurant information
	 * @return if This Id is Existes or updated
	 * @throws SQLException
	 */
	public static String Create_acceptRestaurant(Resturaunt res) throws SQLException {
		PreparedStatement ps;
		try {
			String query1 = ("UPDATE assignment3.restaurant SET confirm = ? ,Taxes=? WHERE IDRestaurant = ?");
			ps = conn.prepareStatement(query1);
			ps.setInt(1, 1);
			ps.setDouble(2, res.getTaxe());
			ps.setInt(3, res.getResturauntID());
			ps.execute();
		} catch (Exception e) {
			return "This Id is Existes";
		}
		return "updated";
	}

	/**
	 * @return true if can add user
	 */
	static boolean GetTheRequestNormalAccount() {
		NormalUsersNotAccepted = new ArrayList<Normal>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * FROM assignment3.users where confirm= 0 ");
			Normal request;
			while (RS.next()) {
				String ID = RS.getString(1);
				ResultSet RS1 = ps1.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + ID + "'");
				while (RS1.next()) {
					request = new Normal(RS1.getString(1), RS1.getString(2), RS1.getString(3), RS1.getString(4),
							RS1.getString(5), Integer.parseInt(RS1.getString(6)), Float.parseFloat(RS1.getString(7)));
					NormalUsersNotAccepted.add(request);
				}
			}
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * insert account with visa
	 * 
	 * @param visa
	 * @param user
	 * @param NorUser
	 * @throws SQLException
	 */
	public static void InsertNewNormalAccountWithVisa(Visa visa, User user, Normal NorUser) throws SQLException {
		InsertNewNormalAccountWithOutVisa(user, NorUser);
		String query1 = " insert into assignment3.visa (userID,Number,CVV,Year,CardHolderName,Month)"
				+ " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		preparedStmt1.setString(1, visa.getUserID());
		preparedStmt1.setString(2, visa.getNumber());
		preparedStmt1.setInt(3, visa.getCVV());
		preparedStmt1.setInt(4, visa.getYear());
		preparedStmt1.setString(5, visa.getCardHolderName());
		preparedStmt1.setInt(6, visa.getMonth());
		preparedStmt1.execute();

	}

	/**
	 * insert account without visa
	 * 
	 * @param user
	 * @param NorUser
	 * @return
	 * @throws SQLException
	 */
	public static String InsertNewNormalAccountWithOutVisa(User user, Normal NorUser) throws SQLException {
		Statement stmt;

		System.out.println("i am getting into login query");
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.users where UserName='" + user.getUserName() + "'");
			if (rs.next() == true) {
				return "UserNameAlreadyExists";
			}
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM assignment3.users where ID='" + user.getID() + "'");
			if (rs1.next() == true) {
				return "IDAlreadyExists";
			}
			String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getID());
			preparedStmt.setString(2, user.getUserName());
			preparedStmt.setString(3, user.getPassword());
			preparedStmt.setString(4, user.getUserType());
			preparedStmt.setInt(5, user.getIsLoggedIn());
			preparedStmt.setInt(6, user.getConfirm());
			preparedStmt.setString(7, user.getStatus());
			preparedStmt.execute();

			String query1 = " insert into assignment3.normaluser (ID,FirstName,LastName,Email,Phone,VisaIsAvailable)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, NorUser.getID());
			preparedStmt1.setString(2, NorUser.getFirstName());
			preparedStmt1.setString(3, NorUser.getLastName());
			preparedStmt1.setString(4, NorUser.getEmail());
			preparedStmt1.setString(5, NorUser.getPhoneNumber());
			preparedStmt1.setInt(6, NorUser.getVisaIsAvailable());
			preparedStmt1.execute();

			String query2 = " insert into assignment3.w4cnormal (code,IDuser)" + " values (?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, String.valueOf(NorUser.getW4C()));
			preparedStmt2.setString(2, user.getID());
			preparedStmt2.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Updated";
	}

	/**
	 * insert business account
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static String InsertNewBussinessAccount(BussinessUser user) throws SQLException {
		Statement stmt;
		System.out.println("i am getting into login query");
		try {
			String query = " insert into assignment3.bussinessuser (ID,FirstName,LastName,PhoneNumber,Email,Company)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getID());
			preparedStmt.setString(2, user.getFirstName());
			preparedStmt.setString(3, user.getLastName());
			preparedStmt.setString(4, user.getPhoneNumber());
			preparedStmt.setString(5, user.getEmail());
			preparedStmt.setString(6, user.getCompany());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Updated";
	}

	/**
	 * @param itemId item id
	 * @return array list of addition
	 */
	private static ArrayList<Addition> getAdditions(int itemId) {
		String sql = "select * from assignment3.item_addition where Item_ID=" + itemId;
		Statement st;
		ResultSet rs = null;
		ArrayList<Addition> list = new ArrayList<Addition>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Addition tmp = new Addition(rs.getString("name"));
				list.add(tmp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * get item for specific menu
	 * 
	 * @param itemId
	 * @return Item object
	 */
	private static Item getItems(int itemId) {
		String sql = "select * from assignment3.item where Item_ID=" + itemId;
		Statement st;
		ResultSet rs = null;
		Item item = null;
		ArrayList<Addition> list;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String cat = rs.getString("category");
				Category c = Category.valueOf(cat.toString());
				list = getAdditions(itemId);
				item = new Item(itemId, rs.getString("Item_name"), rs.getDouble("Item_price"), c, list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * get menu for each restaurant, calls getItems function to get items for menu
	 * 
	 * @param ResId
	 * @return menu object
	 */
	private static Menu getMenu(int ResId) {
		Menu m = null;
		Item item = null;
		Statement st = null;
		HashMap<Item, Integer> items2 = new HashMap<>();
		// ArrayList<Item> items = new ArrayList<>();
		ResultSet rs = null;
		String sql = "select * from assignment3.menu where IDRestaurant=" + ResId;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int itemId = rs.getInt("Item_ID");
				item = getItems(itemId);
				items2.put(item, rs.getInt("quantity"));
				// items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		m = new Menu(items2);
		return m;
	}

	/**
	 * return all restaurants from data base to display to the client
	 * 
	 * @return List of restaurants
	 */
	public static ArrayList<Resturaunt> getAllResturaunts() {
		ArrayList<Resturaunt> resList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "select * from assignment3.restaurant where confirm=1";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int ResId = rs.getInt("IDRestaurant");
				Menu m = getMenu(ResId);
				Resturaunt r = new Resturaunt(ResId, rs.getString(2), rs.getString(3), m);
				resList.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resList;
	}

	/**
	 * @param orderID
	 * @return status of order and date
	 * @throws SQLException
	 */
	private static String[] orderInProcess(int orderID) throws SQLException {

		String[] arr = new String[2];
		arr[0] = "";
		arr[1] = "";
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT * FROM assignment3.processingorders where orderNum='" + orderID + "'");
		if (RS.next() == true) {
			
			arr[1] = RS.getString(6);
			
		}
		
		ResultSet rs = ps.executeQuery("SELECT * FROM assignment3.order where orderNum='" + orderID + "'");
		if (rs.next() == true) {
			arr[0] = rs.getString(6);
			
			
		}
		
		
		
		
		return arr;
	}

	/**
	 * @param orderNum
	 * @return Yes if order is early order else no
	 */
	public static String getOrderEarlyLate(int orderNum) {
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * FROM assignment3.order where orderNum=" + orderNum);
			if (rs.next()) {
				return rs.getString(7);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param id
	 * @return List of all orders for user
	 * @throws ParseException
	 */
	public static ArrayList<OrderHistory> getUserOrders(String id) throws ParseException {
		ArrayList<OrderHistory> orderhistory = new ArrayList<OrderHistory>();
		OrderHistory OrderHistory;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date CurrentDate = sdformat.parse(EchoServer.sClock);
		Date WantedDate = null;
		
		try {
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT * FROM assignment3.order where userID='" + id + "'");
		while (RS.next()) {
			String StatusOfOrder=RS.getString(8);
			System.out.println(StatusOfOrder);
			String EarlyBooking = RS.getString(7);
			int RestaurantID=RS.getInt(2);
			int orderNum=RS.getInt(1);
			Double price = RS.getDouble(5);
			String Time=RS.getString(6);
			Resturaunt r=getrestaurantname(RestaurantID);
			Statement ps2 = conn.createStatement();
			ResultSet Rs = ps2.executeQuery("SELECT * FROM assignment3.processingorders where orderNum='" + orderNum + "'");
			String  SecondStatus ="";
			if(Rs.next()==true) {
				SecondStatus=Rs.getString(6);
			}
				WantedDate=formatter.parse(RS.getString(6));
				if (WantedDate.getTime()+ (1000 * 60 * 60) <= CurrentDate.getTime() && EarlyBooking.equals("No") 
						&&!SecondStatus.equals("Late (Ready)")	&&!SecondStatus.equals("Late (Delivery On it's way)")
						&&!SecondStatus.equals("Ready")&&!SecondStatus.equals("Delivery On it's way"))	
				{
					PreparedStatement js = conn.prepareStatement(
							"UPDATE assignment3.processingorders SET Status= ? WHERE IDuser = ? and orderNum=  ?");
					js.setString(1, "Late");
					js.setString(2, id);
					js.setInt(3, RS.getInt(1));
					js.executeUpdate();
					OrderHistory = new OrderHistory(RS.getInt(1), r.getResturaunt_Name(), RS.getDouble(5), RS.getString(6),
							"Late");
					orderhistory.add(OrderHistory);
				}
				else if (WantedDate.getTime() + (1000 * 60 * 20) <= CurrentDate.getTime() && EarlyBooking.equals("Yes")
						&&!SecondStatus.equals("Late (Ready)")	&&!SecondStatus.equals("Late (Delivery On it's way)")
						&&!SecondStatus.equals("Ready")&&!SecondStatus.equals("Delivery On it's way"))	
				{
					PreparedStatement js = conn.prepareStatement(
							"UPDATE assignment3.processingorders SET Status= ? WHERE IDuser = ? and orderNum=  ?");
					js.setString(1, "Late");
					js.setString(2, id);
					js.setInt(3, RS.getInt(1));
					js.executeUpdate();
					OrderHistory = new OrderHistory(RS.getInt(1), r.getResturaunt_Name(), RS.getDouble(5), RS.getString(6),
							"Late");
					orderhistory.add(OrderHistory);
				}else {
				
					Statement ps1 = conn.createStatement();
					ResultSet rs = ps1.executeQuery("SELECT * FROM assignment3.processingorders where orderNum='" + orderNum + "'");
					if(rs.next()==true)
					{
						if(rs.getString(6).equals("Late (Ready)"))
						{
							OrderHistory = new OrderHistory(orderNum, r.getResturaunt_Name(), price, Time,
									"Late (Ready)");
							orderhistory.add(OrderHistory);
						}
						else if(rs.getString(6).equals("Late (Delivery On it's way)"))
						{
							OrderHistory = new OrderHistory(orderNum, r.getResturaunt_Name(), price, Time,
									"Late (Delivery On it's way)");
							orderhistory.add(OrderHistory);
						}
						else if(rs.getString(6).equals("Ready")) {
						OrderHistory = new OrderHistory(orderNum, r.getResturaunt_Name(), price, Time,
								"Ready");
						orderhistory.add(OrderHistory);
						}
						else if(rs.getString(6).equals("Delivery On it's way")) {
							OrderHistory = new OrderHistory(orderNum, r.getResturaunt_Name(), price, Time,
									"Delivery On it's way");
							orderhistory.add(OrderHistory);
							}
						else {
							OrderHistory = new OrderHistory(orderNum, r.getResturaunt_Name(), price, Time,
									"Processing");
							orderhistory.add(OrderHistory);
						}
					}
					else {
					OrderHistory = new OrderHistory(orderNum, r.getResturaunt_Name(), price, Time,
							"Waiting To Accept");
					orderhistory.add(OrderHistory);
					}
				}
			
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderhistory;
	}

	/**
	 * getting if the company confirmed or not or companyExist
	 * 
	 * @param cname11 company name
	 * @return companyExists if company exists else null
	 */
	public static String companyChecker(String cname11) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM assignment3.company where confirm='" + 1 + "' and companyname='" + cname11 + "'");
			if (rs.next() == true) {
				return "companyExist";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getting the w4c of user
	 * 
	 * @param user normal user
	 * @return the w4cnormal
	 */
	public static W4CNormal getW4C(User user) {
		Statement stmt;
		W4CNormal w4c = null;
		String sql = null;
		if (user.getUserType().equals("Normal"))
			sql = "select * from assignment3.w4cnormal where IDuser=" + user.getID();
		else if (user.getUserType().equals("Bussiness"))
			sql = "select * from assignment3.w4cbussiness where IDuser=" + user.getID();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (user.getUserType().equals("Normal")) {
					w4c = new W4CNormal(rs.getInt(1), user);
					user.setW4c(w4c);
				} else if (user.getUserType().equals("Bussiness")) {
					w4c = new W4CBussiness(rs.getInt(1), user, rs.getInt(2));
					user.setW4c(w4c);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return w4c;
	}

	/**
	 * getting all items of restaurant
	 * 
	 * @param id   restaurant id
	 * @param type type of item
	 * @return array list of items
	 * @throws SQLException
	 */
	public static ArrayList<Item> getallmaindish(int id, String type) throws SQLException {
		Item Items = null;
		ArrayList<Addition> addition = new ArrayList<Addition>();
		Itemss = new ArrayList<Item>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("select * from assignment3.menu where IDRestaurant='" + id + "'");
			while (RS.next()) {
				int itemid = RS.getInt(2);
				ResultSet RS1 = ps1.executeQuery("SELECT * From assignment3.item where Item_ID='" + itemid + "'");
				if (RS1.next()) {
					String cat = RS1.getString("category");
					Category c = Category.valueOf(cat.toString());
					addition = GetAllAddition(itemid);
					Items = new Item(itemid, RS1.getString(2), Double.parseDouble(RS1.getString(3)),
							(ArrayList<Addition>) addition, Integer.parseInt(RS.getString(3)), c);
					Items.setCate(cat);
					Itemss.add(Items);

				}
			}
			return Itemss;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param itemid
	 * @return List of all additions for items
	 * @throws SQLException
	 */
	public static ArrayList<Addition> GetAllAddition(int itemid) throws SQLException {
		ArrayList<Addition> addition = new ArrayList<Addition>();
		Addition add;
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("select * from assignment3.item_addition where Item_ID='" + itemid + "'");
		while (RS.next()) {
			add = new Addition(RS.getString(2));
			addition.add(add);
		}
		return addition;
	}

	/**
	 * get items of restaurant
	 * 
	 * @param id restaurant id
	 * @return return arraylist all the items of the restaurant
	 * @throws SQLException
	 */
	public static ArrayList<Item> getallitems(int id) throws SQLException {
		Item Items = null;

		AllItems = new ArrayList<Item>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("select * from assignment3.menu where IDRestaurant='" + id + "'");
			while (RS.next()) {
				int itemid = RS.getInt(2);
				ResultSet RS1 = ps1.executeQuery("SELECT * From assignment3.item where Item_ID='" + itemid + "'");
				if (RS1.next()) {
					Items = new Item(itemid, RS1.getString(2), Double.parseDouble(RS1.getString(3)),
							Integer.parseInt(RS.getString(3)));
					AllItems.add(Items);
				}
			}
			return AllItems;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void generate_qr(String image_name, String qrCodeData) {
		try {
			System.out.println(image_name + " " + qrCodeData);
			String filePath = "D:\\" + image_name + ".png";
			String charset = "UTF-8"; // or "ISO-8859-1"
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, 200, 200, hintMap);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
					new File(filePath));
			System.out.println("QR Code image created successfully!");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Add performance report
	 * 
	 * @param RestaurantName
	 * @param orderStatus
	 */
	public static void SendToPerformencereports(String RestaurantName, String orderStatus) {
		try {
			LocalDateTime today = LocalDateTime.now();
			int YearNow = today.getYear();
			int MonthNow = today.getMonthValue();
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * from assignment3.performencereports where RestaurantName='"
					+ RestaurantName + "' and Year = '" + YearNow + "' and Month = '" + MonthNow + "'");

			if (rs.next() == true) {
				if (orderStatus.equals("Ready") || orderStatus.equals("Delivery On it's way")) {
					PreparedStatement pt = conn.prepareStatement(
							"UPDATE assignment3.performencereports SET Early = ? WHERE RestaurantName = ? and Year = ? and Month = ?");
					int early = rs.getInt(5) + 1;
					pt.setInt(1, early);
					pt.setString(2, RestaurantName);
					pt.setInt(3, YearNow);
					pt.setInt(4, MonthNow);
					pt.executeUpdate();
				} else if (orderStatus.equals("Late (Ready)") || orderStatus.equals("Late (Delivery On it's way)")) {
					PreparedStatement pt = conn.prepareStatement(
							"UPDATE assignment3.performencereports SET Late = ? WHERE RestaurantName = ? and Year = ? and Month = ?");
					int late = rs.getInt(4) + 1;
					pt.setInt(1, late);
					pt.setString(2, RestaurantName);
					pt.setInt(3, YearNow);
					pt.setInt(4, MonthNow);
					pt.executeUpdate();
				}
			} else {
				String sql = "insert into assignment3.performencereports (RestaurantName,Year,Month,Late,Early) values (?,?,?,?,?)";
				try {
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setString(1, RestaurantName);
					preparedStmt.setInt(2, YearNow);
					preparedStmt.setInt(3, MonthNow);
					if (orderStatus.equals("Ready")) {
						preparedStmt.setInt(4, 0);
						preparedStmt.setInt(5, 1);
						preparedStmt.execute();
					} else if (orderStatus.equals("Late (Ready)")) {
						preparedStmt.setInt(4, 1);
						preparedStmt.setInt(5, 0);
						preparedStmt.execute();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * add order to processing table
	 * 
	 * @param oh
	 */
	public static void AddOrderToProccess(OrderHistory oh) {
		String sql = "insert into assignment3.processingorders (orderNum,IDRestaurant,IDuser,submitDate,ReceiveTime,Status) values (?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, oh.getOrdernumbercl());
			preparedStmt.setInt(2, oh.getRestaurantID());
			preparedStmt.setString(3, oh.getUserID());
			preparedStmt.setString(4, oh.getSubmitDate());
			preparedStmt.setString(5, "");
			preparedStmt.setString(6, oh.getStatuscl());
			preparedStmt.execute();

			PreparedStatement rs = conn.prepareStatement("UPDATE assignment3.order SET Status= ? WHERE orderNum = ?");
			rs.setString(1, "Accepted");
			rs.setInt(2, oh.getOrdernumbercl());
			rs.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
