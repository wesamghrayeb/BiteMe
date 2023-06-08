package common;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
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
import java.util.ArrayList;

import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.ui.TextAnchor;

/**
 * @author Bashar Bashir
 *
 */
public class HistogramForCEO extends Application {

	/**
	 * ArrayList includes InCome and sold items for specific quarter
	 */
	public static ArrayList<HistogramCEO> HistogramArray;

	/**
	 * The chose quarter for creating the Histogram
	 */
	public static String Month;

	/**
	 * Chose year for report
	 */
	public static int year;

	/**
	 * saving the convert of the quarter to integer month
	 */
	int month = 0;
	Stage primaryStage;

	/**
	 * Creating AreaChart Setting title for the Histogram setting the values into
	 * the AreaChart Set the AreaChart into the Scene Pick the scene
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		if (Month.equals("1,2,3")) {
			month = 1;
		}
		if (Month.equals("4,5,6")) {
			month = 4;
		}
		if (Month.equals("7,8,9")) {
			month = 7;
		}
		if (Month.equals("10,11,12")) {
			month = 10;
		}
		primaryStage.setTitle("Histogram");
		final NumberAxis xAxis = new NumberAxis(month, month + 2, 1);
		final NumberAxis yAxis = new NumberAxis();
		final AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);
		if (HistogramArray.size() > 0) {
			ac.setTitle("Histogram for " + HistogramArray.get(0).getRestaurantName() + "\n" + "(Months:" + Month
					+ ") Year(" + year + ")");
			XYChart.Series seriesInCome = new XYChart.Series();
			seriesInCome.setName("InCome");
			XYChart.Series seriesSales = new XYChart.Series();
			seriesSales.setName("Sold");
			for (int i = 0; i < HistogramArray.size(); i++) {
				seriesInCome.getData().add(new XYChart.Data(month, HistogramArray.get(i).getInCome()));
				seriesSales.getData().add(new XYChart.Data(month, HistogramArray.get(i).getSold()));
				month++;
			}
			Scene scene = new Scene(ac, 800, 600);
			ac.getData().addAll(seriesInCome, seriesSales);
			primaryStage.setScene(scene);
			primaryStage.show();
		}

	}
}