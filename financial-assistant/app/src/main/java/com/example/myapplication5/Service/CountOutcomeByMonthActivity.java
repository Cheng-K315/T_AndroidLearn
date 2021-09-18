package com.example.myapplication5.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.LoginActivity;
import com.example.myapplication5.R;
import com.example.myapplication5.Utils.CurrentTime;

public class CountOutcomeByMonthActivity extends MyActivity {
    public static int springSeason,summerSeason,autumnSeason,winterSeason = 0;
    //        春季（3，4，5），夏季（6，7，8），秋季（9，10，11），冬季（12，1，2）
    int year = CurrentTime.getCurrentYear(),month,day;
    TextView tvSpring,tvSummer,tvAutumn,tvWinter;
    //    最大值,目标值Target
    public static int mMax=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(111);
        //        按季度统计
        initData();
        //        先拿到数据再绘图
        setContentView(R.layout.activity_count_outcome_by_month);
        System.out.println(222);
        tvSpring = findViewById(R.id.tv_springOutcome);
        tvSummer = findViewById(R.id.tv_summerOutcome);
        tvAutumn = findViewById(R.id.tv_autumnOutcome);
        tvWinter = findViewById(R.id.tv_winterOutcome);
        tvSpring.setText("春季支出: "+springSeason+"元");
        tvSummer.setText("夏季支出: "+summerSeason+"元");
        tvAutumn.setText("秋季支出: "+autumnSeason+"元");
        tvWinter.setText("冬季支出: "+winterSeason+"元");
    }
    private void initData() {
//        先初始化
        springSeason=0;
        summerSeason=0;
        autumnSeason=0;
        winterSeason = 0;
        for(month=3;month<=5;month++){
            springSeason +=countByMonth(year,month);
        }
        System.out.println("春季支出："+springSeason);
        for(month=6;month<=8;month++){
            summerSeason +=countByMonth(year,month);
        }
        System.out.println("夏季支出："+summerSeason);
        for(month=9;month<=11;month++){
            autumnSeason +=countByMonth(year,month);
        }
        System.out.println("秋季支出："+autumnSeason);
        for(month=1;month<=2;month++){
            winterSeason +=countByMonth(year,month);
        }
        winterSeason +=countByMonth(year,12);
        System.out.println("冬季支出："+winterSeason);
        mMax = springSeason+summerSeason+autumnSeason+winterSeason;
        System.out.println("总支出: "+mMax);
    }
    private int countByMonth(int year,int month){
        String sql = "select sum(outcome.money) as money from outcome where name ='"+ LoginActivity.name +"' and date like '%"+year+"-"+month+"-%'";
        System.out.println(sql);
        int count = dbprocess2.queryMoney(sql);
        System.out.println(month+"月的支出："+ count);
        return count;
    }
}