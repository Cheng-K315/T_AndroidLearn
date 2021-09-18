package com.example.myapplication5.Service;
/* 统计当前月份收支来源（分类），包括金额和占比*/
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication5.Bean.Income;
import com.example.myapplication5.Bean.Outcome;
import com.example.myapplication5.Bean.ShanData;
import com.example.myapplication5.Bean.Tips;
import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.LoginActivity;
import com.example.myapplication5.R;
import com.example.myapplication5.Service.CountIncomeByMonth;
import com.example.myapplication5.Utils.CurrentTime;
import com.example.myapplication5.View.ShanView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManageActivity extends MyActivity implements View.OnClickListener{
int[] myOut = new int[4]; // 支出有四项（餐饮、交通、娱乐、购物）
int[] myIn = new int[5];  // 收入有四项 （外快、兼职、工资、津贴）
    int currentMonth = CurrentTime.getCurrentMonth();
    int currentYear = CurrentTime.getCurrentYear();
     ShanView shanView,shanView2;
     Button btnCountIncomeByMonth,btnCountOutcomeByMonth,btn3,btn4;
     LinearLayout llFunction;
     TextView tvTip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manage);
//        初始化数据
        initOut();
        initIn();
        initView();
        /* ShanView shanView = UIUtil.findView(view, R.id.shanView); */
        shanView = findViewById(R.id.shanView_countOutcomeByClasses);
        shanView.setMaxNum(5);// 设置可以显示的最大数值 该数值之后的会合并为其他
        shanView.setRadius(205);// 设置圆盘半径
        shanView.setTextDescript("本月支出");
        initShanViewMyOutcome();

        shanView2 = findViewById(R.id.shanView_countIncomeByClasses);
        shanView2.setMaxNum(5);// 设置可以显示的最大数值 该数值之后的会合并为其他
        shanView2.setRadius(205);// 设置圆盘半径
        shanView2.setTextDescript("本月收入");
        initShanViewMyIncome();
    }

    private void initView() {
        btnCountIncomeByMonth = findViewById(R.id.btn_countIncomeByMonth);
        btnCountOutcomeByMonth = findViewById(R.id.btn_countOutcomeByMonth);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btnCountIncomeByMonth.setOnClickListener(this);
        btnCountOutcomeByMonth.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        tvTip = findViewById(R.id.tv_tip);
    }


    // 给list<bean>赋值
    private void initShanViewMyOutcome() {

        ArrayList<ShanData> listData = new ArrayList<ShanData>();
        listData.clear();
        ShanData shanData = new ShanData();// 数据实体类
        shanData.setData(myOut[0]);
        shanData.setText("餐饮");
        listData.add(shanData);

        shanData = new ShanData();
        shanData.setData(myOut[1]);
        shanData.setText("交通");
        listData.add(shanData);

        shanData = new ShanData();
        shanData.setData(myOut[2]);
        shanData.setText("娱乐");
        listData.add(shanData);

        shanData = new ShanData();
        shanData.setData(myOut[3]);
        shanData.setText("购物");
        listData.add(shanData);
        shanView.setData(listData);// 将list数据传递到view中
    }

    private void initShanViewMyIncome() {
        ArrayList<ShanData> listData = new ArrayList<ShanData>();
        listData.clear();
        ShanData shanData = new ShanData();// 数据实体类
        shanData.setData(myIn[0]);
        shanData.setText("外快");
        listData.add(shanData);

        shanData = new ShanData();
        shanData.setData(myIn[1]);
        shanData.setText("兼职");
        listData.add(shanData);

        shanData = new ShanData();
        shanData.setData(myIn[2]);
        shanData.setText("工资");
        listData.add(shanData);

        shanData = new ShanData();
        shanData.setData(myIn[3]);
        shanData.setText("津贴");
        listData.add(shanData);
        shanView2.setData(listData);// 将list数据传递到view中
    }
//  支出分类数据键值对
    private void initOut() {
        myOut[0] = CountOutcomeByClass(myOut,"餐饮");
        myOut[1] = CountOutcomeByClass(myOut,"交通");
        myOut[2] = CountOutcomeByClass(myOut,"娱乐");
        myOut[3] = CountOutcomeByClass(myOut,"购物");
    }
//  收入分类数据键值对
    private void initIn() {
        myIn[0] = CountIncomeByClasses(myIn,"外快");
        myIn[1] = CountIncomeByClasses(myIn,"兼职");
        myIn[2] = CountIncomeByClasses(myIn,"工资");
        myIn[3] = CountIncomeByClasses(myIn,"津贴");
    }


    //统计个人支出数据
    private int CountOutcomeByClass(int[] myOut,String classes) {
/*        select class,sum(outcome.money) as money from outcome where name = '小明' group by class;
        简化版 select sum(outcome.money) as money from outcome where name = '小明' and class = '餐饮';*/

        String sql = "select sum(outcome.money) as money from outcome where class = '"+classes+"' and name = '"+ LoginActivity.name +"' and date like '%"+currentYear+"-"+currentMonth+"-%'";
        System.out.println(sql);
        int count = dbprocess2.queryMoney(sql);
        System.out.println(classes+"的支出："+ count);
        return count;
    }
//    统计个人收入数据
    private int CountIncomeByClasses(int[] myIn,String classes) {
        String sql = "select sum(income.money) as money from income where class = '"+classes+"' and name = '"+ LoginActivity.name +"'and date like '%"+currentYear+"-"+currentMonth+"-%'";
        System.out.println(sql);
        int count = dbprocess2.queryMoney(sql);
        System.out.println(classes+"的收入："+ count);
        return count;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.btn_countIncomeByMonth:
                intent.setClass(this, CountIncomeByMonth.class);
                startActivity(intent);
                break;
            case R.id.btn_countOutcomeByMonth:
                intent.setClass(this, CountOutcomeByMonthActivity.class);
                startActivity(intent);
                break;
            case R.id.btn3:
                if(btn3.isSelected()){
                    btn3.setSelected(false);
                    shanView2.setVisibility(View.GONE);
                }else{
                    btn3.setSelected(true);
                    shanView2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn4:
                if(btn4.isSelected()){
                    btn4.setSelected(false);
                    shanView.setVisibility(View.GONE);
                }else{
                    btn4.setSelected(true);
                    shanView.setVisibility(View.VISIBLE);
                }
                break;
        }
//        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //导入菜单布局
        getMenuInflater().inflate(R.menu.data_manage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            findViewById(R.id.tv_tip).setVisibility(View.GONE);
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.income:
//                Toast.makeText(this, "季节收入", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(this, CountIncomeByMonth.class);
                startActivity(intent);
                break;
            case R.id.outcome:
//                Toast.makeText(this, "季节支出", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent();
                intent2.setClass(this, CountOutcomeByMonthActivity.class);
                startActivity(intent2);
                break;
            case R.id.icome_from:
//                Toast.makeText(this, "收入来源", Toast.LENGTH_SHORT).show();
                if(btn3.isSelected()){
                    btn3.setSelected(false);
                    shanView2.setVisibility(View.GONE);
                }else{
                    btn3.setSelected(true);
                    shanView2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.outcome_to:
//                Toast.makeText(this, "支出方向", Toast.LENGTH_SHORT).show();
                if(btn4.isSelected()){
                    btn4.setSelected(false);
                    shanView.setVisibility(View.GONE);
                }else{
                    btn4.setSelected(true);
                    shanView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tips_manage:
//                Toast.makeText(this, "便签管理", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent();
                intent3.setClass(this, TipsManageActivity.class);
                startActivity(intent3);
                break;
            case R.id.export_database:
//                Toast.makeText(this, "数据导出", Toast.LENGTH_SHORT).show();
                exportToTxt();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportToTxt() {
        exportIncomeToTxt();
        exportOutcomeToTxt();
        exportTipsToTxt();
        Toast.makeText(this, "文件导出成功！请到files文件夹下查看", Toast.LENGTH_SHORT).show();
    }

    private void exportTipsToTxt() {
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql = "select * from tips";
        Cursor cursor = db.rawQuery(sql, null);
        List<Tips> list = new ArrayList<>();
        while(cursor.moveToNext()){
            Tips tips = new Tips();
            tips.set_id(cursor.getString(0));
            tips.setInfo(cursor.getString(2));
            list.add(tips);
            System.out.println((tips.get_id()+","+tips.getInfo()+"\r\n"));
        }
        cursor.close();
        db.close();

        StringBuffer buffer = new StringBuffer();
        buffer.append("id,便签内容\r\n");
        for(Tips tips:list){
            buffer.append(tips.get_id()+","+tips.getInfo()+"\r\n");
        }

//				String data =new String(buffer.toString().getBytes("utf-8"), "ansi") ;
        String data = buffer.toString();
//            System.out.println(111);
        String filename = "我的便签_"+CurrentTime.getCurrentTime()+".txt";
        FileOutputStream fos;
        try {
            fos = openFileOutput(filename,MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
//                System.out.println(111);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportOutcomeToTxt() {
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql = "select * from outcome";
        Cursor cursor = db.rawQuery(sql, null);
        List<Outcome> list = new ArrayList<>();
        while(cursor.moveToNext()){
            Outcome outcome = new Outcome();
            outcome.set_id(cursor.getString(0));
            outcome.setMoney(cursor.getString(2));
            outcome.setDate(cursor.getString(3));
            outcome.setClasses(cursor.getString(4));
            outcome.setLocation(cursor.getString(5));
            outcome.setComment(cursor.getString(6));
            list.add(outcome);
            System.out.println((outcome.get_id()+","+outcome.getMoney()+","+outcome.getDate()+","+outcome.getClasses()+","+outcome.getLocation()+","+outcome.getComment()+"\r\n"));
        }
        cursor.close();
        db.close();

        StringBuffer buffer = new StringBuffer();
        buffer.append("id,金额,时间,类别,地点,备注\r\n");
        for(Outcome outcome:list){
            buffer.append(outcome.get_id()+","+outcome.getMoney()+","+outcome.getDate()+","+outcome.getClasses()+","+outcome.getLocation()+","+outcome.getComment()+"\r\n");
        }

//				String data =new String(buffer.toString().getBytes("utf-8"), "ansi") ;
        String data = buffer.toString();
//            System.out.println(111);
        String filename = "支出账单_"+CurrentTime.getCurrentTime()+".txt";
        FileOutputStream fos;
        try {
            fos = openFileOutput(filename,MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
//                System.out.println(111);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportIncomeToTxt() {
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql = "select * from income";
        Cursor cursor = db.rawQuery(sql, null);
        List<Income> list = new ArrayList<>();
        while(cursor.moveToNext()){
            Income income = new Income();
            income.set_id(cursor.getString(0));
            income.setMoney(cursor.getString(2));
            income.setDate(cursor.getString(3));
            income.setClasses(cursor.getString(4));
            income.setPayer(cursor.getString(5));
            income.setComment(cursor.getString(6));
            list.add(income);
            System.out.println((income.get_id()+","+income.getMoney()+","+income.getDate()+","+income.getClasses()+","+income.getPayer()+","+income.getComment()+"\r\n"));
        }
        cursor.close();
        db.close();

        StringBuffer buffer = new StringBuffer();
        buffer.append("id,金额,时间,类别,付款方,备注\r\n");
        for(Income income:list){
            buffer.append(income.get_id()+","+income.getMoney()+","+income.getDate()+","+income.getClasses()+","+income.getPayer()+","+income.getComment()+"\r\n");
        }

//				String data =new String(buffer.toString().getBytes("utf-8"), "ansi") ;
            String data = buffer.toString();
//            System.out.println(111);
            String filename = "收入账单_"+CurrentTime.getCurrentTime()+".txt";
            FileOutputStream fos;
            try {
                fos = openFileOutput(filename,MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();
//                System.out.println(111);
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

}