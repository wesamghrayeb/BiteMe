package common;

import java.beans.EventHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.Group;

/**
 *  Class for creating Pie Chart for the order and Performance report
 * @author Bashar Bashir
 */
public class ReportCreator extends Application {
	/**
	 * Array list for order report
	 */
	public static ArrayList<OrdersReport> OrderArray;
	/**
	 * ArrayList for performence report
	 */
	public static ArrayList<PerformenceReport> Perf;
	/**
	 * Report type
	 */
	public static String Type;
	public static JFreeChart chart;
	public static ChartFrame frame;
 
	/**
	 *	Creating pie chart from the arraylist values
	 */
	@Override
	public void start(Stage stage) {
		if (Type.equals("Order")) {
			stage.setTitle("Order Report");
			stage.setWidth(500);
			stage.setHeight(500);
			int MainDish = 0;
			int Salad = 0;
			int Drink = 0;
			int Dessert = 0;
			for (int i = 0; i < OrderArray.size(); i++) {
				if (OrderArray.get(i).getCatalog().equals("MainDish"))
					MainDish+=OrderArray.get(i).getSold();
				if (OrderArray.get(i).getCatalog().equals("Salad"))
					Salad+=OrderArray.get(i).getSold();
				if (OrderArray.get(i).getCatalog().equals("Drink"))
					Drink+=OrderArray.get(i).getSold();
				if (OrderArray.get(i).getCatalog().equals("Dessert"))
					Dessert+=OrderArray.get(i).getSold();
			}
			DefaultPieDataset dataset = new DefaultPieDataset();
			dataset.setValue("MainDish", MainDish);
			dataset.setValue("Salad", Salad);
			dataset.setValue("Drink", Drink);
			dataset.setValue("Dessert", Dessert);
			chart = ChartFactory.createPieChart("Orders Report", dataset, true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			 PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
			            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
			        plot.setLabelGenerator(gen);
			frame = new ChartFrame("Orders", chart);
			frame.pack();
			frame.setVisible(true);
		}
		if (Type.equals("Performencereports")) {
			stage.setTitle("Performence Report");
			stage.setWidth(500);
			stage.setHeight(500);
			int Late =0 ;
			int Early = 0;
			for(int i=0;i<Perf.size();i++) {
				Late += Perf.get(i).getLate();
				Early += Perf.get(i).getEarly();
			}
			DefaultPieDataset dataset = new DefaultPieDataset();
			dataset.setValue("Late", Late);
			dataset.setValue("Early", Early);
			chart = ChartFactory.createPieChart("Performence Report", dataset, true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			 PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
			            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
			        plot.setLabelGenerator(gen);
			frame = new ChartFrame("Performence", chart);
			frame.pack();
			frame.setVisible(true);
		}
	}

}