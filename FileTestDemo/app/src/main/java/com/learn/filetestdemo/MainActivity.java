package com.learn.filetestdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_content;
    private Button btn_save;
    private Button btn_clean;
    private Button btn_read;
    private SDFileHelper sdFileHelper;
    private FileHelper fileHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdFileHelper = new SDFileHelper(context);
        context = getApplicationContext();
        fileHelper = new FileHelper(context);
        bindViews();
    }

    private void bindViews() {
        et_content = (EditText)findViewById(R.id.et_content);
        et_name = (EditText)findViewById(R.id.et_name);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_read = (Button)findViewById(R.id.btn_read);
        btn_clean = (Button)findViewById(R.id.btn_clean);

        btn_save.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        btn_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clean:
                et_name.setText("");
                et_content.setText("");
                break;
            case R.id.btn_save:
                String filename = et_name.getText().toString();
                String filecontent = et_content.getText().toString();

                try {
                    fileHelper.save(filename,filecontent);
                    Toast.makeText(getApplicationContext(),"写入数据成功！",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"写入数据失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_read:
                String content = null;
                String filename1 = et_name.getText().toString();
                try {
                     content= fileHelper.read(filename1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}