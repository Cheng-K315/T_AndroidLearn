package com.learn.fragmentdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyFragment1 extends Fragment implements View.OnClickListener {

    private Context mContext;
    private TextView text_content;
    private String content;
    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private TextView tab_menu_channel_num;
    private TextView tab_menu_message_num;
    private TextView tab_menu_better_num;
    private TextView tab_menu_setting_num;

    public MyFragment1() {
    }

    public MyFragment1(String content){
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        bindView(view);

        text_content.setText(content);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void bindView(View view) {
        text_content = (TextView)view.findViewById(R.id.fg_content);
        btn_one = (Button)view.findViewById(R.id.btn_one);
        btn_two = (Button)view.findViewById(R.id.btn_two);
        btn_three = (Button)view.findViewById(R.id.btn_three);
        btn_four = (Button)view.findViewById(R.id.btn_four);
        tab_menu_channel_num = (TextView)getActivity().findViewById(R.id.tab_menu_channel_num);
        tab_menu_message_num = (TextView)getActivity().findViewById(R.id.tab_menu_message_num);
        tab_menu_better_num = (TextView)getActivity().findViewById(R.id.tab_menu_better_num);
        tab_menu_setting_num = (TextView)getActivity().findViewById(R.id.tab_menu_setting_num);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        text_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one :
                tab_menu_channel_num.setText("11");
                tab_menu_channel_num.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_two :
                tab_menu_message_num.setText("99+");
                tab_menu_message_num.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_three :
                tab_menu_better_num.setText("5");
                tab_menu_better_num.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_four :
                tab_menu_setting_num.setText("1");
                tab_menu_setting_num.setVisibility(View.VISIBLE);
                Log.d("DATE","content");
                break;
        }

    }
}
