package gui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.BussinessUser;
import common.Messages;
import common.MessagesClass;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Shows User(Customer) home page with all details and buttons
 * @author moham
 *
 */
public class NormalUserHomePageController implements Initializable {

	/**
	 * name is a text to put user name
	 */
	@FXML
	private Text name;
	/**
	 * use it to frozen and active member if frozen can't use it
	 */
	@FXML
	private Button new_order_btn;

	@FXML
	private TextField w4ctxt;

	@FXML
	private Text companynametxt;

	/**
	 * clock text
	 */
	@FXML
	private Text clock;

	/**
	 * in clock use
	 */
	public static String sClock;

	/**
	 * status of normal user
	 */
	@FXML
	private Text status;
	/**
	 * path text
	 */
	@FXML
	private Text pathtext;
	@FXML
	private Button ext_home_page_btn;

	/**
	 * prints text on screen for user to see his balance
	 */
	@FXML
	private Text balance;

	@FXML
	private Text errtxt;
	@FXML
	private ImageView QRimage;
	static BussinessUser Bussinessuser;
	ClientController chat;
	public static Stage homePageStage;

	/**
	 * @param event action of buttons
	 * @throws Exception return to login page (back)
	 */
	@FXML
	void logoutbtn(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		/** send to server to change the islogeed in in DB */
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
		LogInForm aFrame = new LogInForm();
		Stage primaryStage = new Stage();
		aFrame.start1(primaryStage);
	}

	/**
	 * @param event action of new order button
	 * @throws IOException this method used after we check the w4c if correct => can execute new order
	 */
	@FXML
	void neworder(ActionEvent event) throws IOException {
		String w4c = (String) w4ctxt.getText();
		if (!w4c.equals("")) {
			MessagesClass msg = new MessagesClass(Messages.W4C, ChatClient.user);
			ClientUI.chat.accept(msg);
		} else {
			// need to enter text here to client to enter w4c details
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("Enter W4C Details");
			return;
		}
		if (Integer.valueOf(w4c).equals(ChatClient.w4c.getCode())) {
			homePageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			homePageStage.hide();
			Stage primaryStage = new Stage();
			ResturauntMenuController RMC = new ResturauntMenuController();
			RMC.start(primaryStage);
		} else {
			// print text to user "w4c does not match"
			Timeline fiveseconds=new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					errtxt.setText("");
				}
				
			}));
			fiveseconds.setCycleCount(Timeline.INDEFINITE);
			fiveseconds.play();
			errtxt.setText("W4C does not match");
			return;
		}
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
		return qrCodeResult.getText();
	}

	private String getFilePath() {
		String path = "";
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int s = j.showSaveDialog(j);
		if (s == JFileChooser.APPROVE_OPTION) {
			path = j.getSelectedFile().getPath();
			return path;
		}
		return "";

	}

	/**
	 * @param event => imports W4C code to the home page to load the User information
	 */
	@FXML
	void QR_Import(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();
		String filePath = getFilePath();
		stage.show();
		if (filePath.equals("")) {
			return;
		} else {
			String charset = "UTF-8";
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			try {
				String code = readQRCode(filePath, charset, hintMap);
				FileInputStream input = new FileInputStream(filePath);
				Image image = new Image(input);
				QRimage.setImage(image);
				w4ctxt.setText(code);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void QR_Create(ActionEvent event) {
		MessagesClass msg = new MessagesClass(Messages.W4C, ChatClient.user);
		ClientUI.chat.accept(msg);
		MessagesClass msg1 = new MessagesClass(Messages.QRcreateW4C, ChatClient.user);
		ClientUI.chat.accept(msg1);
	}

	/**
	 * @param primaryStage to open normal homepage gui
	 * @throws Exception this start method to appear the normalhomepage gui
	 */
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/gui/NormalUserHomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("normal user");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}
	
	/**
	 * @param event => Go to orders controller. User can see all orders that he bought and see its process
	 * @throws Exception
	 */
	@FXML
    void OrderHistory(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		OrderHistoryController OHC = new OrderHistoryController();
		Stage primaryStage = new Stage();
		OHC.start(primaryStage);
    }
	 
	/**
	 * an initializer is a block of code that has no associated name or data type
	 * and is placed outside of any method
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (TypeOfOrderController.shared == true) {
			chat = new ClientController(FirstPageController.ip, 5555);
			ClientUI.chat = chat;
		}
		pathtext.setText("LogIn->Home Page");
		/** get all the information about this normal user */
		MessagesClass msg = new MessagesClass(Messages.getnormaluser, ChatClient.user);
		ClientUI.chat.accept(msg);

		if (ChatClient.user.getUserType().equals("Normal")) {
			name.setText(ChatClient.normaluser.getFirstName());
			status.setText(ChatClient.user.getStatus());
		}

		if (ChatClient.user.getUserType().equals("Bussiness")) {
			msg = new MessagesClass(Messages.GetbissnessUser, ChatClient.user);
			ClientUI.chat.accept(msg);
			name.setText(ChatClient.Bussinessuser.getFirstName());
			companynametxt.setText("Company: " + ChatClient.Bussinessuser.getCompany());
			status.setText(ChatClient.user.getStatus());
			balance.setText("Wallet Balance: " + String.format("%.2f",ChatClient.Bussinessuser.getW4c().getMoney()) + "$");
		}

		if (ChatClient.user.getStatus().equals("Frozen")) {
			new_order_btn.setDisable(true);
		}

		w4ctxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,3}?")) {
					w4ctxt.setText(oldValue);
				}
			}
		});
		/* put clock in gui of normalhome page */
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				clock.setText(LocalDateTime.now().format(format));
				sClock = String.valueOf(LocalDateTime.now().format(format));
			}
		}));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();/**/
	}

}
