package com.example.myapplication5;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.Utils.MyBaseAdapter;
import com.example.myapplication5.Utils.StringUtils;

public class MyOutcomeActivity extends MyActivity {
    private ListView mListView;
    String[] id = new String[100];
    String[] content = new String[100];
    int index = 0;
    AlertDialog dialog;
    String id_selected=null;//选中记录的id
    MyBaseAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent();
    }

    private void loadContent() {
        setContentView(R.layout.activity_my_outcome);
        mListView = findViewById(R.id.lv2);
        initData();
        mAdapter = new MyBaseAdapter(id,content,this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                id_selected = id[position];
//                Toast.makeText(MyIncomeActivity.this,id[position],Toast.LENGTH_SHORT).show();
                if(!StringUtils.isBlank(id_selected)){
//                    id不为空表示有数据，这时才显示删除提示
                    dialog.show();
                }
            }
        });
        dialog = new AlertDialog.Builder(MyOutcomeActivity.this).setTitle("系统提示")
                .setMessage("是否删除此条记录？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       删除一条记录
                        String sql = "delete from outcome where _id="+id_selected;
                        System.out.println(sql);
                        dbprocess2.execSql(sql);
                        dbprocess2.execSql("update outcome set _id=_id-1 where _id>"+id_selected);
                        Toast.makeText(MyOutcomeActivity.this,"删除成功，请刷新页面查看",Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("取消",null)
                .create();
    }

    private void initData() {
        String sql = "select * from outcome where name ='"+LoginActivity.name+"'";
        System.out.println(sql);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"没有数据",Toast.LENGTH_SHORT).show();
        }else{
            cursor.moveToFirst();
            System.out.println(111);
//            为何定义inter类型查询出来还是String???
            id[index] = cursor.getString(0);
            content[index] = cursor.getString(2)+"元,"+cursor.getString(3)+","+cursor.getString(4)+","+cursor.getString(5)+","+cursor.getString(6);
            index++;
        }while(cursor.moveToNext()){
            id[index] = cursor.getString(0);
            content[index] = cursor.getString(2)+"元,"+cursor.getString(3)+","+cursor.getString(4)+","+cursor.getString(5)+","+cursor.getString(6);
            System.out.println("id:"+id[index]+",content:"+content[index]);
            index++;
        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //导入菜单布局
        getMenuInflater().inflate(R.menu.my_income, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.refresh:
                Toast.makeText(this,"刷新页面",Toast.LENGTH_SHORT).show();
//                明天接着做
                mListView.invalidate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}