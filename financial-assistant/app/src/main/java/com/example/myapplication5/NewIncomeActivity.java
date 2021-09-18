package com.example.myapplication5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.myapplication5.Utils.dbProcess2;

import java.util.Calendar;

public class NewIncomeActivity extends MyActivity implements View.OnClickListener{
//    private MyHelper helper;
//    private dbProcess2 dbprocess2;
    private EditText etMoney,etPayer,etComment;
    TextView tvDate;
    Spinner spinner;
    private Calendar cal;
    private int year,month,day;
    private Button btnSave,btnCancel;
//    private Spinner spClass;
    private AlertDialog dialog;
    private String classes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);
        // 初始化下拉列表
        initSpinner();
        initView();
//        helper = new MyHelper(getBaseContext());
//        dbprocess2 = new dbProcess2(helper);
//        btnSave = findViewById(R.id.btn_save);
//        btnSave.setOnClickListener(this);
    }

    private void initView() {
        etMoney = findViewById(R.id.et_money);
        tvDate = findViewById(R.id.tv_time);
        etPayer = findViewById(R.id.et_payer);
        etComment = findViewById(R.id.et_comment);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        btnCancel =findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        dialog = new AlertDialog.Builder(NewIncomeActivity.this).setTitle("系统提示")
                .setMessage("是否放弃更改？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        clearText();
//                        Intent intent = new Intent(NewIncomeActivity.this,MainScreenActivity.class);
//                        startActivity(intent);
//                        finish 能顺便清空表单元素
                        finish();
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
                DatePickerDialog dialog=new DatePickerDialog(NewIncomeActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
            }
        });
    }

//    清空表单元素
    private void clearText() {
        etMoney.setText("");
        tvDate.setText("");
        etPayer.setText("");
        etComment.setText("");
    }

    //获取当前日期
    private void getDate() {
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);    //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);  //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                String name=LoginActivity.name;
                String money=etMoney.getText().toString().trim(); //1000.00
//                String classes="工资";
                String payer=etPayer.getText().toString().trim(); //Boss
                String comment=etComment.getText().toString().trim(); //无备注
                String date=tvDate.getText().toString().trim(); //2020-12-12
                /*判断表单输入是否为空*/
                if(StringUtils.isBlank(money)||StringUtils.isBlank(date)||StringUtils.isBlank(payer)||StringUtils.isBlank(comment)||StringUtils.isBlank(classes)){
                    Toast.makeText(this, "表单内容不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                /*控制输入金额为整数*/
                if(!StringUtils.isInt(money)){
                    Toast.makeText(this, "金额必须为整数", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sql = "insert into income(name,money,date,class,payer,comment) values('"+name+"','"+money+"','"+date+"','"+classes+"','"+payer+"','"+comment+"')";
                System.out.println(sql);
                dbprocess2.execSql(sql);
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancel:
                dialog.show();

        }
    }

    //    Spinner 下拉列表数据加载 样式、监听设置
    public void initSpinner(){
        // 初始化控件
        spinner = (Spinner) findViewById(R.id.sp_classes);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.InCome);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] InCome = getResources().getStringArray(R.array.InCome);
//                获取类别
                classes = InCome[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

}