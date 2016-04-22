package com.example.martin.myapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
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

public class talkToDBTask extends AsyncTask<String, Void, String> {
    private String username;
    private String pwd;
    private String email;
    private String[] values;
    private String[] keys;
    private String URL;
    private int requestType;
    private OnTalkToDBFinish listener;

    public talkToDBTask(OnTalkToDBFinish listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        String output = "";
        runCommandOnRequest();

        //for (String url : params) {
        System.out.println(requestType);
        System.out.println(URL);
        response = getURLResponse(URL);
        //}
        try {
            JSONObject object = new JSONObject(response.toString());
            if (!checkFail(object)) {
                if (checkType(object).contains("LOGIN")) {
                    if (checkCorrectUser(object)) {
                        output = "TRUE";
                        System.out.println(output);
                        System.out.println("success");
                    }
                } else if (checkType(object).contains("NEW USER")) {
                    if (checkCorrectUser(object)) {
                        output = "TRUE";
                    }
                } else if (checkType(object).contains("VAR ADDED")) {
                    if (checkCorrectUser(object)) {
                        output = "TRUE";

                    }
                } else if (checkType(object).contains("RETR DATA")) {
                    storeValues(object);
                    output = "TRUE";
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
        System.out.println(result);
        System.out.println(requestType);

        if(result.contains("TRUE")){
            listener.onTaskCompleted();
            System.out.println(result);
        }
        else{
            listener.onTaskFailed();
        }


        /*
        if (result.contains("LOGIN SUCCESS") && requestType == 1) {
            dbSuccess(result, false);
        } else if (result.contains("LOGIN SUCCESS") && requestType == 2) {
            dbFail(result);
        } else if (result.contains("NEW USER ADDED") && requestType == 2) {
            dbSuccess(result, false);

        } else if (result.contains("VARIABLE ADDED") && requestType == 3) {
            dbSuccess(result, false);
        } else if (!result.contains("LOGIN SUCCESS") && requestType == 2) {
            dbSuccess(result, false);

        } else if (result.contains("VARIABLES RETRIEVED") && requestType == 4) {
            dbSuccess(result, true);
        } else {
            dbFail(result);
        }
        */
    }
    private void runCommandOnRequest(){
        switch (requestType) {
            case 1:
                login(username, pwd);
                System.out.println("försöker logga in");
                break;
            case 2:
                //email = intent.getStringExtra("email");
                createUser(username, pwd, email);
                break;
            case 3:
                sendVariables(username, pwd, values, keys);
                break;
            case 4:
                getVariables(username, pwd);
                break;
        }
    }

    private void login(String user, String pwd) {
        String program = "LogIn.php?";
        this.URL = setupURLBasic(user, pwd, program);
        System.out.println(URL);
        //setupConnection(new String[]{URL});
    }

    private void storeValues(JSONObject object) {
        try {
            JSONArray jsonKeys = object.getJSONArray("KEYS");
            JSONArray jsonValues = object.getJSONArray("VALUES");
            System.out.println(jsonKeys.getString(0));
            keys = new String[jsonKeys.length()];
            values = new String[jsonValues.length()];

            for (int i = 0; i < jsonKeys.length(); i++) {
                keys[i] = jsonKeys.getString(i);
                values[i] = jsonValues.getString(i);
            }
            System.out.println(values[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
/*
    private void dbFail(String result) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", result);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
        System.out.println("onPostExecute");
    }

    private void dbSuccess(String result, boolean receive) {
        Intent returnIntent = new Intent();
        if (receive) {
            returnIntent.putExtra("keys", keys);
            returnIntent.putExtra("values", values);
        }
        returnIntent.putExtra("result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        System.out.println("onPostExecute");
    }
*/
    private void createUser(String user, String pwd, String email) {
        this.URL = setupURLNewUser(user, pwd, email);
        //setupConnection(new String[]{URL});
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setValues(String[] values){
        this.values = values;
    }
    public void setKeys(String[] keys){
        this.keys = keys;
    }
    public void setRequestType(int requestType){
        this.requestType = requestType;
    }
    public int getRequestType(){
        return this.requestType;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPwd(){
        return this.pwd;
    }
    public String[] getValues(){
        return this.values;
    }
    public String[] getKeys(){
        return this.keys;
    }

    private void sendVariables(String user, String pwd, String[] values, String[] keys) {
        this.URL = setupURLValueSend(user, pwd, values, keys);
        //setupConnection(new String[]{URL});
    }

    private void getVariables(String user, String pwd) {
        String program = "getdata.php?";
        this.URL = setupURLBasic(user, pwd, program);
        //setupConnection(new String[]{URL});
    }

    private String checkType(JSONObject object) {
        try {
            return object.getString("TYPE");
        } catch (JSONException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    private Boolean checkCorrectUser(JSONObject object) {
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

    private Boolean checkFail(JSONObject object) {
        try {
            return object.getString("TYPE").contains("ERROR");
        } catch (JSONException e) {
            return false;
        }
    }

    private String getURLResponse(String url) {
        HttpURLConnection connection = null;
        String response = "";
        try {
            java.net.URL mainURL = new URL(url);
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

    private void failureHandler() {

    }

    private String setupURLValueSend(String username, String password, String[] values, String[] keys) {
        String ipadress = "http://www.lifebyme.stsvt16.student.it.uu.se/php/";
        String url = "";
        String program = "Add_Var.php?";

        try {
            url = ipadress + program + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            url += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            for (int i = 0; i < values.length; i++) {
                url += "&data[";
                System.out.println("in to for");
                if (keys.length > 0) {
                    url += URLEncoder.encode(keys[i], "UTF-8");
                    url += "][";
                }
                url += "]=" + URLEncoder.encode(values[i], "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(url);
        return url;
    }

    private String setupURLBasic(String username, String password, String program) {

        String ipadress = "http://www.lifebyme.stsvt16.student.it.uu.se/php/";

        String data = null;
        try {
            data = ipadress + program + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            failureHandler();
        }

        return data;

    }

    private String setupURLNewUser(String username, String password, String email) {
        String ipadress = "http://www.lifebyme.stsvt16.student.it.uu.se/php/";
        String program = "NewUser.php?";
        String data = null;

        try {
            data = ipadress + program + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            failureHandler();
        }

        return data;
    }
}
