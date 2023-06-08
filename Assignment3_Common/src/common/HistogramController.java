package common;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Class for creating BarChart for InCome report
 * @author Bashar Bashir 
 *
 */
public class HistogramController {
	
	/**
	 * ArrayList includes InCome report
	 */
	public static ArrayList<InComeReport> InComeArray;
	/**
	 * variable saving InCome for everyday
	 */
	private int InComePerDay=0;
	
	/**
	 * Setting the values in JFreeChart
	 */
	public static JFreeChart jFreeChart;
	
	/**
	 * Setting the JFreeChart in the frame
	 */
	public static ChartFrame frame;

	/**
	 * @param primaryStage1
	 * @throws Exception
	 * Creating DefaultCategoryDataset
	 * putting the values in the DefaultCategoryDataset
	 * Converting the values and sets them in frame
	 */
	public void start(Stage primaryStage1) throws Exception {
	   DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
	   for (int i = 1; i < 31; i++) {
			InComePerDay=0; 
			for(int j=0;j<InComeArray.size();j++)
				if(InComeArray.get(j).getDay()==i)
					InComePerDay+=InComeArray.get(j).getIncome();
			defaultCategoryDataset.setValue(InComePerDay, "Money",String.valueOf(i));
		}
	   
       jFreeChart = ChartFactory.createBarChart("InCome Report","Days","Money",defaultCategoryDataset,PlotOrientation.VERTICAL, false, false, false); 
       CategoryItemRenderer renderer = ((CategoryPlot)jFreeChart.getPlot()).getRenderer();
       renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
       renderer.setBaseItemLabelsVisible(true);
       ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, 
               TextAnchor.TOP_CENTER);
       renderer.setBasePositiveItemLabelPosition(position);
       frame = new ChartFrame("InCome", jFreeChart);
       frame.pack();
       frame.setVisible(true);
	} 
	
}
