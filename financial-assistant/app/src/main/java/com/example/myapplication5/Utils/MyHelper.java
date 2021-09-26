// 用户表
package com.example.myapplication5.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
//  数据库 构造函数 new一个MyHelper对象时调用
    public MyHelper(Context context) {
        super(context,"myapp.db",null,2);
        System.out.println("数据库连接成功！");
    }

    //    第一次创建时被调用
    /*
    * 只会调用一次
    * 所以数据库更新时要卸载手机上的app
    * onCreate只有在数据库不存在的时候才会执行一次。后续不会再重新创建
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
//        建用户表
        db.execSQL("create table outcome(_id integer primary key,name varchar(20) not null,money varchar(20) not null" +
                ",date varchar(20) not null,class varchar(20) not null,location varchar(20) not null,comment varchar(50) not null)");
        db.execSQL("create table income(_id integer primary key,name varchar(20) not null,money varchar(20) not null" +
                ",date varchar(20) not null,class varchar(20) not null,payer varchar(20) not null,comment varchar(50) not null)");
        db.execSQL("create table tips(_id integer primary key autoincrement,name varchar(20) not null,info varchar(50) not null)");
        db.execSQL("create table user(name varchar(20) primary key not null,password varchar(20) not null,role varchar(20) not null)");
        System.out.println("数据库表创建成功！");
        //数据库创建的同时创建一个管理员
        db.execSQL("insert into user(name,password,role) values('admin','admin','管理员')");
    }

//    数据库更新
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
