package com.learn.learndemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FourthActivity extends BaseActivity {

    private Button mBtn_insert;
    private Button mBtn_searcher;
    private EditText mEt_id;
    private EditText mEt_word;
    private EditText mEt_detail;
    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        mBtn_insert = findViewById(R.id.btn_insert);
        mBtn_searcher = findViewById(R.id.btn_searcher);
        mEt_id = findViewById(R.id.et_id);
        mEt_word = findViewById(R.id.et_word);
        mEt_detail = findViewById(R.id.et_detail);
        contentResolver = getContentResolver();
//        getContentResolver().registerContentObserver(Uri.parse("content://" + Words.AUTHORITY), true, new WordObserver(this,new Handler()));

        mBtn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入
                String word = mEt_word.getText().toString();
                String detail = mEt_detail.getText().toString();
                //插入单词纪录
                ContentValues values = new ContentValues();
                values.put(Words.Word.WORD,word);
                values.put(Words.Word.DETAIL,detail);
                contentResolver.insert(Words.Word.DICT_CONTENT_URI,values);
                Toast.makeText(FourthActivity.this,"添加单词成功",Toast.LENGTH_SHORT).show();
            }
        });

        mBtn_searcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入
                String id = mEt_id.getText().toString();
                //执行查询
                Cursor cursor = contentResolver.query(Words.Word.DICT_CONTENT_URI,null,"word like ? or detail like ?",new String[]{"%" + id + "%","%" + id + "%"},null);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",converCursorToList(cursor));

                Intent intent = new Intent(FourthActivity.this, ResultActivity.class);
                intent.putExtra("bundle",bundle);
                Log.d("FourthActivity",""+converCursorToList(cursor));
//                startActivity(intent);
            }
        });
    }

    private ArrayList<Map<String,String >> converCursorToList(Cursor cursor){
        ArrayList<Map<String ,String >> result = new ArrayList<>();
        //遍历Cursor结果集
        while (cursor.moveToNext()){
            //将结果集中的数据存入ArrayList中
            Map<String,String> map = new HashMap<>();
            //取出查询记录中的第2列、第3列的值
            map.put(Words.Word.WORD,cursor.getString(1));
            map.put(Words.Word.DETAIL,cursor.getString(2));
            result.add(map);
        }
        return result;
    }
}