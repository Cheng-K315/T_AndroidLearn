package com.learn.fragmentdemo1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragment0 extends androidx.fragment.app.Fragment {

    private TextView fg0_tv_content;
    public Fragment0() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        fg0_tv_content =view.findViewById(R.id.fg0_tv_content);
//        fg0_tv_content.setText(getArguments().getString("date"));
        return view;
    }
}
