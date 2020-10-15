package com.example.crigsarmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Animation popUpAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        popUpAnim = AnimationUtils.loadAnimation(this,R.anim.anim_popup_cons);

        ConstraintLayout constraintLayout = findViewById(R.id.loginCons);
        constraintLayout.setAnimation(popUpAnim);

        Button butLogin = findViewById(R.id.butLogin);
        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
