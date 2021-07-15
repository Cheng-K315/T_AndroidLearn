package com.learn.learndemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_usersDB = "create table dict(_id integer primary key autoincrement, word , detail)";/*"create table " + Words.TABLE_NAME +
            "(" + Words.Word._ID +
            " integer primary key autoincrement," +Words.Word.WORD+
            " varchar(30)," + Words.Word.DETAIL +
            " varchar(32))";*/

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_usersDB);
        Log.d("MtDatabaseHelper","创建成功!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新表
        db.execSQL("drop table if exists usersDB");
        onCreate(db);
    }
}
