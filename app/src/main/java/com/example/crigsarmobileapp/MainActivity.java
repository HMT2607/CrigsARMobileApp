package com.example.crigsarmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Animation popUpAnim, logAnime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        popUpAnim = AnimationUtils.loadAnimation(this,R.anim.anim_popup_cons);
        logAnime = AnimationUtils.loadAnimation(this,R.anim.anim_logo);

        ConstraintLayout constraintLayout = findViewById(R.id.consLayout);
        constraintLayout.setAnimation(popUpAnim);

        ImageView logoView = findViewById(R.id.logoImage);
        logoView.setAnimation(logAnime);

        Button butStart = findViewById(R.id.butStart);
        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
