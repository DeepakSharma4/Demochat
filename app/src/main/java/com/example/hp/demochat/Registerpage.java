package com.example.hp.demochat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ApiconfigFolder.Apiconfig;
import HttprequestFolder.HttprequestProcesser;
import HttprequestFolder.Response;

public class Registerpage extends AppCompatActivity {

    private EditText edtName, edtAddress, edtEmailID, edtPhone, edtUserName, edtPassword;
    private Button submit;
    //
    private String name, address, emailID, phone, userName, password;
    //
    private HttprequestProcesser httpRequestProcessor;
    private Response response;
    private Apiconfig apiConfiguration;
    //
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private int responseData;
    private String message;
    private boolean success;

    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        //
        edtName = (EditText) findViewById(R.id.editname);
        edtAddress = (EditText) findViewById(R.id.editaddrss);
        edtEmailID = (EditText) findViewById(R.id.editemail);
        edtPhone = (EditText) findViewById(R.id.editphno);
        edtUserName = (EditText) findViewById(R.id.editusrname);
        edtPassword = (EditText) findViewById(R.id.editpasswrd);
        submit = (Button) findViewById(R.id.button1);
        //
        //Initialization
        httpRequestProcessor = new HttprequestProcesser();
        response = new Response();
        apiConfiguration = new Apiconfig();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";     //AccountAPI/SaveApplicationUser

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting values
                name = edtName.getText().toString();
                address = edtAddress.getText().toString();
                emailID = edtEmailID.getText().toString();
                phone = edtPhone.getText().toString();
                userName = edtUserName.getText().toString();
                password = edtPassword.getText().toString();

                new RegistrationTask().execute(name, address, emailID, phone, userName, password);

              /*  Intent i = new Intent(Registerpage.this, userlogin.class);
                startActivity(i);*/
            }
        });
    }

    //
    public class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            Log.e("Name", name);
            address = params[1];
            emailID = params[2];
            phone = params[3];
            userName = params[4];
            password = params[5];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("Address", address);
                jsonObject.put("EmailId", emailID);
                jsonObject.put("MobileNo", phone);
                jsonObject.put("UserName", userName);
                jsonObject.put("Password", password);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                responseData = jsonObject.getInt("responseData");
                Log.d("responseData", String.valueOf(responseData));
                // success = jsonObject.getBoolean("success");
                //  Log.d("Success", String.valueOf(success));
              //  message = jsonObject.getString("message");
              //  Log.d("message", message);

                if (responseData == 1) {
                    Toast.makeText(Registerpage.this, "Data Submitted", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Registerpage.this, userlogin.class));
                } else if (responseData == 2) {
                    Toast.makeText(Registerpage.this, "Data not Submitted", Toast.LENGTH_LONG).show();
                }

               /* JSONObject jsonObject = new JSONObject(s);
                responseData = jsonObject.getInt("responseData");
                Log.d("responseData", String.valueOf(responseData));
                message = jsonObject.getString("message");
                Log.d("message", message);

                if (name.equals("") || emailID.equals("") || phone.equals("") || address.equals("") || userName.equals("") || password.equals("")) {
                    Toast.makeText(Registerpage.this, "Empty field not allowed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Registerpage.this, "valid", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Registerpage.this, userlogin.class));
                }  */

                if (success) {

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
                        phone = object.getString("MobileNo");
                        Log.d("MobileNo", phone);
                        userName = object.getString("UserName");
                        Log.d("userName", userName);
                        password = object.getString("UserName");
                        Log.d("password", password);






                    }

                    //  startActivity(new Intent(Registerpage.this, userlogin.class));
                } /*else {
                    Toast.makeText(Registerpage.this, "valid", Toast.LENGTH_LONG).show();
                }*/


                /*if (success) {
                    startActivity(new Intent(RegisterActivity.this, LoginAct◙ivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

