package com.learn.filetestdemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SDFileHelper {

    private Context mContext;

    public SDFileHelper() {
    }

    public SDFileHelper(Context context) {
        super();
        this.mContext = context;
    }

    //往SD卡写入文件的方法
    public void saveFileToSD(String filename, String filecontent) throws Exception{
        //如果手机已插入SD卡，且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //获取SD卡的外部目录，同时获得SD卡路径
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
            Log.d("Main",""+Environment.getExternalStorageDirectory());
            //这里不用openFileOutput,因为那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            //将String字符串以字节流的形式写入到输出流中
            output.write(filecontent.getBytes());
            //关闭输出流
            output.close();
        }else Toast.makeText(mContext,"SD卡不存在或SD卡不可读写",Toast.LENGTH_SHORT).show();
    }

    //读取SD卡中文件的方法
    public String readFromSD(String filename) throws Exception{
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filename = Environment.getExternalStorageDirectory().getCanonicalFile() + "/" + filename;
            FileInputStream input = new FileInputStream(filename);
            byte[] temp = new byte[1024];

            int len = 0;
            while ((len = input.read(temp))>0){
                sb.append(new String(temp,0,len));
            }
            input.close();
        }
        return sb.toString();
    }
}
