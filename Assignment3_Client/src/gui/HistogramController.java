package gui;

import javafx.application.Application;  
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import common.OrdersReport;
import common.HistogramCEO;
import common.InComeReport;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.ArrayList;

public class HistogramController extends Application {
	static ArrayList<HistogramCEO> HistogramArray;
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;

		Label labelInfo = new Label();
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setCategoryGap(170);
		barChart.setBarGap(0);

		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		series1.setName("InCome");
		series2.setName("Sold");
		labelInfo.setText("Histogram report");
		xAxis.setLabel("Restaurants");
		yAxis.setLabel("Numbers");
		for (int i = 0; i < HistogramArray.size(); i++) {
			series1.getData().add(new XYChart.Data(HistogramArray.get(i).getRestaurantName(), HistogramArray.get(i).getInCome()));
			series2.getData().add(new XYChart.Data(HistogramArray.get(i).getRestaurantName(), HistogramArray.get(i).getSold()));
			
		}
		
		barChart.getData().addAll(series1,series2);
		barChart.setBarGap(3);
		barChart.setCategoryGap(20);
		ScrollPane HistogranPane = new ScrollPane(barChart);
		HistogranPane.setContent(barChart);
		HistogranPane.setFitToWidth(true); // set content width to viewport width
		HistogranPane.setFitToHeight(true);
		HistogranPane.setPannable(true); // allow scrolling via mouse dragging
		HistogranPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		HistogranPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		HistogranPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		VBox vBox = new VBox();
		vBox.getChildren().addAll(labelInfo, HistogranPane);
		
		
	
		
		StackPane root = new StackPane();
		root.getChildren().add(vBox);
		Scene scene = new Scene(root, 800, 450);
		primaryStage.setTitle("Histogram");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
