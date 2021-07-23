package com.learn.fragmentdemo1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Fragment1 extends Fragment {

    private TextView fg_tv_content;
    private Button fg_btn_commit;
    private EditText fg_et_content;
    private String info;
    private CallBack callBack;

    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);
        fg_tv_content =view.findViewById(R.id.fg_tv_content);
        fg_et_content = (EditText) view.findViewById(R.id.fg_et_content);
        fg_btn_commit = view.findViewById(R.id.fg_btn_commit);

        fg_tv_content.setText(getArguments().getString("date"));


        fg_btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info = fg_et_content.getText().toString();
                Log.d("INFO",info);
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("info",info);
                Fragment2 fragment2 = new Fragment2();
                fragment2.setArguments(bundle);
                fragmentTransaction.replace(R.id.ly_fragment,fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                callBack.getResult(info);
            }
        });
        return view;
    }

    public  interface CallBack{
        void getResult(String result);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        callBack = (CallBack)getActivity();
    }
}
