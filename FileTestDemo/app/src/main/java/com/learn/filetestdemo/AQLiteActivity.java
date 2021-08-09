package com.learn.filetestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AQLiteActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView clean;
    private EditText et_title;
    private EditText et_text;
    private EditText et_id;
    private Button btn_insert;
    private Button btn_delete;
    private Button btn_update;
    private Button btn_search;
    private Button btn_searchAll;
    private ListView listView;
    MyDatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_q_lite);
        dh = new MyDatabaseHelper(this,"my.db3",null,1);
        bindViews();
    }

    private void bindViews() {
        clean = (TextView)findViewById(R.id.clean);
        et_text = (EditText) findViewById(R.id.et_text);
        et_title = (EditText)findViewById(R.id.et_title);
        et_id = (EditText)findViewById(R.id.et_id);
        btn_insert = (Button)findViewById(R.id.btn_insert);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_update = (Button)findViewById(R.id.btn_update);
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_searchAll = (Button)findViewById(R.id.btn_searchAll);
        listView = (ListView) findViewById(R.id.listview);

        clean.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_searchAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db = dh.getReadableDatabase();
        String id = et_id.getText().toString();
        switch (v.getId()){
            case R.id.clean:
                et_id.setText("");
                et_title.setText("");
                et_text.setText("");
                break;
            case R.id.btn_insert:
                String title = et_title.getText().toString().trim();
                String text = et_text.getText().toString().trim();
                ContentValues values = new ContentValues();
                values.put("news_title",title);
                values.put("news_content",text);
                db.insert("news",null,values);
                Toast.makeText(getApplicationContext(),"插入数据成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                db.beginTransaction();
                ContentValues upid = new ContentValues();
                upid.put("_id","_id"+(-1));
                try {
                    db.delete("news","_id like ?",new String[]{id});
                    db.update("news",upid,"_id not like ?",new String[]{"1"});
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                String text1 = et_text.getText().toString();
                String title1 = et_title.getText().toString();
                ContentValues values1 = new ContentValues();
                values1.put("news_title",title1);
                values1.put("news_content",text1);
                db.update("news",values1,"_id like ?",new String[]{id});
                break;
            case R.id.btn_search:
                Cursor cursor = db.query("news",null,"_id like ?",new String[]{id},null,null,null);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.recycler_view,cursor,
                        new String[]{"_id","news_title","news_content"},
                        new int[]{R.id.tv_id,R.id.tv_title,R.id.tv_text}, Adapter.NO_SELECTION);
                listView.setAdapter(adapter);
                break;
            case R.id.btn_searchAll:
                StringBuilder sb =new StringBuilder();
                Cursor cursor1 = db.query("news",null,null,null,null,null,null);
                SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this,R.layout.recycler_view,cursor1,
                        new String[]{"_id","news_title","news_content"},
                        new int[]{R.id.tv_id,R.id.tv_title,R.id.tv_text}, Adapter.NO_SELECTION);
                listView.setAdapter(adapter1);

                while (cursor1.moveToNext()){
                String id1 = cursor1.getString(cursor1.getColumnIndex("_id"));
                String news_title = cursor1.getString(cursor1.getColumnIndex("news_title"));
                String news_content = cursor1.getString(cursor1.getColumnIndex("news_content"));
                sb.append("id:"+id1+" title:"+news_title+" content:"+news_content+"\n");
            }
                Log.d("AQLite",sb.toString());
                break;
        }
    }
}