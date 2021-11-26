package com.learn.learndemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class FirstProvider extends ContentProvider {

    private static final String TAG = "FirstProvider";

    //实现删除方法，该方法返回被删除的纪录条数
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG,uri.toString() + "===delete方法被调用===");
        Log.d(TAG,"where参数为：" + selection);
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    //该方法的返回值代表了该ContentProvider所提供数据的MIME类型
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //实现插入方法，该方法的返回新插入的纪录的Uri
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG,uri.toString() + "===insert方法被调用===");
        Log.d(TAG,"value参数为：" + values);
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    //第一次创建ContentProvider时调用该方法
    @Override
    public boolean onCreate() {
        Log.d(TAG,"====onCreate方法被调用===");
        return true;
    }

    //实现查询方法，该方法应该返回查询得到的Cursor
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG,uri.toString() + "===query方法被调用===");
        Log.d(TAG,"where参数为：" + selection);
        return null;
    }

    //实现更新方法，该方法返回更新的纪录条数
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG,uri.toString() + "===update方法被调用===");
        Log.d(TAG,"where参数为：" + selection + ", value参数为：" + values);
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }
}