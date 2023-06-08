package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import common.Visa;
import common.W4CBussiness;
import common.W4CNormal;
import common.WorkerUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author moham this class to get all the users with no type and shows them to
 *         the branch manager the manager choose user and decide which kind to
 *         be normal or business
 * 
 */
public class CreateNewNormalAccountController implements Initializable {
	/**
	 * list used in tableview
	 */
	ObservableList<User> listM;
	ObservableList<User> dataList;
	User user;
	private URL location;
	private ResourceBundle resources;
	@FXML
	private Text ExistsA;

	/**
	 * use it to put the id or username or first name and search in table view
	 */
	@FXML
	private TextField filterField;

	/**
	 * back button
	 */
	@FXML
	private Button Back;

	@FXML
	private Button Subm;

	/**
	 * error message
	 */
	@FXML
	private Text ErrorText;

	/**
	 * path
	 */
	@FXML
	private Text pathtext;

	/**
	 * table view type user
	 */
	@FXML
	private TableView<User> acoountTable;
	/**
	 * this boolean if click in checkboks the company and package appear
	 */
	private boolean flagforbussiness = false;
	/**
	 * id columns
	 */
	@FXML
	private TableColumn<User, String> idcl;

	/**
	 * first name cl
	 */
	@FXML
	private TableColumn<User, String> FNcl;

	/**
	 * last name cl
	 */
	@FXML
	private TableColumn<User, String> LNcl;

	/**
	 * user name cl
	 */
	@FXML
	private TableColumn<User, String> UNcl;
	/**
	 * w4c randomly
	 */
	@FXML
	private TextField W4C;

	/**
	 * password columns
	 */
	@FXML
	private TableColumn<User, String> Passwordcl;

	/**
	 * email cl
	 */
	@FXML
	private TableColumn<User, String> Emailcl;

	/**
	 * phone cl
	 */
	@FXML
	private TableColumn<User, String> Phonecl;

	/**
	 * to get id
	 */
	@FXML
	private TextField id;
	/**
	 * to get phone
	 */
	@FXML
	private TextField phone;
	/**
	 * to get email
	 */
	@FXML
	private TextField email;
	/**
	 * to get card hold name for visa
	 */
	@FXML
	private TextField CardholderName;
	/**
	 * to get card number visa
	 */
	@FXML
	private TextField CardNumber;

	/**
	 * to getcvv
	 */
	@FXML
	private TextField CVV;
	/**
	 * to get with visa
	 */
	@FXML
	private CheckBox withvisa;
	/**
	 * to get password
	 */
	@FXML
	private TextField password;
	/**
	 * to get user name
	 */

	@FXML
	private TextField username;

	/**
	 * to get last name
	 */

	@FXML
	private TextField lastname;
	/**
	 * to get first name
	 */

	@FXML
	private TextField firstname;
	/**
	 * to get check box to let the company combobox and package appear
	 */

	@FXML
	private CheckBox putbussiness;
	/**
	 * to get all accepted company
	 */

	@FXML
	private ComboBox<String> companybox;
	/**
	 * used to visa
	 */
	private boolean flag = false;
	/**
	 * to get choose company
	 */
	@FXML
	private Text choosecompany;
	/**
	 * to get for normal
	 */

	@FXML
	private CheckBox putnormal;

	@FXML
	private Label namelbl;

	@FXML
	private Label numlbl;

	@FXML
	private Label datelbl;

	@FXML
	private Label cvvlbl;
	@FXML
	private TextField year;

	@FXML
	private TextField month;

	/**
	 * @param primaryStage start method to show the gui CreateNewNormalAccount
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/CreateNewNormalAccount.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param event checkbox action to appear or not
	 */
	@FXML
	void withvisa(ActionEvent event) {
		flag = !flag;
		CardholderName.setVisible(flag);
		CardNumber.setVisible(flag);
		year.setVisible(flag);
		month.setVisible(flag);
		CVV.setVisible(flag);
		namelbl.setVisible(flag);
		numlbl.setVisible(flag);
		datelbl.setVisible(flag);
		cvvlbl.setVisible(flag);
	}

	/**
	 * @param event back button this method return the branch to his home page
	 * 
	 */
	@FXML
	void BackBt(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;
		try {
			root = loader.load(this.getClass().getResource("/gui/BranchManager_Home_Page.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param event submit button action this method to accept user from external
	 *              table if clicked in submit this but take all the information of
	 *              user to server and the server reach to all table business or
	 *              normal and add them to bite me and can use the app
	 */
	@FXML
	void submitBt(ActionEvent event) {
		if (!putnormal.isSelected() && !putbussiness.isSelected()) {
			ErrorText.setText("Error, Choose User type");
			return;
		}
		if (putnormal.isSelected() && putbussiness.isSelected()) {
			ErrorText.setText("Error,You have to choose one type");
			return;
		}
		if (putnormal.isSelected() && !withvisa.isSelected()) {
			ErrorText.setText("Error,You have to put visa from normal user");
			return;
		}
		if (putnormal.isSelected()) {
			String IDW4C = W4C.getText();
			user = new User(id.getText(), username.getText(), password.getText(), "Normal", firstname.getText(),
					email.getText(), phone.getText(), lastname.getText(), null, null, 0, 1, "Active", 0, 0);
			W4CNormal w4c = new W4CNormal(Integer.parseInt(IDW4C), user);
			if (!withvisa.isSelected()) {
				ErrorText.setText("You Should add visa card to add normal user " + firstname.getText() + " .");
				return;

			} else {
				if (id.getText().isEmpty() || CardNumber.getText().isEmpty() || year == null
						|| CardholderName.getText().isEmpty() || month == null || CVV.getText().isEmpty()) {
					ErrorText.setText("There is lack in visa informtion");
					return;
				}
				Visa visa1 = new Visa(id.getText(), CardNumber.getText(), Integer.parseInt(CVV.getText()),
						Integer.parseInt(year.getText()), CardholderName.getText(), Integer.parseInt(month.getText()));
				System.out.println(visa1 + "*****");
				MessagesClass msg1 = new MessagesClass(Messages.AddNewUserwithvisa, user, w4c, visa1, "normaluser");
				ClientUI.chat.accept(msg1);
				ErrorText.setText(firstname.getText() + " User Has Been Added as a Normal User With Visa.");
				initialize(location, resources);
			}
		} else if (putbussiness.isSelected()) {
			String IDW4C = W4C.getText();
			user = new User(id.getText(), username.getText(), password.getText(), "Bussiness", firstname.getText(),
					email.getText(), phone.getText(), lastname.getText(), companybox.getValue(), null, 0, 1, "Frozen",
					0, 0);
			W4CBussiness w4cb = new W4CBussiness(Integer.parseInt(IDW4C), user, Integer.parseInt(user.getID()), 0.0,
					0.0, null);
			if (!withvisa.isSelected()) {
				MessagesClass msg1 = new MessagesClass(Messages.AddNewUser, user, w4cb, "bussinessuser");
				ClientUI.chat.accept(msg1);
				ErrorText.setText(firstname.getText() + " User Has Been Added as a Bussiness User  With Visa.");
				initialize(location, resources);
			} else {
				if (id.getText().isEmpty() || CardNumber.getText().isEmpty() || year == null
						|| CardholderName.getText().isEmpty() || month == null || CVV.getText().isEmpty()) {
					ErrorText.setText("There is lack in visa informtion");
					return;
				}
				Visa visa = new Visa(id.getText(), CardNumber.getText(), Integer.parseInt(CVV.getText()),
						Integer.parseInt(year.getText()), CardholderName.getText(), Integer.parseInt(month.getText()));
				MessagesClass msg1 = new MessagesClass(Messages.AddNewUserwithvisa, user, w4cb, visa, "bussinessuser");
				ClientUI.chat.accept(msg1);
				ErrorText.setText(firstname.getText() + " User Has Been Added as a Bussiness User.");
				initialize(location, resources);
			}
		}
	}

	/**
	 * @param event Enters business account
	 */
	@FXML
	void putbussinesscheckbox(ActionEvent event) {
		flagforbussiness = !flagforbussiness;
		companybox.setVisible(flagforbussiness);
		choosecompany.setVisible(flagforbussiness);

	}

	@FXML
	void putnormalcheckbox(ActionEvent event) {

	}

	/**
	 * Initialize all parameters and JavaFx fields on start of controller
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		companybox.setVisible(false);
		choosecompany.setVisible(false);
		W4C.setText(String.valueOf(ChatClient.W4CC));

		CardholderName.setVisible(false);
		CardNumber.setVisible(false);
		month.setVisible(false);
		year.setVisible(false);
		CVV.setVisible(false);
		namelbl.setVisible(false);
		numlbl.setVisible(false);
		datelbl.setVisible(false);
		cvvlbl.setVisible(false);
		companybox.getItems().removeAll(companybox.getItems());
		load();
		MessagesClass msg1 = new MessagesClass(Messages.GetAllUsersFromUsersTable, null);
		System.out.println(msg1);
		ClientUI.chat.accept(msg1);
		listM = FXCollections.observableArrayList(ChatClient.GetAllUsersFromUsersTable);
		idcl.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
		FNcl.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
		LNcl.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
		UNcl.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
		Passwordcl.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		Emailcl.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		Phonecl.setCellValueFactory(new PropertyValueFactory<User, String>("phonenumber"));
		acoountTable.setItems(listM);
		settext();
		FilteredList<User> filteredData = new FilteredList<>(listM, b -> true);
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(User -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (User.getID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (User.getFirstname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(User.getUserName()).indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});

		SortedList<User> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(acoountTable.comparatorProperty());
		acoountTable.setItems(sortedData);
	}

	private void settext() {
		acoountTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				User w = acoountTable.getItems().get(acoountTable.getSelectionModel().getSelectedIndex());
				id.setText(w.getID());
				email.setText(w.getEmail());
				firstname.setText(w.getFirstname());
				username.setText(w.getUserName());
				lastname.setText(w.getLastname());
				password.setText(w.getPassword());
				phone.setText(w.getPhonenumber());
				CVV.setText("" + w.getVisa().getCVV());
				CardNumber.setText(w.getVisa().getNumber());
				CardholderName.setText(w.getVisa().getCardHolderName());
				year.setText("" + w.getVisa().getYear());
				month.setText("" + w.getVisa().getMonth());

			}
		});

	}

	/**
	 * Get all items from tables
	 */
	private void load() {
		companybox.getItems().removeAll(companybox.getItems());
		MessagesClass msg1 = new MessagesClass(Messages.GetallAvailableCompany, null);
		ClientUI.chat.accept(msg1);
		for (int i = 0; i < ChatClient.AllCompany.size(); i++) {
			companybox.getItems().addAll(ChatClient.AllCompany.get(i).getCompanyname());
		}
	}
}