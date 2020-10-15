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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView lvHome;
    int rImag[] = {R.drawable.crigs_logo, R.drawable.hmt_logo, R.drawable.crigs_logo, R.drawable.hmt_logo, R.drawable.crigs_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lvHome = findViewById(R.id.lvHome);
        myAdapter adapter = new myAdapter(this, rImag);
        lvHome.setAdapter(adapter);
}

    class myAdapter extends ArrayAdapter<String>{
        Context context;
        int myImages[];


        public myAdapter(@NonNull Context c, int myImages[]) {
            super(c, R.layout.home_page_list_view_layout, R.id.img);
            this.context = c;
            this.myImages = myImages;
        }

        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
            //LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //View row = layoutInflater.inflate(R.layout.home_page_list_view_layout, parent, false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_list_view_layout, null, true);
            ImageView img = findViewById(R.id.img);

            img.setImageResource(rImag[position]);

            return view;
        }
    }
}
