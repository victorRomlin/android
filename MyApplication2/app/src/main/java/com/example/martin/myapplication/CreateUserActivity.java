package com.example.martin.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
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
    public void attemptAddUser(){

    }
    private void onUserAllreadyExists(){

    }
    private static boolean passwordValid(String password){
        return password.length() > 4;
    }
    private static class SaveAccountTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

}
