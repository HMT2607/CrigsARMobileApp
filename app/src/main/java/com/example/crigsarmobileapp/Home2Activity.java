package com.example.crigsarmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home2Activity extends AppCompatActivity {


    ListView lvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        Button logout = findViewById(R.id.logoutbut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(Home2Activity.this, LoginActivity.class);
                intent.putExtra("facebookLogCheck", "logged");
                startActivity(intent);
            }
        });

        //=====================================================================================================================
         GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

             @Override
             public void onCompleted(JSONObject object, GraphResponse response) {
                 //Toast.makeText(getApplicationContext(), "entered", Toast.LENGTH_SHORT).show();
                 Log.d("print", "test");
                 Log.d("print", object.toString());

                 try {
                     String first_name = object.getString("first_name");
                     Toast.makeText(getApplicationContext(), first_name, Toast.LENGTH_SHORT).show();

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
         });

         Bundle bundle = new Bundle();

         bundle.putString("fields", "gender, name, id, first_name, last_name");

         graphRequest.setParameters(bundle);
         graphRequest.executeAsync();


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




}