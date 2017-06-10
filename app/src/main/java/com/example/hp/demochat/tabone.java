package com.example.hp.demochat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import AdapterFolder.Adapter_Request;

import ApiconfigFolder.Apiconfig;
import BeansFolder.Request;
import HttprequestFolder.HttprequestProcesser;
import HttprequestFolder.Response;
import sharedprefrnce.Login_Pref;

import static com.example.hp.demochat.R.id.view;


public class tabone extends Fragment {
    private HttprequestProcesser httpRequestProcessor;
    private Response response;
    private Apiconfig apiConfiguration;
    private String baseURL, urlMyFriendRequest, jsonStringToPost, urlRequest;
    private boolean success;
    private String message, name, jsonResponse, loggedInUserID, applicationFriendAssociationId;

    private Request request;
    private ArrayList<Request> RequestArrayList;
    String[] Name;
    Adapter_Request adapter_request;
    private String memberName, friendName, friendId, memberId;
    private int memberID;
    private int ApplicationUserId;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int logID;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //  return inflater.inflate(R.layout.activity_tabone, container, false);
        View view = inflater.inflate(R.layout.activity_tabone, container, false);

        ListView lv = (ListView) view.findViewById(R.id.lv1);
        httpRequestProcessor = new HttprequestProcesser();
        response = new Response();
        apiConfiguration = new Apiconfig();

        sharedPreferences = getActivity().getSharedPreferences(Login_Pref.Pref_Name, Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getString(Login_Pref.LoggedInUserID, null);
        logID = Integer.parseInt(loggedInUserID);

        //editor=sharedPreferences.edit();
        // editor.putString(MyPref.ApplicationFriendAssociationId,ApplicationFriendAssociationId);
        //editor.commit();
        sharedPreferences = getActivity().getSharedPreferences(Login_Pref.Pref_Name, Context.MODE_PRIVATE);
        memberId = sharedPreferences.getString(Login_Pref.ApplicationFriendAssociationId, null);
        //ApplicationFriendAssociationId=sharedPreferences.getString(MyPref.ApplicationFriendAssociationId,null);
        //ApplicationUserId=Integer.parseInt(ApplicationFriendAssociationId);

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlMyFriendRequest = baseURL + "ApplicationFriendAPI/MyFriendRequest/" + logID;
        RequestArrayList = new ArrayList<>();
        new RequestTask().execute();


        adapter_request = new Adapter_Request(getActivity(), RequestArrayList);
        lv.setAdapter(adapter_request);


        return view;
    }

    public class RequestTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            jsonResponse = httpRequestProcessor.gETRequestProcessor(urlMyFriendRequest);
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);

                if (success) {
                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);
                        applicationFriendAssociationId = object.getString("ApplicationFriendAssociationId");
                        memberId = object.getString("FriendId");
                        Log.d("FriendId ", memberId);

                        friendId = object.getString("MemberId");
                        Log.d("MemberId ", friendId);

                        memberName = object.getString("MemberName");
                        Log.d("MemberName ", memberName);

                        name = object.getString("FriendName");
                        Log.d("FriendName ", name);

                        sharedPreferences = getActivity().getSharedPreferences(Login_Pref.Pref_Name, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString(Login_Pref.ApplicationFriendAssociationId, applicationFriendAssociationId);
                        editor.commit();

                        request = new Request(name, memberID, Integer.parseInt(applicationFriendAssociationId));

                        //sharedPreferences = Context.getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
                        // editor = sharedPreferences.edit();
                        //editor.putString(MyPref.ApplicationFriendAssociationId,ApplicationFriendAssociationId);
                        //editor.commit();
                        RequestArrayList.add(request);

                    }
                    adapter_request.notifyDataSetChanged();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}




