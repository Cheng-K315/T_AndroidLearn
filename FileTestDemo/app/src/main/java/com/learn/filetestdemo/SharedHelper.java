package com.learn.filetestdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SharedHelper {
    private Context mContext;

    public SharedHelper(){

    }

    public SharedHelper(Context context){
        super();
        this.mContext = context;
    }

    public void save(String username,String password){
        SharedPreferences preferences = mContext.getSharedPreferences("msp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //对密码进行MD5加密
        password = MD5.getMD5(password);
        Log.d("SharedHelper",password);

        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
        Toast.makeText(mContext,"信息已写入SharedPreferences中",Toast.LENGTH_SHORT).show();
    }

    public Map<String,String> read(){
        Map<String,String> date = new HashMap<>();
        SharedPreferences sp = mContext.getSharedPreferences("msp",Context.MODE_PRIVATE);
        date.put("username",sp.getString("username",null));
        date.put("password",sp.getString("password",null));
        return date;
    }
}
