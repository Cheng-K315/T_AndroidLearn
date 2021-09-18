package com.example.myapplication5.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.R;

public class AdminActivity extends MyActivity {
    EditText etName, etPassword, etRole;
    Button btnAdd, btnDelete, btnUpdate, btnSearch;
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
        addListener();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        etRole = findViewById(R.id.et_role);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        btnSearch = findViewById(R.id.btn_search);
        tvShow = findViewById(R.id.tvShow);
    }

    private void addListener(){
        /* 添加一名新用户 */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String role = etRole.getText().toString().trim();
                String sql = "insert into user(name,password,role) values('"+name+"','"+password+"','"+role+"')";
                System.out.println(sql);
                dbprocess2.execSql(sql);
                Toast.makeText(AdminActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
        /* 按姓名删除一名用户*/
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String sql = "delete from user where name = '"+name+"'";
                System.out.println(sql);
                dbprocess2.execSql(sql);
                Toast.makeText(AdminActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        /* 按姓名修改密码和权限 */
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String role = etRole.getText().toString().trim();
                String sql1 = "update user set password='"+password+"' where name='"+name+"'";
                String sql2 = "update user set role='"+role+"' where name='"+name+"'";
                System.out.println(sql1);
                dbprocess2.execSql(sql1);
                System.out.println(sql2);
                dbprocess2.execSql(sql2);
                Toast.makeText(AdminActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            }
        });
        /*  查询所有用户 */
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "select * from user ";
                System.out.println(sql);
                Cursor cursor = db.rawQuery(sql,null);
                if(cursor.getCount() == 0){
                    Toast.makeText(AdminActivity.this,"无数据",Toast.LENGTH_SHORT).show();
                }
                else{
                    cursor.moveToFirst();
                    tvShow.setText("('"+cursor.getString(0)+"','"+cursor.getString(1)+"','"+cursor.getString(2)+"')");

                }while(cursor.moveToNext()){
                    tvShow.append("\n--------------------------------");
                    tvShow.append("\n"+"('"+cursor.getString(0)+"','"+cursor.getString(1)+"','"+cursor.getString(2)+"')");
                }
                cursor.close();
                db.close();
            }
        });
    }
}
