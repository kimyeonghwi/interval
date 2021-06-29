package com.example.interval;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<Item> list = new ArrayList<>();


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView = new ItemView(parent.getContext());
        Item item = list.get(position);

        itemView.set_txt_set("세트횟수 : "+item.getSet()+"회");
        itemView.set_txt_title(item.getTitle());
        itemView.set_txt_rest("휴식시간 : "+item.getRest_min()+"분"+item.getRest_sec()+"초");
        itemView.set_txt_run("운동시간 : "+item.getRun_min()+"분"+item.getRun_sec()+"초");

        return itemView;
    }


    void addItem(Item item) {
        list.add(item);
    }
}

