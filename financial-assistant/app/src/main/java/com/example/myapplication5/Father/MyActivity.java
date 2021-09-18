package com.example.myapplication5.Father;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication5.Utils.MyHelper;
import com.example.myapplication5.Utils.dbProcess;
import com.example.myapplication5.Utils.dbProcess2;

/* 父类activity */
public class MyActivity extends AppCompatActivity {
//    proteced的属性子类才能继承过去
    protected MyHelper helper;
    protected dbProcess2 dbprocess2;
/* 配置数据库启动类和操作类 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new MyHelper(getBaseContext());
        dbprocess2 = new dbProcess2(helper);
    }


}