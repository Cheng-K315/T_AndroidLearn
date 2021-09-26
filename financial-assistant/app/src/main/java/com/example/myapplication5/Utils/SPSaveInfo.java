package com.example.myapplication5.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SPSaveInfo {
    /* 以键值对的形式存入用户名和密码 */
    public static boolean saveUserInfo(Context context,String name,String password){
        SharedPreferences sp = context.getSharedPreferences("data2",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name,password);
        edit.commit();
        return true;
    }
    /* 通过key=name来查询value也就是password*/
    public static String getUserInfo(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences("data2",Context.MODE_PRIVATE);
        String password = sp.getString(name,null);
        return password;
    }
    /* 以键值对的形式存入总收入 */
    public static  boolean saveTotalIncome(Context context, String key, int total_income){
        SharedPreferences sp = context.getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, total_income);
        editor.commit();
        return true;
    }
    /* 通过key=name来查询总收入也就是total_income*/
    public static int getTotalIncome(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("data2",Context.MODE_PRIVATE);
        int total_income = sp.getInt(key,0);
        return total_income;
    }
}
