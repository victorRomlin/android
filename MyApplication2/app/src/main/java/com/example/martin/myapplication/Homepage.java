package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Martin on 2016-04-05.
 * ich han små vänner
 */

public class Homepage extends Activity {

    String user;
    String pwd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        TextView textView8 = (TextView) findViewById(R.id.textView8);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        pwd = intent.getStringExtra("password");
        textView8.setText("Välkommen "+user+"!");
    }

    public void onButtonClick(View V){
        //dagens input
        if (V.getId() == R.id.button6){
            Intent r = new Intent(Homepage.this, DataInput.class);
            r.putExtra("username", user);
            r.putExtra("password", pwd);
            startActivity(r);
        }
        // statistics
        if (V.getId() == R.id.button5){
            Intent r = new Intent(Homepage.this, Statistics.class);
            r.putExtra("username", user);
            r.putExtra("password", pwd);

            startActivity(r);
        }
    }





}



