package com.learn.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

public class ResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.tv_result);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Serializable data = bundle.getSerializable("date");
        resultText.setText(""+data);
    }
}