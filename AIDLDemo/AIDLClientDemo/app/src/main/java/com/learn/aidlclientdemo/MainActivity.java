package com.learn.aidlclientdemo;

import java.util.List;

import com.learn.aidlservicedemo.IPet;
import com.learn.aidlservicedemo.Person;
import com.learn.aidlservicedemo.Pet;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity
{
    private IPet petService;
    private Button get;
    EditText personView;
    ListView showView;
    private ServiceConnection conn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name
                , IBinder service)
        {
            // 获取远程Service的onBind方法返回的对象的代理
            petService = IPet.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            petService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personView = (EditText) findViewById(R.id.person);
        showView = (ListView) findViewById(R.id.show);
        get = (Button) findViewById(R.id.get);
        // 创建所需绑定的Service的Intent
        Intent intent = new Intent();
        intent.setAction("com.learn.aidlservicedemo.action.COMPLEX_SERVICE");
        intent.setPackage("com.learn.aidlservicedemo");
        // 绑定远程Service
        bindService(intent, conn, Service.BIND_AUTO_CREATE);
        get.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                String personName = personView.getText().toString();
                if (personName == null || personName.equals("")){
                    return;
                }
                try
                {
                    // 调用远程Service的方法
                    List<Pet> pets = petService.getPets(new Person(1,
                            personName, personName)); //①
                    // 将程序返回的List包装成ArrayAdapter
                    ArrayAdapter<Pet> adapter = new ArrayAdapter<Pet>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1, pets);
                    showView.setAdapter(adapter);
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // 解除绑定
        this.unbindService(conn);
    }
}