//注释方式一 : Ctrl+/
/* 注释方式二 一般多行注释 Ctrl+Shift+/ */
// 数据库操作类
package com.example.myapplication5.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

public class dbProcess {
    //    启动类
    private MyHelper helper;

    public dbProcess(MyHelper helper) {
        this.helper = helper;
    }

    //    插入数据
/*    ?
    ?
    今天下午思考一下能否找到插入参数为sql语句,以便实现多个表的统一化操作,这样就只需要一个
    数据库操作类dbProcess
    12/14 下午已解决
    ?
    ?*/
    public long insert(String table,String user, String password) {
//        获取数据库
        SQLiteDatabase db = helper.getWritableDatabase();
//        类似于打包，bundle
        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("password", password);
//        插入表
//        id是表格默认生成的记录序号,标明第几条记录
        long id = db.insert(table, null, values);
//        System.out.println(id);
        db.close();
        return id;
    }

    //    更新数据
    public int update(String user, String password) {
        //        获取数据库
        SQLiteDatabase db = helper.getWritableDatabase();
//        类似于打包，bundle
        ContentValues values = new ContentValues();
//        values.put("user",user);
        values.put("password", password);
//        插入表
        int number = db.update("user", values, "user=?", new String[]{user});
        db.close();
        return number;
    }

    //    删除所有数据
    public int deleteAll() {
        //        获取数据库
        SQLiteDatabase db = helper.getWritableDatabase();
//        参数设置为null可删除所有数据
        int number = db.delete("user", null, null);
        db.close();
        return number;
    }

    //    删除一条数据
    public int delete(String user) {
        //        获取数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        int number = db.delete("user", "user=?", new String[]{user});
        db.close();
        return number;
    }

    //    查询一条数据
    public boolean query(String user,TextView mTvShow,Context context) {
        //        获取数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("user", null, "user=?", new String[]{user}, null, null, null);
        if(cursor.getCount() == 0){
            mTvShow.setText("");
            Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
        }else{
//            取出第一条记录
            cursor.moveToFirst();
//            下标从0开始
            mTvShow.setText("user : " + cursor.getString(0) + " ；  password : " + cursor.getString(1));
        }
        cursor.close(); //关闭游标
        db.close();
        return true;
    }

    //    查询所有数据
    public int queryAll(TextView mTvShow, Context context) {
        //        获取数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        int number = cursor.getCount();
        if (cursor.getCount() == 0) {
            mTvShow.setText("");
            Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();
//            下标从0开始
            mTvShow.setText("user : " + cursor.getString(0) + " ；  password : " + cursor.getString(1));
        }
        while (cursor.moveToNext()) {
            mTvShow.append("\n" + "user : " + cursor.getString(0) + " ；  password : " + cursor.getString(1));
        }
        cursor.close();
        db.close();
        return number;
    }
}


