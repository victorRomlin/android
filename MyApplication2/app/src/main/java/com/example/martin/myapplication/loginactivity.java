package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Martin on 2016-04-05.
 */

public class loginactivity extends Activity {

    NumberPicker np;
    ImageButton happyB;
    ImageButton sadB;
    int hoursSleep;
    private Switch mySwitch;
    boolean Tswich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        TextView textView1 = (TextView) findViewById(R.id.textView1);

        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        textView1.setText("Välkommen "+str+"!");

            np = (NumberPicker) findViewById(R.id.numberPicker);

            np.setMinValue(0);
            np.setMaxValue(15);
            np.setWrapSelectorWheel(false);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    hoursSleep = newVal;
                    System.out.println(hoursSleep);
                }
            });

        happyB = (ImageButton)findViewById(R.id.imageButton);
        happyB.setOnClickListener(imgButtonHandler);
        sadB = (ImageButton)findViewById(R.id.imageButton2);
        sadB.setOnClickListener(imgButtonHandler);

        mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setChecked(false);
        Tswich = false;


    }


    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            System.out.println("happy");
        }
    };


    public void onSwitch(View v){


        if(Tswich == false){
            Tswich = true;
            System.out.println(Tswich);
            }
        else{
            Tswich = false;
            System.out.println(Tswich);
        }
    }

}
