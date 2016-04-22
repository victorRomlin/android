package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * Created by Martin on 2016-04-05.
 * ich han små vänner
 */

public class DataInput extends Activity implements OnTalkToDBFinish {

    //DET git här ska bort
    int BubbaFett;
    String username;
    String password;
    NumberPicker np;
    ImageButton happyB;
    ImageButton sadB;
    int hoursSleep;
    int mood;
    int work;
    private Switch mySwitch;
    boolean Tswich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datainput);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");



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
                    //Mood: good
                    mood = 5;
                    break;
            case R.id.radioButton2:
                if (checked)
                    // Mood: OK
                    mood = 0;
                    break;
            case R.id.radioButton3:
                if (checked)
                    // Mood: Bad
                    mood = -5;
                    break;
        }
    }

    public void onSwitch(View v){


        if(Tswich == false){
            Tswich = true;
            work = 1;
            }
        else{
            Tswich = false;
            work = 0;
        }
    }

    public void onButtonClicked(View V){
        System.out.println("Timmars sömn: "+hoursSleep);
        System.out.println("Tränat: "+Tswich);
        System.out.println("Humör: "+mood);
        System.out.println(Integer.toString(hoursSleep));

        String[] values = {Integer.toString(hoursSleep),Integer.toString(work),Integer.toString(mood)};
        String[] keys = {"1","2","3"};
        System.out.println(Arrays.toString(values));
        System.out.println(keys);
        System.out.println(username);
        System.out.println(password);
        runDBtask(values,keys);


    }
    private void runDBtask(String[] values, String[] keys){
        talkToDBTask task = new talkToDBTask(this);
        task.setUsername(username);
        task.setPwd(password);
        task.setKeys(keys);
        task.setValues(values);
        task.setRequestType(3);
        task.execute();
    }
    @Override
    public void onTaskCompleted() {
        Toast.makeText(getApplicationContext(), "Variables added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskFailed() {
        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        System.out.println("shit");
    }
}



