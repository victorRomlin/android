package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.example.martin.myapplication.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


/**
 * Created by Sara on 2016-04-19.
 */
public class Statistics extends Activity {
    String[] keys;
    String[] values;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);


        //Här nedanför hämtas värden från föregående aktivitet
        Intent i = getIntent();
        int y1 = i.getIntExtra("y1", 0);
        int y2 = i.getIntExtra("y2", 0);
        int y3 = i.getIntExtra("y3", 0);
        keys = i.getStringArrayExtra("keys");
        values = i.getStringArrayExtra("values");


        //Här skapar vi våran graf, lämpligt nog döpt till "graph"
        GraphView graph = (GraphView) findViewById(R.id.graph);

        //Stil på grafen, alltså själva grafen och inte linjerna
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("x-axel");
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxY(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setBackgroundColor(Color.parseColor("#4DFFFFFF"));
        graph.setTitle("En graf");
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        graph.getGridLabelRenderer().setNumVerticalLabels(6);


        //värden för ena serien
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, y1),
                new DataPoint(1, y2),
                new DataPoint(2, y3),
                new DataPoint(3, 0),
                new DataPoint(4, 0),
                new DataPoint(5, 0),
                new DataPoint(6, 0),
                new DataPoint(7, 0),
                new DataPoint(8, 0),
                new DataPoint(9, 0),
                new DataPoint(10, 0),
        });
        series.setColor(Color.parseColor("#944155"));

        //värden för andra serien
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 10),
                new DataPoint(1, 50),
                new DataPoint(2, 30),
                new DataPoint(3, 80),
                new DataPoint(4, 40),
                new DataPoint(5, 90),
                new DataPoint(6, 100),
                new DataPoint(7, 70),
                new DataPoint(8, 80),
                new DataPoint(9, 90),
                new DataPoint(10, 80),

        });

        series2.setColor(Color.parseColor("#A17947"));

        // alltid manuellt angivla värden och skalor för den 2:a axeln
        graph.getSecondScale().setMinY(-10);
        graph.getSecondScale().setMaxY(10);
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.parseColor("#A98253"));

        //slutligen, lägg till serierna i grafen
        //graph.getSecondScale().addSeries(receivedData(1));
        graph.addSeries(receivedData(1));
    }

    public LineGraphSeries<DataPoint> receivedData(int key){
        LineGraphSeries<DataPoint> returnDataSeries = new LineGraphSeries<>();
        for(int i = 0; i < keys.length; i++){
            int tempKey = Integer.parseInt(keys[i]);
            int tempValue = Integer.parseInt(values[i]);
            if(tempKey == key){
                System.out.println(values[i]);
                returnDataSeries.appendData(new DataPoint(i,tempValue),true,100);
            }
        }
        return returnDataSeries;
    }
}
