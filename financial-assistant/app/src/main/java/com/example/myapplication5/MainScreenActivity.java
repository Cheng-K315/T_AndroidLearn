package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.example.myapplication5.Service.DataManageActivity;
import com.example.myapplication5.Utils.MyHelper;
import com.example.myapplication5.Utils.dbProcess2;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private MyHelper helper;
    private dbProcess2 dbprocess2;
    private GridLayout glMenu;
    SharedPreferences sp ;
private ImageButton imgNewOut,imgNewIn,imgMyOut,imgMyIn,imgDataManage,imgSettings,imgTips,imgExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        initView();
        helper = new MyHelper(getBaseContext());
        dbprocess2 = new dbProcess2(helper);
    }
    /* 切换到前台，重新获取焦点执行的方法*/
    @Override
    protected void onResume(){
        super.onResume();
        sp = getSharedPreferences("data",MODE_PRIVATE);
        String bg_color = sp.getString("bg_prefs","");
        setBg(bg_color);
        System.out.println(bg_color);
    }

    private void initView() {
        imgNewOut = findViewById(R.id.imgbtn_outNew);
        imgNewIn = findViewById(R.id.imgbtn_inNew);
        imgMyOut = findViewById(R.id.imgbtn_myOutcome);
        imgMyIn = findViewById(R.id.imgbtn_myIncome);
        imgDataManage = findViewById(R.id.imgbtn_dataManagement);
        imgSettings = findViewById(R.id.imgbtn_settings);
        imgTips = findViewById(R.id.imgbtn_tips);
        imgExit = findViewById(R.id.imgbtn_exit);
        imgNewOut.setOnClickListener(this);
        imgNewIn.setOnClickListener(this);
        imgMyOut.setOnClickListener(this);
        imgMyIn.setOnClickListener(this);
        imgDataManage.setOnClickListener(this);
        imgSettings.setOnClickListener(this);
        imgTips.setOnClickListener(this);
        imgExit.setOnClickListener(this);
        glMenu = findViewById(R.id.gl_menu);
//        getIntent()不能乱用
//        String bg = getIntent().getStringExtra("bg");

    }

//设置壁纸函数
    private void setBg(String bg) {
        if(bg.equals("pink")){
            glMenu.setBackgroundResource(R.drawable.bg2);
//            this.getApplicationContext().setTheme(R.style.ThemePink);
        }
        if(bg.equals("green")){
            glMenu.setBackgroundResource(R.drawable.bg);
//            getApplication().setTheme(R.style.ThemeGreen);
        }
        if(bg.equals("blue")){
            glMenu.setBackgroundResource(R.drawable.bg3);
//            getApplication().setTheme(R.style.ThemeBlue);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.imgbtn_outNew:
                intent.setClass(this,NewOutcomeActivity.class);
                break;
            case R.id.imgbtn_inNew:
                intent.setClass(this,NewIncomeActivity.class);
                break;
            case R.id.imgbtn_myOutcome:
                intent.setClass(this, MyOutcomeActivity.class);
                break;
            case R.id.imgbtn_myIncome:
                intent.setClass(this,MyIncomeActivity.class);
                break;
            case R.id.imgbtn_dataManagement:
                intent.setClass(this, DataManageActivity.class);
                break;
            case R.id.imgbtn_settings:
                intent.setClass(this,SettingsActivity.class);
                break;
            case R.id.imgbtn_tips:
                intent.setClass(this,NewTipsActivity.class);
                break;
            case R.id.imgbtn_exit:
                intent.setClass(this,LoginActivity.class);
//                finish();
                break;
        }
        startActivity(intent);
    }
}