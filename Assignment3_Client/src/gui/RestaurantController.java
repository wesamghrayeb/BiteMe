package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import common.Category;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.Resturaunt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created chosen restaurant controller according to its available items in the database
 * @author asem
 *
 */
public class RestaurantController implements Initializable {

	public static Resturaunt restaurant;
	public static HashMap<Item, Object> spinners;
	ArrayList<Item> item;
	HashMap<Item, Integer> menu;

	public static Stage stageRestaurant;

	// constructor for creating menu of restaurant
	public RestaurantController(Resturaunt r) {
		restaurant = r;
		menu = restaurant.getMenu().getMenu();
	}
    public static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch);
        ImageView imageView = new ImageView();
        //You can set width and height
       // imageView.setTranslateY(3);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setImage(i);
        return imageView;
    }
    
	/**
	 * set menu to categories (Salad,MainDish,Dessert,Drink)
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setResizable(false);
		
		Font font = Font.font("Arial",  16);
		Font font1 = Font.font("Arial", FontWeight.BOLD, 25);
		 //Creating an image 
	      Image image = new Image("icons/restaurant.png");  
	      //Setting the image view 
	      ImageView imageView = new ImageView(image); 
	     
	      //Setting the position of the image 
	      imageView.setX(300); 
	      imageView.setY(18); 
	      
	      //setting the fit height and width of the image view 
	      imageView.setFitHeight(50); 
	      imageView.setFitWidth(50); 
	      
		TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		AnchorPane pane = new AnchorPane();
	   
		pane.setStyle("-fx-background-color: #555555;");
		pane.setPrefSize(800, 600);
		Label label = new Label(restaurant.getResturaunt_Name());
		label.setTextFill(Color.web("#eb8b00"));
		label.setFont(new Font("Arial", 50));
		label.setTranslateX(350);
		label.setTranslateY(18);

		Label NavigationLbl = new Label("LogIn->Home Page->Resturant's Menu->Resturant Menu");
		NavigationLbl.setTextFill(Color.web("#FFFFFF"));
		NavigationLbl.setFont(new Font("Arial", 12));
		NavigationLbl.setTranslateX(5);
		NavigationLbl.setTranslateY(5);
		pane.getChildren().add(NavigationLbl);
		
		MessagesClass msg = new MessagesClass(Messages.GetRefund, ChatClient.user.getID(),
				restaurant.getResturauntID());
		ClientUI.chat.accept(msg);
		
		if (ChatClient.getRefund != -1&&ChatClient.getRefund!=0) {
			Label refund = new Label("Your Fund is: "+ChatClient.getRefund+"$");
			refund.setTextFill(Color.web("#eb8b00"));
			refund.setFont(new Font("Arial", 25));
			refund.setTranslateX(550);
			refund.setTranslateY(90);
			pane.getChildren().add(refund);
		}

		Label meallbl = new Label("Pick your meal:");
		meallbl.setTextFill(Color.web("#eb8b00"));
		meallbl.setFont(new Font("Arial", 25));
		meallbl.setTranslateX(10);
		meallbl.setTranslateY(90);
		
		pane.getChildren().addAll(meallbl, label);

		HBox hbox = new HBox(10);
		hbox.setTranslateX(10);
		hbox.setTranslateY(140);
		
		// create salad menu
		VBox saladBox = new VBox(5);
		saladBox.setStyle("-fx-background-color: #707070");
		saladBox.setPrefSize(200, 360);
		ScrollPane saladPane = new ScrollPane();
		saladPane.setContent(saladBox);
		saladPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		saladPane.setFitToWidth(true); // set content width to viewport width
		saladPane.setPannable(true); // allow scrolling via mouse dragging
		saladPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		saladPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		// create mainDish menu
		VBox mainDishBox = new VBox(5);
		mainDishBox.setPrefSize(200, 360);
		mainDishBox.setStyle("-fx-background-color: #707070");
		ScrollPane mainDishPane = new ScrollPane();
		mainDishPane.setContent(mainDishBox);
		mainDishPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		mainDishPane.setFitToWidth(true); // set content width to viewport width
		mainDishPane.setPannable(true); // allow scrolling via mouse dragging
		mainDishPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		mainDishPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		// create dessert menu
		VBox dessertBox = new VBox(5);
		dessertBox.setStyle("-fx-background-color: #707070");
		dessertBox.setPrefSize(200, 360);
		ScrollPane dessertPane = new ScrollPane();
		dessertPane.setContent(dessertBox);
		dessertPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		dessertPane.setFitToWidth(true); // set content width to viewport width
		dessertPane.setPannable(true); // allow scrolling via mouse dragging
		dessertPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		dessertPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		// create drinks menu
		VBox drinkBox = new VBox(5);
		drinkBox.setStyle("-fx-background-color: #707070");
		drinkBox.setPrefSize(200, 360);
		ScrollPane drinkPane = new ScrollPane();
		drinkPane.setContent(drinkBox);
		drinkPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		drinkPane.setFitToWidth(true); // set content width to viewport width
		drinkPane.setPannable(true); // allow scrolling via mouse dragging
		drinkPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		drinkPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		// drinkBox.getChildren().add(line);
		spinners = new HashMap<Item, Object>();
		item = restaurant.getMenu().getItems();

		// Initialize each item quantity according to database
		for (int i = 0; i < item.size(); i++) {
			HBox tmp = new HBox(10);
			Item it = (Item) item.get(i);

			Category c = it.getCat();
			Spinner<Integer> spinner = new Spinner<>();
			int initialValue = 0;
			// Value factory.
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,
					menu.get(it), initialValue);
			spinner.setValueFactory(valueFactory);
			spinner.setPrefSize(70, 5);
			spinner.setId("spinner");
			spinners.put(it, spinner);
			Label lbl = new Label(it.getItem_Name() + " " + it.getPrice() + "$");
			Label lbl2 = new Label(" ");
			lbl.setFont(font);
			lbl.setTextFill(Color.web("black"));
			tmp.getChildren().addAll(lbl2,spinner, lbl);

			switch (c) {
			// add each item to VBox according to its category
			case Drink:
				tmp.setTranslateY(10);
				drinkBox.getChildren().add(tmp);

				break;
			case Dessert:
				tmp.setTranslateY(10);
				dessertBox.getChildren().add(tmp);

				break;
			case Salad:
				tmp.setTranslateY(10);
				saladBox.getChildren().add(tmp);

				break;
			case MainDish:
				tmp.setTranslateY(10);
				mainDishBox.getChildren().add(tmp);

				break;
			}
		}

		// add all label to gui
		 pane.getChildren().add(imageView);
		tabPane.setTranslateX(10);
		tabPane.setTranslateY(120);
		tabPane.setPrefSize(780, 380);
		Tab tab1 = new Tab("Salad", saladPane);
        Tab tab2 = new Tab("MainDish"  , mainDishPane);
        Tab tab3 = new Tab("Dessert" , dessertPane);
        Tab tab4 = new Tab("Drink" , drinkPane);
        tab1.setGraphic(buildImage("icons/salad.png"));
        tab2.setGraphic(buildImage("icons/mainDish.png"));
        tab3.setGraphic(buildImage("icons/Dessert.png"));
        tab4.setGraphic(buildImage("icons/drink.png"));
       // tabPane.setStyle( "-fx-tab-min-height:30px;");
		tabPane.getTabs().addAll(tab1,tab2,tab3,tab4);
		  
		//hbox.getChildren().addAll(saladPane, mainDishPane, dessertPane, drinkPane);
		//pane.getChildren().addAll(saladlbl, mainDishlbl, dessertlbl, drinklbl);
		//pane.getChildren().add(hbox);
	    
	    pane.getChildren().add(tabPane);

		Button submitBtn = new Button("Next");
		Button backBtn = new Button("Back");
		submitBtn.setTranslateX(680);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(30);
		backBtn.setTranslateY(525);
		pane.getChildren().addAll(backBtn, submitBtn);
		backBtn.setOnAction(backToUserHomePage);
		submitBtn.setOnAction(Additions);

		Scene scene = new Scene(pane, 800, 600);
		scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("spinner1.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("tabs.css").toExternalForm());
		tabPane.setId("tab-pane");
		tab1.setId("tab");
		tab2.setId("tab");
		tab3.setId("tab");
		tab4.setId("tab");
		
		backBtn.setId("button-back");
		submitBtn.setId("button-submit");
		primaryStage.setTitle("Resturaunt's Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * go to additions page
	 */
	EventHandler<ActionEvent> Additions = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {

			int countItemQuantity = 0;
			// count selected items quantity by user
			for (Item it : spinners.keySet()) {
				System.out.println(it.toString());
				Spinner<Integer> spin = (Spinner<Integer>) spinners.get(it);
				countItemQuantity += spin.getValue().intValue();
			}
			// check if user didn't pick any item
			// if user didn't pick any items pops alert
			if (countItemQuantity == 0) {
				Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
				alert.setTitle("Message");
				alert.setContentText("Fill in all information");
				alert.getDialogPane().setPrefSize(200, 100);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.CLOSE) {
					return;
				}
			}

			stageRestaurant = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stageRestaurant.hide();
			Stage primaryStage1 = new Stage();
			AdditionsController AC = new AdditionsController();
			try {
				AC.start(primaryStage1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	/**
	 * go back to restaurants list
	 */
	EventHandler<ActionEvent> backToUserHomePage = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage1 = new Stage();
			ResturauntMenuController RMC = new ResturauntMenuController();
			try {
				RMC.start(primaryStage1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// t.setText("d");
	}
}
