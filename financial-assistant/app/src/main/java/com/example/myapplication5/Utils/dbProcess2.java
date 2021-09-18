package com.example.myapplication5.Utils;
/* 本来想定义为静态工具类的，由于有传入参数helper，故不这样设置 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class dbProcess2 {
    //    启动类
    private MyHelper helper;
    //    数据库
    private SQLiteDatabase db;

    public dbProcess2(MyHelper helper) {
        this.helper = helper;

    }

    //    每次的数据库对象要重新获取
//    增删改
    public void execSql(String sql) {
        //  获取数据库
        db = helper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    //    查询收支记录
    public void query(String sql, TextView mTvShow, Context context) {
        //  获取数据库
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int number = cursor.getCount();
        if (cursor.getCount() == 0) {
            mTvShow.setText("");
            Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();
//            下标从0开始
//            setText-->append
            mTvShow.append("\n------------------------------");
            mTvShow.setText("\n" + cursor.getString(2) + "元," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + "," + cursor.getString(6));
            mTvShow.append("\n------------------------------");
        }
        while (cursor.moveToNext()) {
            mTvShow.append("\n" + cursor.getString(2) + "元," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + "," + cursor.getString(6) );
            mTvShow.append("\n------------------------------");
        }
        cursor.close();
        db.close();
    }

    //    查询收支便签
    public void queryTips(String sql, TextView mTvShow, Context context) {
        //  获取数据库
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int number = cursor.getCount();
        System.out.println(number+"------");
        if (cursor.getCount() == 0) {
            mTvShow.setText("");
            System.out.println(1111);
            Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"查询到"+number+"条数据",Toast.LENGTH_SHORT).show();
            cursor.moveToFirst();
//            下标从0开始
//            setText-->append
            System.out.println(cursor.getString(0)+cursor.getString(2));
            mTvShow.setText(cursor.getString(0) + "," + cursor.getString(2) + ";");
        }
        while (cursor.moveToNext()) {
            System.out.println(cursor.getString(0)+cursor.getString(2));
            mTvShow.append("\n" + cursor.getString(0) + "," + cursor.getString(2) + ";");
        }
        cursor.close();
        db.close();
    }

    //  根据用户名查询密码或权限
    public String queryOne(String sql) {
        //  获取数据库
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            return "用户不存在";
        } else {
            cursor.moveToFirst();
            /* 这里index为何是0 解答：因为这里获取的是单个列---select password/role */
            return cursor.getString(0);
        }
    }

    //    根据用户名查询收入/支出表 根据月份查询收支表
    public int queryMoney(String sql) {
        int count = 0;
        //  获取数据库
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        System.out.println("查询到的记录条数"+cursor.getCount());
        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            String num = cursor.getString(0);
            if (StringUtils.isBlank(num)){
//                这里需要加个判断是否为空，为啥总会查到一条记录 ？？？
                return 0;
            }
            count = parseInt(num);
            System.out.println("-----" + num + "-----");
        }
        cursor.close();
        db.close();
        return count;
    }

/*
常用数据库sql语句
*  查 ：select * from user where name='小明' and role='用户'
*  删：delete from user where name='小明'
*  改：update user set role='管理员' where name='小明'
*  增：insert into user(name,password,role) values('小明','123','管理员')
*  其他: select sum(income.money) as money from income where classes='工资' order by name
* */
}

