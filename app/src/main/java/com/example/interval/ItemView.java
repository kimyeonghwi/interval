package com.example.interval;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {
    TextView txt_title,txt_set,txt_run,txt_rest;

    public ItemView(Context context) {
        super(context);
        setwidget(context);
    }

    public void set_txt_title(String name) {
        txt_title.setText(name);
    }

    public void set_txt_set(String hour){
        txt_set.setText(hour);
    }
    public void set_txt_run(String minute){
        txt_run.setText(minute);
    }
    public void set_txt_rest(String second){
        txt_rest.setText(second);
    }



    public void setwidget(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout, this, true);

        txt_title = findViewById(R.id.txt_title);

        txt_set = findViewById(R.id.txt_set);
        txt_run = findViewById(R.id.txt_run);
        txt_rest = findViewById(R.id.txt_rest);




    }

}

