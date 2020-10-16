package com.example.crigsarmobileapp;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageDisplayAdapter extends ArrayAdapter<ImageDisplayClass> {

    private Context mcontext;
    private int mResource;
    private ArrayList<ImageDisplayClass> obj;

    public ImageDisplayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ImageDisplayClass> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.mResource = resource;
        this.obj = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(mResource, parent, false);

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_list_view_layout, null, true);

        ImageView img1 = view.findViewById(R.id.img);

        img1.setImageResource(obj.get(position).getImage());

        return view;
    }
}
