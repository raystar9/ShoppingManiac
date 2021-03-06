package com.example.kcci.shoppingmaniac;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.kcci.shoppingmaniac.database.Database;
import com.example.kcci.shoppingmaniac.database.PriceHistory;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

import static com.example.kcci.shoppingmaniac.MainActivity.EXTRA_ID;

/**
 * Created by CHJ on 2017-07-07.
 */

public class LineChartActivity extends AppCompatActivity {

    // 막대그래프의 가로축

    private String[] mMonth = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private GraphicalView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_line_chart);
        String id = getIntent().getStringExtra(EXTRA_ID);

        final Database database = new Database();
        database.requestPriceHistory(id, new Database.LoadCompleteListener() {
            @Override
            public void onLoadComplete() {
//                database.get
                drawChart(database.getPriceHistoryList());
            }
        });


    }

    private void drawChart(ArrayList<PriceHistory> historyList) {

        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
//        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        // Creating an  XYSeries for Income

        XYSeries incomeSeries = new XYSeries("가격추이");

        // Creating an  XYSeries for Expense

//        XYSeries expenseSeries = new XYSeries("지출");


        // Adding data to Income and Expense Series


        for (int i = 0; i < historyList.size(); i++) {
            incomeSeries.add(x[i],
                    Double.parseDouble(historyList.get(i).getPrice()));

//            expenseSeries.add(x[i],expense[i]);

        }


        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset

        dataset.addSeries(incomeSeries);

        // Adding Expense Series to dataset

//        dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries

        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.RED);
        incomeRenderer.setChartValuesTextSize(30);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(5);
        incomeRenderer.setDisplayChartValues(true);
        incomeRenderer.setAnnotationsTextSize(15);
        incomeRenderer.setChartValuesSpacing(30);


        // Creating XYSeriesRenderer to customize expenseSeries

//        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
//        expenseRenderer.setColor(Color.RED);
//        expenseRenderer.setPointStyle(PointStyle.CIRCLE);
//        expenseRenderer.setFillPoints(true);
//        expenseRenderer.setLineWidth(2);
//        expenseRenderer.setDisplayChartValues(true);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("   ----    상품가격추이    ----   ");
        multiRenderer.setXTitle("날  짜");
        multiRenderer.setYTitle("금  액");
        multiRenderer.setYAxisMax(2000);
        multiRenderer.setYAxisMin(500);
//        multiRenderer.setZoomButtonsVisible(true);

        for (int i = 0; i < historyList.size(); i++) {

            multiRenderer.addXTextLabel(i + 1, mMonth[i]);

        }


//         Adding incomeRenderer and expenseRenderer to multipleRenderer
//
//         Note: The order of adding dataseries to dataset and renderers to multipleRenderer
//
//         should be same

        multiRenderer.addSeriesRenderer(incomeRenderer);
//        multiRenderer.addSeriesRenderer(expenseRenderer);


        // Creating an intent to plot line chart using dataset and multipleRenderer
        // Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);


        // Start Activity

        //startActivity(intent);


        if (mChartView == null) {

            LinearLayout layout = (LinearLayout) findViewById(R.id.chart_line);

            mChartView = ChartFactory.getLineChartView(this, dataset, multiRenderer);

            multiRenderer.setClickEnabled(true);

            multiRenderer.setSelectableBuffer(10);

            layout.addView(mChartView,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.FILL_PARENT));

        } else {

            mChartView.repaint();

        }

    }

}