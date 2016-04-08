package com.example.martin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public void onButtonClick(View V){
        if (V.getId() == R.id.button3){

            email = ((EditText) findViewById(R.id.textemail)).getText().toString();
            username = ((EditText) findViewById(R.id.textusername)).getText().toString();
            EditText editTextPwd = ((EditText) findViewById(R.id.textpassword));
            password = editTextPwd.getText().toString();
            EditText editTextPwdCon = ((EditText) findViewById(R.id.textconfirmpassword));
            confirmedpassword = editTextPwdCon.getText().toString();

            if(password.equals(confirmedpassword)){
                System.out.println("Equal");
                // skicka data till server HÄR (anropa Connect() ).
            }

            else{
                Toast.makeText(getApplicationContext(), "Password does not match the confirm password.", Toast.LENGTH_LONG).show();
                editTextPwd.setText("");
                editTextPwdCon.setText("");
            }
    }
    }

}
