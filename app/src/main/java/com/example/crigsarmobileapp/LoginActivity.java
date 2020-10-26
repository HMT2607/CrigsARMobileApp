package com.example.crigsarmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginbutton;
    private SignInButton signInButton;

    boolean GoogleCheck = false;

    String facebookLog = "noLogged";

    public boolean isGoogleCheck() {
        return GoogleCheck;
    }

    public void setGoogleCheck(boolean googleCheck) {
        GoogleCheck = googleCheck;
    }

    boolean FacebookCheck = false;




    private int RC_SIGN_IN = 0;

    Animation popUpAnim, logAnime, logTitle;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        popUpAnim = AnimationUtils.loadAnimation(this,R.anim.anim_popup_cons);
        logAnime = AnimationUtils.loadAnimation(this,R.anim.anim_logo);
        logTitle = AnimationUtils.loadAnimation(this,R.anim.anim_login_title);

        ImageView logoView = findViewById(R.id.logoImage);
        logoView.setAnimation(logAnime);

        TextView logTitleText = findViewById(R.id.textView2);
        logTitleText.setAnimation(logTitle);

        loginbutton = findViewById(R.id.login_button1);
        ConstraintLayout constraintLayout = findViewById(R.id.loginCons);
        constraintLayout.setAnimation(popUpAnim);


        callbackManager = CallbackManager.Factory.create();

        loginbutton.setPermissions(Arrays.asList("user_gender", "user_friends", "user_birthday"));

        loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //=================================================
        signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleCheck = true;

                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //=================================================
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GoogleCheck == false)
        {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);

            Intent intent = new Intent(LoginActivity.this, Home2Activity.class);
            startActivity(intent);
        }
        else if (GoogleCheck == true){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RC_SIGN_IN) {

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }



        }
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);


        } catch (ApiException e) {
            Log.d("ERROR", "signInResult:failed code=" + e.getStatusCode());
        }
    }
//
//
//
    @Override
    protected void onStart() {

        facebookLog = getIntent().getStringExtra("facebookLogCheck");


        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        if(account != null) {
            Log.d("onStart", account.toString());
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn == true && facebookLog == null){
            Intent intent = new Intent(LoginActivity.this, Home2Activity.class);
            startActivity(intent);
        }


    }




    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
