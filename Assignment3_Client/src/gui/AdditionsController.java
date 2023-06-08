package gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import common.Item;
import common.ViewOrderDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Show Additions to each selected Item for the customer
 * Additions are build with CheckBox so that the customer can choose more than one addition the his items
 * Each item has it's own addition that was added by the restaurant itself
 * @author asem
 *
 */
public class AdditionsController {
	public static LinkedHashMap<Item, Object> additions;
	public static LinkedHashMap<Integer, LinkedHashMap<Item, Object>> Order;
	public static ArrayList<ViewOrderDetails> arlist;
	public static double OverAllSum;
	public static Stage stageAdditions;
	
	/**
	 * Build the controller without according to the additions and items size
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setResizable(false);
		
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
		 //header of the page
	      
	      Label label = new Label(RestaurantController.restaurant.getResturaunt_Name());
			label.setTextFill(Color.web("#eb8b00"));
			label.setFont(new Font("Arial", 50));
			label.setTranslateX(350);
			label.setTranslateY(18);
	      
	      
		 Label Additionslbl=new Label("Pick additions to your meal:");
		 Additionslbl.setTextFill(Color.web("#eb8b00"));
		 Additionslbl.setFont(new Font("Arial", 25));
		 Additionslbl.setTranslateX(10);
		 Additionslbl.setTranslateY(90);
		 Font font1=Font.font("Arial", FontWeight.BOLD, 25);
		 Font font=Font.font("Arial", 23);
		 Font font2=Font.font("Arial",  20);
		 OverAllSum = 0;
		 AnchorPane pane = new AnchorPane();
		 pane.setStyle("-fx-background-color: #555555;");
		 pane.getChildren().add(Additionslbl);
		 TabPane tabPane = new TabPane();
	    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	    
	    
	    Label NavigationLbl = new Label("LogIn->Home Page->Resturant's Menu->Resturant Menu->Additions");
		NavigationLbl.setTextFill(Color.web("#FFFFFF"));
		NavigationLbl.setFont(new Font("Arial", 12));
		NavigationLbl.setTranslateX(5);
		NavigationLbl.setTranslateY(5);
		pane.getChildren().add(NavigationLbl);
	    
		 HBox hbox=new HBox(10);
		 hbox.setTranslateX(10);
		 hbox.setTranslateY(140);
		
		//create salad menu additions
		VBox saladBox = new VBox(5);
		saladBox.setPrefSize(200, 360);
		
		saladBox.setStyle("-fx-background-color: #707070");
		
		ScrollPane saladPane = new ScrollPane(saladBox);
		saladPane.setContent(saladBox);
		saladPane.setFitToWidth(true); // set content width to viewport width
		saladPane.setPannable(true); // allow scrolling via mouse dragging
		saladPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		saladPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		saladPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		//create mainDish menu additions
		VBox mainDishBox = new VBox(5);
		mainDishBox.setPrefSize(200, 360);
		mainDishBox.setStyle("-fx-background-color: #707070");		
		ScrollPane mainDishPane = new ScrollPane(mainDishBox);
		mainDishPane.setContent(mainDishBox);
		mainDishPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		mainDishPane.setFitToWidth(true); // set content width to viewport width
		mainDishPane.setPannable(true); // allow scrolling via mouse dragging
		mainDishPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		mainDishPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		
		//create dessert menu additions
		VBox dessertBox = new VBox(5);
		dessertBox.setPrefSize(200, 360);
		dessertBox.setStyle("-fx-background-color: #707070");
		ScrollPane dessertPane = new ScrollPane(dessertBox);
		dessertPane.setContent(dessertBox);
		dessertPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		dessertPane.setFitToWidth(true); // set content width to viewport width
		dessertPane.setPannable(true); // allow scrolling via mouse dragging
		dessertPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		dessertPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		//create drinks menu additions
		VBox drinkBox = new VBox(5);
		drinkBox.setPrefSize(200, 360);
		drinkBox.setStyle("-fx-background-color: #707070");
		ScrollPane drinkPane = new ScrollPane(drinkBox);	
		drinkPane.setContent(drinkBox);
		drinkPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		drinkPane.setFitToWidth(true); // set content width to viewport width
		drinkPane.setPannable(true); // allow scrolling via mouse dragging
		drinkPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		drinkPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	
	
		int countItemQuantity = 0;
		for (Item it : RestaurantController.spinners.keySet()) {
			Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
			countItemQuantity += spin.getValue().intValue();
		}

		int i = 0;
		arlist = new ArrayList<ViewOrderDetails>();
		//add additions to gui and category
		for (Item it : RestaurantController.spinners.keySet()) {

			Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
			for (int k = 0; k < spin.getValue().intValue(); k++) {
				CheckBox[] CB = new CheckBox[it.getAdditions().size()];
				for (int j = 0; j < it.getAdditions().size(); j++) {
					CheckBox checkbox = new CheckBox(it.getAdditions().get(j).getName());
					CB[j] = checkbox;
					CB[j].setTranslateX(5);
					CB[j].setFont(font2);
					CB[j].setTextFill(Color.web("black"));
				}
				OverAllSum += it.getPrice();//calculate price of picked items
			
				ViewOrderDetails VOD = new ViewOrderDetails(CB, it);
				arlist.add(VOD);
				     Label lbl = new Label(it.getItem_Name());
				     lbl.setFont(font);
				     lbl.setTextFill(Color.web("#eb8b00"));
				if (it.getCat().name().equals("Salad")) {
					lbl.setTranslateY(10);
					lbl.setTranslateX(5);
					
					saladBox.getChildren().add(lbl);
					saladBox.getChildren().addAll(CB);
				} else if (it.getCat().name().equals("MainDish")) {
					lbl.setTranslateY(5);
					lbl.setTranslateX(5);
					mainDishBox.getChildren().add(lbl);
					mainDishBox.getChildren().addAll(CB);
				} else if (it.getCat().name().equals("Dessert")) {
					lbl.setTranslateY(5);
					lbl.setTranslateX(5);
					dessertBox.getChildren().add(lbl);
					dessertBox.getChildren().addAll(CB);
				} else {
					lbl.setTranslateY(5);
					lbl.setTranslateX(5);
					drinkBox.getChildren().add(lbl);
					drinkBox.getChildren().addAll(CB);
				}
				i++;
			}
		}
		

		// add all label to gui
	    pane.getChildren().addAll(imageView,label);
		tabPane.setTranslateX(10);
		tabPane.setTranslateY(120);
		tabPane.setPrefSize(780, 380);
		Tab tab1 = new Tab("Salad", saladPane);
	    Tab tab2 = new Tab("MainDish"  , mainDishPane);
	    Tab tab3 = new Tab("Dessert" , dessertPane);
	    Tab tab4 = new Tab("Drink" , drinkPane);
        tab1.setGraphic(RestaurantController.buildImage("icons/salad.png"));
        tab2.setGraphic(RestaurantController.buildImage("icons/mainDish.png"));
	    tab3.setGraphic(RestaurantController.buildImage("icons/Dessert.png"));
	    tab4.setGraphic(RestaurantController.buildImage("icons/drink.png"));
		tabPane.getTabs().addAll(tab1,tab2,tab3,tab4);
		pane.getChildren().add(tabPane);
			
		Button submitBtn = new Button("Show Order");
		Button backBtn = new Button("Back");
		submitBtn.setTranslateX(610);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(30);
		backBtn.setTranslateY(525);
		pane.getChildren().addAll(backBtn, submitBtn);
		backBtn.setOnAction(backToRestaurantController);
		submitBtn.setOnAction(ShowOrder);

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

		primaryStage.setTitle("Additions Info");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Go back to restaurant menu controller
	 */
	EventHandler<ActionEvent> backToRestaurantController = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			stageAdditions = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stageAdditions.hide();
			//stage.close();
			//Stage primaryStage1 = new Stage();
			//RestaurantController RC = new RestaurantController(RestaurantController.restaurant);
			try {
				//RC.start(primaryStage1);
				RestaurantController.stageRestaurant.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	//show users order
	/**
	 * Submit order to go to show order Controller
	 */
	EventHandler<ActionEvent> ShowOrder = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			 stageAdditions = (Stage) ((Node) e.getSource()).getScene().getWindow();
			//stage.close();
			 stageAdditions.hide();
			Stage primaryStage1 = new Stage();
			ShowOrderController SOC = new ShowOrderController();
			try {
				SOC.start(primaryStage1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
}
