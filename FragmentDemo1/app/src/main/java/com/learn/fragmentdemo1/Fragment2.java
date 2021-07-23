package com.learn.fragmentdemo1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragment2 extends androidx.fragment.app.Fragment {

    private TextView fg2_tv_content;

    public Fragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        fg2_tv_content =view.findViewById(R.id.fg2_tv_content);
        fg2_tv_content.setText(getArguments().getString("info"));
        return view;
    }
}
