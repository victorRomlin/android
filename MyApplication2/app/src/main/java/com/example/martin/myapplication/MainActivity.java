package com.example.martin.myapplication;

import android.os.AsyncTask;
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
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

            login(username,password);

        }

        else if (V.getId() == R.id.button2){
            Intent r = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivity(r);
        }
    }
    private class LoginTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            String output = "";
            //String answer ="";

            for(String url: params){
                response = getURLResponse(url);
            }
            try {

                JSONObject object = new JSONObject(response.toString());
                   output = checkCorrectUser(object);

                System.out.println(output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return output;
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            //setText(result);
            if(result.contains("TRUE")){
                Intent r = new Intent(MainActivity.this, LoginActivity.class);
                r.putExtra("location", username);
                startActivity(r);
                Toast.makeText(getApplicationContext(), "Redirecting..", Toast.LENGTH_SHORT).show();
            }
            else if(result.contains("ERROR")){
                Toast.makeText(getApplicationContext(), "database Fail", Toast.LENGTH_SHORT).show();
                EditText editTextPassword = ((EditText) findViewById(R.id.editText1));
                EditText editTextUsername = ((EditText) findViewById(R.id.editText2));
                editTextPassword.setText("");
                editTextUsername.setText("");
            }
            else{
                Toast.makeText(getApplicationContext(), "login Fail", Toast.LENGTH_SHORT).show();
                EditText editTextPassword = ((EditText) findViewById(R.id.editText1));
                EditText editTextUsername = ((EditText) findViewById(R.id.editText2));
                editTextPassword.setText("");
                editTextUsername.setText("");
            }
        }

    }
    private String checkCorrectUser(JSONObject object){
        String answer = "ERROR";
        try {
            System.out.println(object.getString("DATA"));
            if(!object.getString("TYPE").contains("ERROR")) {
                answer = "FALSE";
                if (object.getString("DATA").contains("TRUE")) {
                   answer = "TRUE";
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answer;
    }
   /* private Boolean checkIfCorrect(JSONObject object){
        try {
            if(object.getString("TYPE").contains("ERROR")){
                return false;
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }return false;
    }
    */
    private String checkType(JSONObject object){
      return object.toString();
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
    private String ConvertJsonToData(String response) throws JSONException {
        JSONObject array = new JSONObject(response.toString());
        response ="";
        System.out.println(array.length());
        if(!array.getString("TYPE").contains("ERROR")) {
            JSONObject datastring = array.getJSONObject("DATA");
            Iterator<String> keys = datastring.keys();
            while (keys.hasNext()) {
              String key = keys.next();
                 response += datastring.getString(key);

               // response += json.getString(key);
               // response += '\t';
            }
            System.out.println(response);
            return response;
        }
        return array.getString("TYPE");
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

        String ipadress = "www.lifebyme.stsvt16.student.it.uu.se/php/";
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
        LoginTask task = new LoginTask();
        task.execute(url);
    }

    private void login(String user, String pwd) {
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
        String testdata = SetupURLValueSend("JSONgen.php?",empty1,empty2,username,password);
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
