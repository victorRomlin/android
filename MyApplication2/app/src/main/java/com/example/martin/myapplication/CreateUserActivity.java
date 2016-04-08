package com.example.martin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Martin on 2016-04-07.
 */
public class CreateUserActivity extends Activity {
    String email;
    String username;
    String password;
    String confirmedpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);
    }


    public void onButtonClick(View V){
        if (V.getId() == R.id.button3){

            email = ((EditText) findViewById(R.id.textemail)).getText().toString();
            username = ((EditText) findViewById(R.id.textusername)).getText().toString();
            password = ((EditText) findViewById(R.id.textpassword)).getText().toString();
            confirmedpassword = ((EditText) findViewById(R.id.textconfirmpassword)).getText().toString();

            if(password.equals(confirmedpassword)){
                // skicka data till server HÄR (anropa Connect() ).
            }
    }
    }

}
