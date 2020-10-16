package com.example.crigsarmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView lvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}


}
