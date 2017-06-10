package com.example.hp.demochat;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AdapterFolder.Useradapter;
import ApiconfigFolder.Apiconfig;
import BeansFolder.UserBean;
import HttprequestFolder.HttprequestProcesser;
import HttprequestFolder.Response;

public class tabthird extends Fragment {

    private String name, emailID;
    private int memberID;
    private ListView lv;

  //  private boolean success;
    private HttprequestProcesser httpRequestProcessor;
    private Response response;
    private Apiconfig apiConfiguration;


    private String baseURL, urlUser, jsonResponseString;
    private boolean success;


    private UserBean user_bean;
    private ArrayList<UserBean> user_beanArrayList;
    private Useradapter userAdapter;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tabthird, container, false);

        //findViewByID
        lv = (ListView) view.findViewById(R.id.lv);

        //initialization
        httpRequestProcessor = new HttprequestProcesser();
        response = new Response();
        apiConfiguration = new Apiconfig();
        user_beanArrayList = new ArrayList<UserBean>();

        //Getting BAseURL
        baseURL = apiConfiguration.getApi();
        urlUser = baseURL + "ApplicationFriendAPI/GetApplicationMemberList";   //ApplicationFriendAPI/GetApplicationMemberList
        new UserTask().execute();

        userAdapter = new Useradapter(getActivity(), user_beanArrayList);
        lv.setAdapter(userAdapter);
        return view;
    }


    //Getting users list

    public class UserTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            jsonResponseString = httpRequestProcessor.gETRequestProcessor(urlUser);
            return jsonResponseString;

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("str", s);


            try {
                JSONObject object = new JSONObject(s);
                success = object.getBoolean("success");
                if (success) {
                 //   message=object.getString("success");
                    JSONArray responseData = object.getJSONArray("responseData");

                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object1 = (JSONObject) responseData.get(i);
                        name = object1.getString("Name");
                        Log.d("Name", name);
                        emailID = object1.getString("EmailId");
                       // phone = object1.getString("MobileNo");
                        memberID = object1.getInt("MemberId");
                        user_bean = new UserBean(name,emailID,memberID);
                        user_beanArrayList.add(user_bean);



                    /*    name = object.getString("Name");
                        Log.d("Name", name);
                        emailID = object.getString("EmailId");
                        Log.d("EmailId", emailID);
                        memberID = object.getInt("MemberId");
                        member = new Member(name, emailID,memberID);
                        memberArrayList.add(member);  */


                    }
                    userAdapter.notifyDataSetChanged();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}





