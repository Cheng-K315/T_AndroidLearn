package com.example.myapplication5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.Utils.StringUtils;

public class NewTipsActivity extends MyActivity {
    private EditText etComment;
    private Button btnSave;
    private Button btnCancel;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tips);
        etComment = findViewById(R.id.et_comment);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        dialog = new AlertDialog.Builder(NewTipsActivity.this).setTitle("系统提示")
                .setMessage("是否放弃更改？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        etComment.setText("");
//                        Intent intent = new Intent(NewTipsActivity.this,MainScreenActivity.class);
//                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消",null)
                .create();
        // 添加监听，插入数据
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户信息
                String name = LoginActivity.name;
                String comment = etComment.getText().toString().trim();
                if (StringUtils.isBlank(comment)){
                    Toast.makeText(NewTipsActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String sql = "insert into tips(name,info) values ('"+name+"','"+comment+"')";
                System.out.println(sql);
                dbprocess2.execSql(sql);
                Toast.makeText(NewTipsActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
//    初始化界面按钮为保存功能
    @Override
    public void onResume(){
        super.onResume();
        btnSave.setText("保存");
    }


}