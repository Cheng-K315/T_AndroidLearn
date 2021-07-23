package com.learn.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment4 extends Fragment {
    private  String content;
    private TextView text_content;

    public MyFragment4() {
    }

    public MyFragment4(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth,container,false);
        text_content = (TextView)view.findViewById(R.id.fg_content);
        text_content.setText(content);
        return view;
    }
}
