package com.example.crigsarmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ListView lvHome;

    GoogleSignInClient mGoogleSignInClient;

    String personName, personGivenName, personFamilyName, personEmail, personId;

    private static String url_add ="http://192.168.100.44:8080/crigs/googleadd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //=====================================================================================================================

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            //Uri personPhoto = acct.getPhotoUrl();

            Log.d("GOOGLE", personName);
            Log.d("GOOGLE", personGivenName);
            Log.d("GOOGLE", personFamilyName);
            Log.d("GOOGLE", personEmail);
            Log.d("GOOGLE", personId);
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
                }

            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("personName", personName);
                    params.put("personGivenName", personGivenName);
                    params.put("personFamilyName", personFamilyName);
                    params.put("personEmail", personEmail);
                    params.put("personId", personId);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
            requestQueue.add(request);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "did not enter", Toast.LENGTH_SHORT).show();
        }


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
                Intent intent = new Intent(HomeActivity.this, ArActivity.class);
                startActivity(intent);
            }
        });



    }

    public void popupMenuG(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.f_signout_pop);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.sign_out_f:
                signOut();
                return true;

            default:
                return false;
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        LoginActivity lgAct = new LoginActivity();
                        lgAct.setGoogleCheck(false);

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                });
    }


}
