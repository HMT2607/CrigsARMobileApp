package com.example.crigsarmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home2Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    ListView lvHome;

    String name, first_name, last_name, gender, birthday, profil_id;


    private static String url_add ="http://192.168.100.44:8080/crigs/facebookadd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        //=====================================================================================================================
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                //Toast.makeText(getApplicationContext(), "entered", Toast.LENGTH_SHORT).show();
                Log.d("print", "test");
                Log.d("print", object.toString());
                Log.d("checking2", "2");
                try {
                    name = object.getString("name");
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    gender = object.getString("gender");
                    birthday = object.getString("birthday");
                    profil_id = object.getString("id");
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                    Log.d("name test", name);
                    Log.d("checking3", "3");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "IN CATCH", Toast.LENGTH_SHORT).show();
                }



                try{
                    StringRequest request = new StringRequest(Request.Method.POST, url_add, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "successful", Toast.LENGTH_SHORT).show();
                        }}, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                            Log.d("checking4", "4");
                        }

                    }){
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name);
                            params.put("first_name", first_name);
                            params.put("last_name", last_name);
                            params.put("gender", gender);
                            params.put("birthday", birthday);
                            params.put("profil_id", profil_id);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Home2Activity.this);
                    requestQueue.add(request);


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "did not enter", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Bundle bundle = new Bundle();

        bundle.putString("fields", "gender, name, id, first_name, last_name, birthday");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
        Log.d("checking", "1");
        //=====================================================================================================================




        //=====================================================================================================================
        lvHome = findViewById(R.id.lvHome);

        ArrayList<ImageDisplayClass> ImgList = new ArrayList<>();

        ImgList.add(new ImageDisplayClass(R.drawable.big_sale));
        ImgList.add(new ImageDisplayClass(R.drawable.shoesdis));
        ImgList.add(new ImageDisplayClass(R.drawable.girlshoes));
        ImgList.add(new ImageDisplayClass(R.drawable.blacksh));
        ImgList.add(new ImageDisplayClass(R.drawable.boatsh));
        ImgList.add(new ImageDisplayClass(R.drawable.bootsh));
        ImgList.add(new ImageDisplayClass(R.drawable.graysh));
        ImgList.add(new ImageDisplayClass(R.drawable.thrity_off));

        ImageDisplayAdapter adapter = new ImageDisplayAdapter(this, R.layout.home_page_list_view_layout, ImgList);
        lvHome.setAdapter(adapter);


        Button butAr = findViewById(R.id.butAr);
        butAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, ArActivity.class);
                startActivity(intent);
            }
        });
    }


    public void popupMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.f_signout_pop);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.sign_out_f:
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(Home2Activity.this, LoginActivity.class);
                intent.putExtra("facebookLogCheck", "logged");
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }
}
