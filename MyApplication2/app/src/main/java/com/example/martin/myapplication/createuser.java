package com.example.martin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Martin on 2016-04-07.
 */
public class createuser extends Activity {
    String email;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);
    }


    public void onButtonClick(View V){
        if (V.getId() == R.id.button3){
            System.out.println("Registrera");

            email = ((EditText) findViewById(R.id.textemail)).getText().toString();
            username = ((EditText) findViewById(R.id.textusername)).getText().toString();
            password = ((EditText) findViewById(R.id.textpassword)).getText().toString();
            System.out.println(email);
            System.out.println(username);
            System.out.println(password);
    }
    }

}
