package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by fleron on 2016-04-08.
 */

public class TalkToDBActivity extends Activity {

    String username = "fleron";
    String password = "12345";
    String email = "@gmail.com";
    /*TalkToDBActivity(String username,String password,String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        String pwd = intent.getStringExtra("password");
        if(intent.getIntExtra("requestCode",7) == 1){
            login(user,pwd,email,"");
        }


    }

    public void createUser(String username,String pwd, String email){

    }

    /*public void login(String username, String pwd){

    }
    */
    public void onFinish(Integer result){
        setResult(result);
    }

    private class talkToDBTask extends AsyncTask<String, Void, String> {

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
                if(!checkFail(object)){
                    if(checkCorrectUser(object)){
                     output = "TRUE";
                        System.out.println(output);
                    }
                }

                System.out.println(output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return output;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.contains("TRUE")){
               Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                System.out.println("onPostExecute");
               // onFinish(1);
            }
            else{
                finish();
            }
          /*  System.out.println(result);
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
            */
        }

    }
    private Boolean checkCorrectUser(JSONObject object){
        try {
            System.out.println(object.getString("DATA"));
            if (object.getString("DATA").contains("TRUE")) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
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
    private Boolean checkFail(JSONObject object){
        try {
            return object.getString("TYPE").contains("ERROR");
        } catch (JSONException e) {
            return false;
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
    private String ConvertJsonToData(String response) throws JSONException {
        JSONObject array = new JSONObject(response.toString());
        response ="";
        System.out.println(array.length());
        if(!checkFail(array)) {
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
    private String SetupURLValueSend(String program, String[] values, String[] keys,String username,String password,String email, String type){

        String ipadress = "http://www.lifebyme.stsvt16.student.it.uu.se/php/";
        String data = "";
        try {
            data = ipadress+program+ URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            if(values.length >0 && type.contains("VALUE")) {
                for (int i = 0; i < values.length; i++) {
                    data += "&testdata2[";
                    if (keys.length>0){
                        data += URLEncoder.encode(keys[i],"UTF-8");
                    }
                    data +="]=" + URLEncoder.encode(values[i], "UTF-8");
                }
            }
            else if(checkValidUserData(username,password,email) && type.contains("NEW USER")){
                System.out.println("new user");
            }

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;

    }
    private boolean checkValidUserData(String username,String password,String email){
        if(username.length()>0 && password.length() > 0 && email.length() > 0){
            return true;
        }return false;
    }

    private void setupConnection(String[] url){

        talkToDBTask task = new talkToDBTask();
       task.execute(url);
    }
    /*private void createUser(String user,String pwd, String email){
        String[] empty1 = {};
        String[] empty2 = {};
        String createuser = SetupURLValueSend("JSONgen.php?",empty1,empty2,user,pwd,email, "NEW USER");
        setupConnection(new String[]{createuser});
    }*/
    private void login(String user, String pwd, String email, String type) {
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
        String mail = email;
        String testType = "NEW USER";
        //setText("söker...");
        //String testdata = SetupURLValueSend("JSONgen.php?",empty1,empty2,username,password,"",testType);
        String test = SetupURLValueSend("LogIn.php?",empty1,empty2,username,password,"",testType);

        //String testdata2 = SetupURLValueSend("CreateUser.php?", empty1, empty2,username,password,email,testType);
        //System.out.println(testdata);
        setupConnection(new String[]{test});
    }




}
