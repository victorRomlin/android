package com.example.martin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }


    public void onButtonClick(View V){

        if (V.getId() == R.id.login) {
            EditText editTextPassword = ((EditText) findViewById(R.id.editText1));
            EditText editTextUsername = ((EditText) findViewById(R.id.editText2));
            String username = editTextPassword.getText().toString();
            String password = editTextUsername.getText().toString();

            System.out.println(username);
            System.out.println(password);

            if (username.equals(password)){
                System.out.println("yes!!!");
                Intent r = new Intent(MainActivity.this, loginactivity.class);
                r.putExtra("location", username);
                startActivity(r);
                Toast.makeText(getApplicationContext(), "Redirecting..", Toast.LENGTH_SHORT).show();
            }
            else{
                System.out.println("nooooooo");
                Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                editTextPassword.setText("");
                editTextUsername.setText("");
            }

        }
    }





/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
