package AdapterFolder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.demochat.R;
import com.example.hp.demochat.Registerpage;
import com.example.hp.demochat.tabthird;
import com.example.hp.demochat.userlogin;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ApiconfigFolder.Apiconfig;
import BeansFolder.UserBean;
import HttprequestFolder.HttprequestProcesser;
import HttprequestFolder.Response;
import sharedprefrnce.Login_Pref;

/**
 * Created by hp on 4/20/2017.
 */

public class Useradapter extends BaseAdapter implements View.OnClickListener {
    //
    private String memberId, friendId, requestby, modifiedby;
    //
    private String loggedInId;
    private String  name, emailId;
    //
    private UserBean userBean;
    private String suggestname;
    //
    private int responseData;
    private SharedPreferences sharedPreferences;

    private HttprequestProcesser httpRequestProcessor;
    private Response response;
    private Apiconfig apiConfiguration;

    private String baseURL, urluser;
    private String jsonPostString;
    private String jsonResponseString;
    private String message;
    private boolean success;

    private Context context;
    private ArrayList<UserBean> user_beanArrayList;
    private LayoutInflater inflater;

    public Useradapter(Context context, ArrayList<UserBean> user_beanArrayList) {
        this.context = context;
        this.user_beanArrayList = user_beanArrayList;
    }

    @Override
    public int getCount() {
        return user_beanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return user_beanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.mylayout, viewGroup, false);
        TextView txt1 = (TextView) view.findViewById(R.id.txtName);
        TextView txt2 = (TextView) view.findViewById(R.id.txtAddress);
    //    TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);
    //    ImageView iv = (ImageView) view.findViewById(R.id.imageUser);
        Button btn = (Button) view.findViewById(R.id.addfrnd);
//Getting Data
        userBean = user_beanArrayList.get(i);

        name = userBean.getName();
        emailId = userBean.getEmailId();
        int mId = userBean.getMemberID();
        memberId = String.valueOf(mId);
        //Button btnAdd = member.getBtnAddFriend();

        txt1.setText(name);
        txt2.setText(emailId);

        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addfrnd:
                httpRequestProcessor = new HttprequestProcesser();
                response = new Response();
                apiConfiguration = new Apiconfig();

                //Getting base url
                baseURL = apiConfiguration.getApi();
                urluser = baseURL + "ApplicationFriendAPI/AddFriendRequest";   //ApplicationFriendAPI/AddFriendRequest */

                //Getting LoggedIn user Detail from SharedPreference
                sharedPreferences = context.getSharedPreferences(Login_Pref.Pref_Name, Context.MODE_PRIVATE);
                loggedInId = sharedPreferences.getString(Login_Pref.LoggedInUserID, null);
                new UserTask(v).execute(memberId, loggedInId);
                //Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show();
                break;
        }
    }


    public class UserTask extends AsyncTask<String, String, String> {

        private View view;

        public UserTask(View view) {
            this.view = view;
        }

        @Override
        protected String doInBackground(String... params) {
            friendId = params[0];
            loggedInId = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MemberId",loggedInId);
                jsonObject.put("FriendId",   friendId);

                Log.d("FriendId",   friendId);
                jsonObject.put("RequestBy", loggedInId);
                jsonObject.put("CreatedBy", loggedInId);
                jsonObject.put("ModifiedBy", loggedInId);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urluser);
                jsonResponseString = response.getJsonResponseString();
                return jsonResponseString;
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
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);

                if (success) {
                    responseData = jsonObject.getInt("responseData");
                    Log.d("responseData", String.valueOf(responseData));
                /*   JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);  */

                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();


                }

                // startActivity(new Intent(Registerpage.this, userlogin.class));
                else {
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}