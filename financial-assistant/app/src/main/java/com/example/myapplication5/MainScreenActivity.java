package com.example.myapplication5;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication5.Service.CountIncomeByMonth;
import com.example.myapplication5.Service.CountOutcomeByMonthActivity;
import com.example.myapplication5.Service.DataManageActivity;
import com.example.myapplication5.Utils.CurrentTime;
import com.example.myapplication5.Utils.MyHelper;
import com.example.myapplication5.Utils.SPSaveInfo;
import com.example.myapplication5.Utils.dbProcess2;

import java.util.Date;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private MyHelper helper;
    private dbProcess2 dbprocess2;
    private GridLayout glMenu;
    SharedPreferences sp ;
    private ImageButton imgNewOut,imgNewIn,imgMyOut,imgMyIn,imgDataManage,imgSettings,imgTips,imgExit;
    private TextView total_income,total_outcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        helper = new MyHelper(getBaseContext());
        dbprocess2 = new dbProcess2(helper);
        initView();//初始化控件
    }
    /* 切换到前台，重新获取焦点执行的方法*/
    @Override
    protected void onResume(){
        super.onResume();
        CountTotal();//计算总收入、支出
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
        total_income = findViewById(R.id.total_income);
        total_outcome = findViewById(R.id.total_outcome);
//        getIntent()不能乱用
//        String bg = getIntent().getStringExtra("bg");
        }

    private void CountTotal() {
        int year=CurrentTime.getCurrentYear(),month,total_I=0,total_O=0;
        for (month = 1; month <= 12; month++) {
            String sql1 = "select sum(income.money) as money from income where name ='" + LoginActivity.name + "' and date like '%" + year + "-" + month + "-%'";
            String sql2 = "select sum(outcome.money) as money from outcome where name ='" + LoginActivity.name + "' and date like '%" + year + "-" + month + "-%'";
            total_I += dbprocess2.queryMoney(sql1);
            total_O += dbprocess2.queryMoney(sql2);
        }
        total_income.setText("总收入:" + total_I);
        total_outcome.setText("总支出：" + total_O);
        System.out.println("总收入：" + total_I + "\n总支出：" + total_O);
    }

    //设置壁纸函数
    private void setBg(String bg) {
        if (bg.equals("white")){
            glMenu.setBackgroundResource(R.color.white);
        }
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