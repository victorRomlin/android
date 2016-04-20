package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;

/**
 * Created by Martin on 2016-04-05.
 * ich han små vänner
 */

public class DataInput extends Activity {

    //DET git här ska bort
    int BubbaFett;

    NumberPicker np;
    ImageButton happyB;
    ImageButton sadB;
    int hoursSleep;
    int mood;
    private Switch mySwitch;
    boolean Tswich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datainput);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        Intent intent = getIntent();


            np = (NumberPicker) findViewById(R.id.numberPicker);

            np.setMinValue(0);
            np.setMaxValue(15);
            np.setWrapSelectorWheel(false);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    hoursSleep = newVal;
                }
            });


        mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setChecked(false);
        Tswich = false;


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    //Mood: Good
                    mood = -5;
                    break;
            case R.id.radioButton2:
                if (checked)
                    // Mood: OK
                    mood = 0;
                    break;
            case R.id.radioButton3:
                if (checked)
                    // Mood: Bad
                    mood = 5;
                    break;
        }
    }

    public void onSwitch(View v){


        if(Tswich == false){
            Tswich = true;
            }
        else{
            Tswich = false;
        }
    }

    public void onButtonClicked(View V){
        System.out.println("Timmars sömn: "+hoursSleep);
        System.out.println("Tränat: "+Tswich);
        System.out.println("Humör: "+mood);


    }


}



