package com.example.myapplication5.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.LoginActivity;
import com.example.myapplication5.MainScreenActivity;
import com.example.myapplication5.R;
import com.example.myapplication5.Utils.CurrentTime;
import com.example.myapplication5.Utils.SPSaveInfo;
import com.example.myapplication5.View.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;


public class CountIncomeByMonth extends MyActivity {
    public static int springSeason,summerSeason,autumnSeason,winterSeason = 0;
//        春季（3，4，5），夏季（6，7，8），秋季（9，10，11），冬季（12，1，2）
    int year = CurrentTime.getCurrentYear(),month,day;
    TextView tvSpring,tvSummer,tvAutumn,tvWinter;
//    最大值,目标值Target
    public static int mMax=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        按季度统计
        initData();
//        先拿到数据再绘图
        setContentView(R.layout.activity_count_income_by_month);

        tvSpring = findViewById(R.id.tv_springIncome);
        tvSummer = findViewById(R.id.tv_summerIncome);
        tvAutumn = findViewById(R.id.tv_autumnIncome);
        tvWinter = findViewById(R.id.tv_winterIncome);
        tvSpring.setText("春季收入: "+springSeason+"元");
        tvSummer.setText("夏季收入: "+summerSeason+"元");
        tvAutumn.setText("秋季收入: "+autumnSeason+"元");
        tvWinter.setText("冬季收入: "+winterSeason+"元");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //导入菜单布局
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.enter:
                Toast.makeText(this, "最近一年", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "最近四月", Toast.LENGTH_SHORT).show();
                break;
            case R.id.out:
                Toast.makeText(this, "最近四周", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //  设计表的时候年月日最好剥离出来，方便做数据操作
//    用模糊查询
    public void initData() {
//        先初始化
        springSeason=0;
        summerSeason=0;
        autumnSeason=0;
        winterSeason = 0;
        for(month=3;month<=5;month++){
            springSeason +=countByMonth(year,month);
        }
        System.out.println("春季收入："+springSeason);
        for(month=6;month<=8;month++){
            summerSeason +=countByMonth(year,month);
        }
        System.out.println("夏季收入："+summerSeason);
        for(month=9;month<=11;month++){
            autumnSeason +=countByMonth(year,month);
        }
        System.out.println("秋季收入："+autumnSeason);
        for(month=1;month<=2;month++){
            winterSeason +=countByMonth(year,month);
        }
            winterSeason +=countByMonth(year,12);
        System.out.println("冬季收入："+winterSeason);

        mMax = springSeason+summerSeason+autumnSeason+winterSeason;
        System.out.println("总收入: "+mMax);

//        将总收入存入SharePreferences
        SPSaveInfo.saveTotalIncome(this,"total_income", mMax);
    }

    private int countByMonth(int year,int month){
        String sql = "select sum(income.money) as money from income where name ='"+ LoginActivity.name +"' and date like '%"+year+"-"+month+"-%'";
        System.out.println(sql);
        int count = dbprocess2.queryMoney(sql);
        System.out.println(month+"月的收入："+ count);
        return count;
    }

}