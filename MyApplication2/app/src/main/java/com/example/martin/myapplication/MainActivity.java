package com.example.martin.myapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String username;

   //private GoogleApiClient client;

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
            username = editTextPassword.getText().toString();
            String password = editTextUsername.getText().toString();

            System.out.println(username);
            System.out.println(password);

            connect(username,password);

           /* if (username.equals(password)){
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
            }*/

        }
    }
    private class PostVariableTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            String output = "";
            String answer ="";
            for(String url: params){
                response = getURLResponse(url);
            }
            try {
                output = ConvertJson(response.toString(),"Svar");
                System.out.println(output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (output.contains("1")){
                System.out.println("its alive");
                output = "login Success";
            }
            else{
                output = "login Fail";
            }
            return output;
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            //setText(result);
            if(result.contains("login Success")){
                Intent r = new Intent(MainActivity.this, loginactivity.class);
                r.putExtra("location", username);
                startActivity(r);
                Toast.makeText(getApplicationContext(), "Redirecting..", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                EditText editTextPassword = ((EditText) findViewById(R.id.editText1));
                EditText editTextUsername = ((EditText) findViewById(R.id.editText2));
                editTextPassword.setText("");
                editTextUsername.setText("");
            }
        }

    }
    private String getURLResponse(String url){
        HttpURLConnection connection = null;
        String response = "";
        try {
            URL mainURL = new URL(url);
            connection = (HttpURLConnection) mainURL.openConnection();

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            if (connection == null) System.out.println("no connection");
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response += line;
                response += '\r';
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;


    }
    private String ConvertJson(String response, String key) throws JSONException {
        JSONArray array = new JSONArray(response);
        response ="";
        System.out.println(array.length());
        for (int i = 0; i < array.length(); i++){
            JSONObject json = array.getJSONObject(i);
            response += json.getString(key);
            response += '\t';
        }
        return response;
    }

    private void setText(String inputText){
        if(inputText == "true"){

        }
        TextView text = (TextView) findViewById(R.id.textView);
        if (text != null) {
            text.setText(inputText);
        }
    }

    private String SetupURLValueSend(String program, String[] values, String[] keys,String username,String password){

        String ipadress = "http://130.238.15.239/lvl2/";
        String data = "";
        try {
            data = ipadress+program+URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            if(values.length >0) {
                for (int i = 0; i < values.length; i++) {
                    data += "&testdata2[";
                    if (keys.length>0){
                        data += URLEncoder.encode(keys[i],"UTF-8");
                    }
                    data +="]=" + URLEncoder.encode(values[i], "UTF-8");
                }
            }

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;

    }

    private void setupConnection(String[] url){
        PostVariableTask task = new PostVariableTask();
        task.execute(url);
    }

    private void connect(String user, String pwd) {
        //String program = "phptest.php?";
        //String program2 = "insertion.php?";
        //String testData = "http://pub.jamaica-inn.net/fpdb/api.php?username=jorass&password=jorass&action=iou_get";

        //hardcoded test values.
        String[] values = {"43","22","123","100","12","1"};
        String[] keys = {"a","b","c","d","e","f"};
        String[] empty1 = {};
        String[] empty2 ={};
        String username = user;
        String password = pwd;
        setText("söker...");
        String testdata = SetupURLValueSend("createUser.php?",empty1,empty2,username,password);
        //String testdata2 = SetupURLValueSend("insertion.php?", values, keys,username,password);
        System.out.println(testdata);
        setupConnection(new String[]{testdata});
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
