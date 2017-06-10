package com.example.hp.demochat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ApiconfigFolder.Apiconfig;
import HttprequestFolder.HttprequestProcesser;
import HttprequestFolder.Response;
import sharedprefrnce.Login_Pref;

public class userlogin extends AppCompatActivity {
    private EditText edtname, edtpasswrd;
    private Button btnlgn, btnregistr;
    private TextView txtview1;
    //
    private String errmsg;
    private String name, passwd;
    //
    private String user, pass, applicationUserId;
    private int loggedInUserID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
//
    private HttprequestProcesser httpRequestProcessor;
    private Response response;
    private Apiconfig apiConfiguration;
    //
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String message, address, emailID, phone, password, userName;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        edtname = (EditText) findViewById(R.id.editname);
        edtpasswrd = (EditText) findViewById(R.id.editpassword);
        btnlgn = (Button) findViewById(R.id.buttonlgn);
        btnregistr = (Button) findViewById(R.id.buttonregistr);
        txtview1 = (TextView) findViewById(R.id.txt1);
        //
        //Initialization
        httpRequestProcessor = new HttprequestProcesser();
        response = new Response();
        apiConfiguration = new Apiconfig();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";  //AccountAPI/GetLoginUser
        Log.e("urlLogin", urlLogin);

        //LOGIN BUTTON
        btnlgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting name and password
                Log.e("Login", "clicked");
                name = edtname.getText().toString().trim();
                passwd = edtpasswrd.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    edtname.setError("Enter UserName");
                    return;
                }

                if (TextUtils.isEmpty(passwd)) {
                    edtpasswrd.setError("Enter Password");
                    return;
                }
                new LoginTask().execute(name, passwd);

             //   sharedLoginPreferences = getSharedPreferences(LogIn_Pref.Pref_Name, MODE_PRIVATE);
            /*    sharedPreferences = getSharedPreferences(Login_Pref.Pref_Name,MODE_PRIVATE);
                editor = sharedPreferences.edit();

                editor.putString("user_key",name);
                editor.putString("pass_key", passwd);
                editor.putString("appUserId_key", applicationUserId);
                editor.commit();  */
             /* if (!validate()) {
                      Toast.makeText(userlogin.this, "Successful", Toast.LENGTH_LONG).show();

                   startActivity(new Intent(userlogin.this, chatpage.class));
                }*/
            }
        });


        //REGISTER BUTTON
        btnregistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userlogin.this, Registerpage.class);
                startActivity(i);
            }
        });
        //FORGOT PASSWORD TEXT CLICK
        txtview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userlogin.this, resetpassword.class);
                startActivity(i);
            }
        });
    }

    //
    private boolean validate() {
        if (edtname.getText().toString().trim().length() <= 0) {
            Toast.makeText(userlogin.this, "Enter your Name", Toast.LENGTH_LONG).show();
            return true;
        } else if (edtpasswrd.getText().toString().trim().length() <= 0) {
            Toast.makeText(userlogin.this, "Enter valid Password", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


    //
    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            Log.e("name", name);
            passwd = params[1];
            Log.e("password", passwd);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", name);
                jsonObject.put("Password", passwd);

                jsonStringToPost = jsonObject.toString();
                Log.e("post", jsonStringToPost);
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();
                Log.e("response", jsonResponseString);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                errmsg = jsonObject.getString("ErrorMessage");
                Log.e("errmsg", errmsg);
                if (errmsg.equals("User Authenticated!!")) {






                    name = jsonObject.getString("Name");
                    applicationUserId = jsonObject.getString("ApplicationUserId");
                  emailID= jsonObject.getString("EmailId");
                    Log.d("ApplicationUserId", applicationUserId);
                    sharedPreferences = getSharedPreferences(Login_Pref.Pref_Name, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(Login_Pref.UserName, name);
                    editor.putString(Login_Pref.EmailId, emailID);
                    editor.putString(Login_Pref.LoggedInUserID, applicationUserId);
                    editor.commit();


                    Toast.makeText(userlogin.this, "Login successful", Toast.LENGTH_LONG).show();
                   startActivity(new Intent(userlogin.this, chatpage.class));

                } else {
                    Toast.makeText(userlogin.this, errmsg, Toast.LENGTH_LONG).show();
                }

               /* if (success) {
                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);
                        userID = object.getInt("UserId");
                        Log.d("userId", String.valueOf(userID));
                        name = object.getString("Name");
                        Log.d("name", name);
                        address = object.getString("Address");
                        Log.d("address", address);
                        emailID = object.getString("EmailId");
                        Log.d("emailId", emailID);
                        phone = object.getString("Phone");
                        Log.d("phone", phone);
                        userName = object.getString("UserName");
                        Log.d("userName", userName);
                        password = object.getString("Password");
                        Log.d("password", password);
                    }
                   *//* Intent intent = new Intent(userlogin.this, chatpage.class);
                    intent.putExtra("name", name);
                    startActivity(intent);  *//*
                } else {
                    Toast.makeText(userlogin.this, message, Toast.LENGTH_LONG).show();
                }*/


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
