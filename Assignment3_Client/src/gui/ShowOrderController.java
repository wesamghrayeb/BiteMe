package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.stage.Stage;

/**
 * Show All selected items and additions to the user
 * Shows total price of picked items from menu
 * user can go back and change the items he chose
 * @author asem
 *
 */
public class ShowOrderController {

	/**
	 * build the gui of selected items and additions
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setResizable(false);
		
		// Creating an image
		Image image = new Image("icons/restaurant.png");
		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(300);
		imageView.setY(18);

		// setting the fit height and width of the image view
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		// header of the page

		Label label = new Label(RestaurantController.restaurant.getResturaunt_Name());
		label.setTextFill(Color.web("#eb8b00"));
		label.setFont(new Font("Arial", 50));
		label.setTranslateX(350);
		label.setTranslateY(18);

		
		Label NavigationLbl = new Label("LogIn->Home Page->Resturant's Menu->Resturant Menu->Additions->Order");
		NavigationLbl.setTextFill(Color.web("#FFFFFF"));
		NavigationLbl.setFont(new Font("Arial", 12));
		NavigationLbl.setTranslateX(5);
		NavigationLbl.setTranslateY(5);
		
		
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// set header of page
		Label Additionslbl = new Label("Your Order:");
		Additionslbl.setTextFill(Color.web("#eb8b00"));
		Additionslbl.setFont(new Font("Arial", 25));
		Additionslbl.setTranslateX(10);
		Additionslbl.setTranslateY(90);
		
		Font font = Font.font("Arial", 23);
		Font font2 = Font.font("Arial", 20);

		// view users order total price
		Label Sum = new Label("Total Price: " + String.valueOf(AdditionsController.OverAllSum) + "$");
		Sum.setTextFill(Color.web("#eb8b00"));
		Sum.setFont(new Font("Arial", 25));
		Sum.setTranslateX(560);
		Sum.setTranslateY(90);
		AnchorPane pane = new AnchorPane();
		pane.setStyle("-fx-background-color: #555555;");
		pane.getChildren().addAll(Sum, Additionslbl);

		HBox hbox = new HBox(10);
		hbox.setTranslateX(10);
		hbox.setTranslateY(140);

		// show salads picked
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
	

		// show mainDishes picked
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
		

		// show desserts picked
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
	
		// show drinks picked
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
		

		// set meals font and color to gui
		for (int j = 0, salads = 1, main = 1, dessert = 1, drinks = 1; j < AdditionsController.arlist
				.size(); j++) {

			Label lbl = new Label();
			lbl.setFont(font);
			lbl.setTextFill(Color.web("#eb8b00"));
			if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Salad")) {
				lbl.setTranslateY(5);
				lbl.setText(" "+salads + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				saladBox.getChildren().addAll(lbl);
				salads++;
			} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("MainDish")) {
				lbl.setText(" "+main + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				mainDishBox.getChildren().add(lbl);
				main++;
			} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Dessert")) {
				lbl.setText(" "+dessert + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				dessertBox.getChildren().add(lbl);
				dessert++;
			} else {
				lbl.setText(" "+drinks + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				drinkBox.getChildren().add(lbl);
				drinks++;
			}

			// set additions font and color to gui
			CheckBox[] c = AdditionsController.arlist.get(j).getCB();
			for (int k = 0; k < c.length; k++) {
				Label lbl2 = new Label("-"+c[k].getText());
				if (c[k].isSelected()) {
					lbl2.setFont(font2);
					lbl2.setTextFill(Color.web("black"));
					lbl2.setTranslateX(5);
					if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Salad")) {
						saladBox.getChildren().add(lbl2);
					} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("MainDish")) {
						mainDishBox.getChildren().add(lbl2);
					} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Dessert")) {
						dessertBox.getChildren().add(lbl2);
					} else {
						drinkBox.getChildren().add(lbl2);
					}
				}
			}

		}

		// add all label to gui
		pane.getChildren().addAll(imageView, label,NavigationLbl);
		tabPane.setTranslateX(10);
		tabPane.setTranslateY(120);
		tabPane.setPrefSize(780, 380);
		Tab tab1 = new Tab("Salad", saladPane);
		Tab tab2 = new Tab("MainDish", mainDishPane);
		Tab tab3 = new Tab("Dessert", dessertPane);
		Tab tab4 = new Tab("Drink", drinkPane);
		tab1.setGraphic(RestaurantController.buildImage("icons/salad.png"));
		tab2.setGraphic(RestaurantController.buildImage("icons/mainDish.png"));
		tab3.setGraphic(RestaurantController.buildImage("icons/Dessert.png"));
		tab4.setGraphic(RestaurantController.buildImage("icons/drink.png"));

		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		pane.getChildren().add(tabPane);

		Button submitBtn = new Button("Delivery/Pick-Up");
		Button backBtn = new Button("Back");
		submitBtn.setTranslateX(580);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(30);
		backBtn.setTranslateY(525);
		
		submitBtn.setOnAction(ChooseTypeOfOrderPage);
		backBtn.setOnAction(backToAdditionsController);
		
		pane.getChildren().addAll(submitBtn, backBtn);

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

		primaryStage.setTitle("Login Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 *  go to delivery/pickup options page
	 */
	EventHandler<ActionEvent> ChooseTypeOfOrderPage = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {

			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			TypeOfOrderController TOOC = new TypeOfOrderController();
			try {
				TOOC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * go back to additions page
	 */
	EventHandler<ActionEvent> backToAdditionsController = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {

			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			AdditionsController AC = new AdditionsController();
			try {

				AdditionsController.stageAdditions.show();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
}
