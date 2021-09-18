package com.example.myapplication5.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication5.MyIncomeActivity;
import com.example.myapplication5.R;


import java.util.ArrayList;

public class MyBaseAdapter<content2> extends BaseAdapter {
    String[] id;
    String[] content;
    Context context;
    public MyBaseAdapter(String[] id, String[] content, Context context){
        this.id = id;
        this.content = content;
        this.context = context;
    }
    @Override
    public int getCount() {
        return id.length;
    }

    @Override
    public Object getItem(int position) {
        return id[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = View.inflate(context, R.layout.list_item,null);
        TextView mId = view.findViewById(R.id.tv_id);
        mId.setText(id[position]);
        TextView mContent = view.findViewById(R.id.tv_content);
        mContent.setText(content[position]);
        return view;
    }
}
