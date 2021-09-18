package com.example.myapplication5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.Utils.MyHelper;
import com.example.myapplication5.Utils.StringUtils;
import com.example.myapplication5.Utils.dbProcess;
import com.example.myapplication5.Utils.dbProcess2;

import java.util.Calendar;

public class NewOutcomeActivity extends MyActivity implements View.OnClickListener{
private String classes = null;
private EditText etMoney,etLocation,etComment;
TextView tvDate;
private Calendar cal;
private int year,month,day;
private Spinner spClass;
private Button btnSave,btnCancel;
private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outcome);
        initView();
    }

    private void initView() {
        etMoney = findViewById(R.id.et_money);
        tvDate = findViewById(R.id.tv_time);
        etLocation = findViewById(R.id.et_location);
        etComment = findViewById(R.id.et_comment);
//        spClass = findViewById(R.id.sp_classes);
        // 初始化Spinner
        initSpinner();
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        dialog = new AlertDialog.Builder(NewOutcomeActivity.this).setTitle("系统提示")
                .setMessage("是否放弃更改？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
//                        Intent intent = new Intent(NewOutcomeActivity.this,MainScreenActivity.class);
//                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .create();
        //获取当前日期
        getDate();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        tvDate.setText(year+"-"+(++month)+"-"+day);   //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(NewOutcomeActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
            }
        });
    }

    //获取当前日期
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);    //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);  //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_save:
                String money=etMoney.getText().toString().trim();//1000
                String date=tvDate.getText().toString().trim();//2020-12-09
//                String classes="吃饭";
                String location=etLocation.getText().toString().trim();//金华酒店
                String comment=etComment.getText().toString().trim();//无备注
                String name = LoginActivity.name;//小明
                /*判断表单输入是否为空*/
                if(StringUtils.isBlank(money)||StringUtils.isBlank(date)||StringUtils.isBlank(location)||StringUtils.isBlank(comment)||StringUtils.isBlank(classes)){
                    Toast.makeText(this, "表单内容不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                /*控制输入金额为整数*/
                if(!StringUtils.isInt(money)){
                    Toast.makeText(this, "金额必须为整数", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sql = "insert into outcome (name,money,date,class,location,comment) values('"+name+"','"+money+"','"+date+"','"+classes+"','"+location+"','"+comment+"')";
//                System.out.println(sql);
                dbprocess2.execSql(sql);
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
//                String sql2 = "delete from outcome where money = 1";
//                dbprocess2.execSql(sql2);
                break;
            case R.id.btn_cancel:
                dialog.show();
//                Application mApplication = (Application)getApplication();
//                mApplication.getApplicationContext();
                break;
        }
    }

//    Spinner 下拉列表数据加载 样式、监听设置
    public void initSpinner(){
        // 初始化控件
        Spinner spinner = (Spinner) findViewById(R.id.sp_classes);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.outCome);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] outCome = getResources().getStringArray(R.array.outCome);
//                获取类别
                classes = outCome[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

}