package com.example.martin.myapplication;

import android.app.Activity;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnTalkToDBFinish {
    String username;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View V){

        if (V.getId() == R.id.login) {
            EditText editTextPassword = ((EditText) findViewById(R.id.editText1));
            EditText editTextUsername = ((EditText) findViewById(R.id.editText2));
            username = editTextPassword.getText().toString();
            password = editTextUsername.getText().toString();
            String email ="";

            System.out.println(username);
            System.out.println(password);

            runDBtask();
        }
        else if (V.getId() == R.id.button2){
            Intent r = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivity(r);
        }
    }
    private void runDBtask(){
        talkToDBTask task = new talkToDBTask(this);
        task.setUsername(username);
        task.setPwd(password);
        task.setRequestType(1);
        task.execute();
    }
    private void setText(boolean success){
        if(success){
            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
        }
        else{
            EditText editTextPassword = ((EditText) findViewById(R.id.editText1));
            EditText editTextUsername = ((EditText) findViewById(R.id.editText2));
            editTextPassword.setText("");
            editTextUsername.setText("");
            Toast.makeText(getApplicationContext(), "Not a valid user", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskCompleted() {
        Intent r = new Intent(MainActivity.this, Homepage.class);
        r.putExtra("user", username);
        r.putExtra("password", password);
        startActivity(r);
    }

    @Override
    public void onTaskFailed() {
        System.out.println("fail");
    }
}
