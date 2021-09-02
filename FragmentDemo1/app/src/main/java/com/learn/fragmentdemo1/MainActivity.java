package com.learn.fragmentdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    private TextView tv_content;
    private Button btn_commit;
    private EditText et_content;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_content = findViewById(R.id.tv_content);
        et_content = findViewById(R.id.et_content);
        btn_commit = findViewById(R.id.btn_commit);
        fManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();

        Fragment0 fragment0 = new Fragment0();
        fragmentTransaction.add(R.id.ly_fragment,fragment0).commit();

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("date",et_content.getText().toString());
//                Fragment0 fragment0= new Fragment0();
//                fragment0.setArguments(bundle);
//                fragmentTransaction.add(R.id.ly_fragment,fragment0).addToBackStack(null).commit();

                Fragment1 fragment1 = new Fragment1();
                fragment1.setArguments(bundle);
                fragmentTransaction.replace(R.id.ly_fragment,fragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


//    @Override
//    public void getResult(String result) {
//        tv_content.setText(result);
//        Toast.makeText(getApplicationContext(),"-->"+result,Toast.LENGTH_SHORT).show();
//    }

}