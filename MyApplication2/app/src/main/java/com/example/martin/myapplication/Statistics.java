package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.example.martin.myapplication.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;


/**
 * Created by Sara on 2016-04-19.
 */
public class Statistics extends Activity {
    String[] keys;
    String[] values;
    GraphView graph;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        runGraph();
    }

    private LineGraphSeries<DataPoint> receivedDataLine(int key){
        LineGraphSeries<DataPoint> returnDataSeries = new LineGraphSeries<>();
        int j = 0;
        for(int i = 0; i < keys.length; i++){
            int tempKey = Integer.parseInt(keys[i]);
            int tempValue = Integer.parseInt(values[i]);
            if(tempKey == key){
                System.out.println(values[i]);
                System.out.println("i= "+i);
                returnDataSeries.appendData(new DataPoint(j,tempValue),false,100);
                System.out.println("i= "+i);
                j++;
            }
        }
        return returnDataSeries;
    }
    private void runGraph(){
        //Här nedanför hämtas värden från föregående aktivitet
        Intent i = getIntent();

        keys = i.getStringArrayExtra("keys");
        values = i.getStringArrayExtra("values");


        //Här skapar vi våran graf, lämpligt nog döpt till "graph"
        graph = (GraphView) findViewById(R.id.graph);
        setupGraph(graph);

        //PointsGraphSeries<DataPoint> seriesPoints = receivedDataPoints(1);
        LineGraphSeries<DataPoint> serieslineSleep = receivedDataLine(1);
        LineGraphSeries<DataPoint> serieslineMood = receivedDataLine(3);

        serieslineMood.setColor(Color.rgb(0,153,51));
        serieslineSleep.setColor(Color.BLUE);


        //Stil på grafen, alltså själva grafen och inte linjerna

        serieslineSleep.setTitle("timmars sömn");
        serieslineMood.setTitle("humör");

        serieslineMood.setDrawBackground(true);
        serieslineMood.setBackgroundColor(Color.argb(50,204,255,204));
        serieslineSleep.setDrawBackground(true);
        serieslineSleep.setBackgroundColor(Color.argb(70,255,255,255));


        graph.addSeries(serieslineSleep);
        graph.getSecondScale().addSeries(serieslineMood);
    }
    private PointsGraphSeries<DataPoint> receivedDataPoints(int key){
        PointsGraphSeries<DataPoint> returnDataSeries = new PointsGraphSeries<>();
        int j = 0;
        for(int k = 0; k < keys.length; k++){
            int tempKey = Integer.parseInt(keys[k]);
            int tempValue = Integer.parseInt(values[k]);
            if(tempKey == key){
                System.out.println(values[k]);
                returnDataSeries.appendData(new DataPoint(j,tempValue),false,100);
                System.out.println("k= "+k);
                j++;
            }
        }
        return returnDataSeries;
    }

    private void setupGraph(GraphView graph){
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("x-axel");

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(17);
        graph.getViewport().setMinX(0);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setBackgroundColor(Color.parseColor("#4DFFFFFF"));
        graph.getSecondScale().setMinY(-5);
        graph.getSecondScale().setMaxY(5);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.setTitle("En graf");
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        graph.getGridLabelRenderer().setNumVerticalLabels(6);
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.rgb(0,153,51));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLUE);
    }
}
