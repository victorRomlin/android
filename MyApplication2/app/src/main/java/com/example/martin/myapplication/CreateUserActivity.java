package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "User created!", Toast.LENGTH_SHORT).show();
                String result = data.getStringExtra("result");
                System.out.println(result);
                Intent r = new Intent(CreateUserActivity.this, MainActivity.class);
                startActivity(r);
            }
            if(resultCode == Activity.RESULT_CANCELED){
                onUserAlreadyExists();
            }
        }
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                onUserAlreadyExists();
            }
            if(resultCode == Activity.RESULT_CANCELED){
                attemptAddUser();
            }
        }
    }

    public void onButtonClick(View V){
        if (V.getId() == R.id.button3){

            getFieldValues();

            if (password.length()>4){
                if(passwordValid()){
                    checkUserExists();
                }
                else{
                    clearFields();
                    Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                clearFields();
                Toast.makeText(getApplicationContext(), "Password must be larger than four characters", Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void getFieldValues(){
        email = ((EditText) findViewById(R.id.textemail)).getText().toString();
        username = ((EditText) findViewById(R.id.textusername)).getText().toString();
        password = ((EditText) findViewById(R.id.textpassword)).getText().toString();
        confirmedpassword = ((EditText) findViewById(R.id.textconfirmpassword)).getText().toString();
    }
    public void clearFields(){
        EditText editTextPassword = ((EditText) findViewById(R.id.textpassword));
        EditText editTextconfirmedpassword = ((EditText) findViewById(R.id.textconfirmpassword));
        editTextPassword.setText("");
        editTextconfirmedpassword.setText("");
    }
    public void attemptAddUser(){

        Intent tent = new Intent(CreateUserActivity.this, TalkToDBActivity.class);
        tent.putExtra("username",username);
        tent.putExtra("password",password);
        tent.putExtra("email",email);
        int requestCode = 2;
        tent.putExtra("requestCode", requestCode);
        startActivityForResult(tent, 2);

    }
    public void checkUserExists(){

        Intent tent = new Intent(CreateUserActivity.this, TalkToDBActivity.class);
        tent.putExtra("username",username);
        tent.putExtra("password",password);
        int requestCode = 1;
        tent.putExtra("requestCode", requestCode);
        startActivityForResult(tent, 1);

    }

    private void onUserAlreadyExists(){
        System.out.println("FAAAAAAAIL");
        EditText editTextPassword = ((EditText) findViewById(R.id.textpassword));
        EditText editTextUsername = ((EditText) findViewById(R.id.textusername));
        EditText editTextconfirmedpassword = ((EditText) findViewById(R.id.textconfirmpassword));
        Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
        editTextPassword.setText("");
        editTextUsername.setText("");
        editTextconfirmedpassword.setText("");
    }
    private boolean passwordValid(){
        if(password == confirmedpassword) {
            return true;
        }
        return false;
    }

}
