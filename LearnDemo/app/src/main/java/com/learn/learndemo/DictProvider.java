package com.learn.learndemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DictProvider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS = 1;
    private static final int WORD = 2;
    private MyDatabaseHelper dbOpenHelper;
    static {
        //为UriMatcher注册两个Uri
        matcher.addURI(Words.AUTHORITY,"words",WORDS);
        matcher.addURI(Words.AUTHORITY,"word/#",WORD);
    }

    //第一次调用该DictProvider时，系统先创建DictProvider对象，并回调该方法
    @Override
    public boolean onCreate() {
        dbOpenHelper = new MyDatabaseHelper(this.getContext(),Words.DB_NAME,null, Words.DB_VERSION);
        return true;
    }

    //查询数据的方法
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String where, @Nullable String[] whereArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            //如果Uri参数代表操作全部数据项
            case WORDS :
                return db.query(Words.TABLE_NAME,projection,where,whereArgs,null,null,sortOrder);
                //如果Uri参数代表操作指定数据项
            case WORD :
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;
                //如果原来的where子句存在，拼接where句子
                if (where != null && !"".equals(where)){
                    whereClause = whereClause + " and " + where;
                }
                return db.query(Words.TABLE_NAME,projection,whereClause,whereArgs,null,null,sortOrder);
        }
        return null;
    }

    //返回指定Uri参数对应的数据的MIME类型
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            //操作的数据是多项数据
            case WORDS:
                return "vnd.android.cursor.dir/com.learn.demo.dict";
            //操作的数据是单项数据
            case WORD:
                return "vnd.android.cursor.item/com.learn.demo.dict";
        }
        return null;
    }

    //插入数据的方法
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //获得数据库实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                //插入数据，返回插入的ID
                long rowID = db.insert(Words.TABLE_NAME,Words.Word._ID,values);
                //如果插入成功返回Uri
                if(rowID>0){
                    //在已有的Uri后面追加ID
                    Uri newUri = ContentUris.withAppendedId(uri,rowID);
                    //通知数据已经改变
                    getContext().getContentResolver().notifyChange(newUri,null);
                    return newUri;
                }
                return null;
    }

    //删除数据的方法
    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        //记录所删除的记录数
        int num = 0;
        switch (matcher.match(uri)){
            //如果Uri参数代表操作全部数据项
            case WORDS :
                num = db.delete(Words.TABLE_NAME,where,whereArgs);
                break;
            case WORD :
                //如果Uri参数代表操作指定数据项
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;
                //如果原来的where子句存在，拼接where句子
                if(where!=null && !"".equals(where)){
                    whereClause = whereClause + " and " + where;
                }
                num = db.delete(Words.TABLE_NAME,whereClause,whereArgs);
                break;
        }
        //通知数据已经改变
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }

    //更新数据的方法
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        //纪录修改的记录数
        int num = 0;
        switch(matcher.match(uri)){
            //如果Uri参数代表操作全部数据项
            case WORDS :
                num = db.update(Words.TABLE_NAME,values,where,whereArgs);
                break;
                //如果Uri参数代表操作指定数据项
            case WORD :
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;
                //如果原来的where存在，拼接where句子
                if (where!=null && !"".equals(where)){
                    whereClause = whereClause + " and " + where;
                }
                num = db.update(Words.TABLE_NAME,values,whereClause,whereArgs);
                break;
        }
        //通知数据已经改变
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }
}