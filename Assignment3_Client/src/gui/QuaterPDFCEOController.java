package gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.mysql.cj.jdbc.Blob;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.MyFile;
import common.PDFList;
import common.User;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Bashar Bashir
 * Controller class for Downloading the PDF file
 */
public class QuaterPDFCEOController implements Initializable, Serializable { 
  
	/**
	 * Table showes the PDF files that sends by the managers
	 */
	@FXML
	private TableView<PDFList> Table;

	/**
	 * Restaurants location (North,South,Center)
	 */
	@FXML
	private TableColumn<PDFList, String> Location;

	/**
	 * Choosing wanted year
	 */
	@FXML
	private TableColumn<PDFList, Integer> Year;

	
	/**
	 * Choosing wanted Quarter
	 */
	@FXML
	private TableColumn<PDFList, String> Months;

	/**
	 * ObservableList for the reports that sends by the managers
	 */
	ObservableList<PDFList> listM;

	/**
	 * Text for appearing if there is any wrong thing
	 */
	@FXML
	private Text StatusText;

	/**
	 * Clicked PDF report from the list
	 */
	private PDFList Wanted;

	/**
	 * Saving the PDF report in Clicker
	 */
	private PDFList Clicker;
	
	/**
	 * Stage for CEO home page
	 */
	static Stage stager;
	
	/**
	 * Path for saving PDF in CEO computer  
	 */
	static String mypath;

	/**
	 * @param event
	 * Fucntion to back for CEO home back
	 */
	@FXML
	void BackBtn(ActionEvent event) {
		CeoHomePageController.CEOStage.show();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	
	/**
	 * @param event
	 * @throws SQLException
	 * @throws IOException
	 * @throws DocumentException
	 * Function for taking the chose PDF from DB
	 */
	@FXML
	void DownloadBtn(ActionEvent event) throws SQLException, IOException, DocumentException {
		String Month = null;
		if (Clicker == null) {
			StatusText.setText("Please choose quarter");
			return;
		}
		if (Clicker.getMonths().equals("1,2,3")) {
			Month = "3";
		}
		if (Clicker.getMonths().equals("4,5,6")) {
			Month = "6";
		}
		if (Clicker.getMonths().equals("7,6,9")) {
			Month = "9";
		}
		if (Clicker.getMonths().equals("10,11,12")) {
			Month = "12";
		}
		mypath = "C:\\G5BiteMe\\Report" + String.valueOf(Clicker.getLocation())
				+ String.valueOf(Clicker.getYear()) + Month + ".pdf";
		MessagesClass msg1 = new MessagesClass(Messages.DownlowndPDFFromDB, mypath);
		ClientUI.chat.accept(msg1);
		DownloadPDF(ChatClient.PDFFile);
	}

	/**
	 * @param pDFFile
	 * @throws IOException
	 * @throws DocumentException
	 * Function for downloading the PDF in the CEO computer on the path that chose by the CEO
	 */
	private void DownloadPDF(File pDFFile) throws IOException, DocumentException {
		stager.hide();
		CeoHomePageController.CEO.hide();
		String path = "";
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int s = j.showSaveDialog(j);
		if (s == JFileChooser.APPROVE_OPTION) {
			path = j.getSelectedFile().getPath();
			System.out.println(path);
		}
		MyFile msg = new MyFile(mypath);
		byte[] mybytearray = new byte[(int) pDFFile.length()];
		FileInputStream fis = new FileInputStream(pDFFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		msg.initArray(mybytearray.length);
		msg.setSize(mybytearray.length);
		bis.read(msg.getMybytearray(), 0, mybytearray.length);
		int fileSize = msg.getSize();
		File myfile = new File(path + "\\QuarterReport.pdf");
		FileOutputStream fos = new FileOutputStream(myfile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		try {
			bos.write(((MyFile) msg).getMybytearray(), 0, fileSize);
			bos.flush();
			fos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(true) {
			StatusText.setText("The file is downloaded successfully to your computer");
		}
		CeoHomePageController.CEO.show();
		stager.show();
	}

	/**
	 * @param primaryStage
	 * @throws Exception
	 * Start function for drawing the needed page
	 */
	public void start(Stage primaryStage) throws Exception {
		stager = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/QuaterPDFCEO.fxml").openStream());
		Scene scene = new Scene(root);
		stager.setTitle("Quater PDF");
		stager.setScene(scene);
		stager.show();
	}

	/**
	 *Initialize for drawing needed things before opening the page
	 *Taking the PDF's that sends by the manager and putting it in the list
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		MessagesClass msg1 = new MessagesClass(Messages.getPDFLists, "Text");
		ClientUI.chat.accept(msg1);
		if (ChatClient.PDFListValues.size() > 0) {
			listM = FXCollections.observableArrayList(ChatClient.PDFListValues);
			Location.setCellValueFactory(new PropertyValueFactory<PDFList, String>("Location"));
			Year.setCellValueFactory(new PropertyValueFactory<PDFList, Integer>("Year"));
			Months.setCellValueFactory(new PropertyValueFactory<PDFList, String>("Months"));
			Table.setItems(listM);
		}
		else {
			StatusText.setText("There is no PDF's by any manager");
		}
		PDFClient();
	}

	/**
	 * Function for taking the chose report (setOnMouseClicked)
	 */
	private void PDFClient() {
		Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Wanted = Table.getItems().get(Table.getSelectionModel().getSelectedIndex());
				Clicker = new PDFList(Wanted.getLocation(), Wanted.getYear(), Wanted.getMonths());
			}

		});

	}

}