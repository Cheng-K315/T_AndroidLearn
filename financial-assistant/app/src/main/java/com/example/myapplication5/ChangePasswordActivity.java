package com.example.myapplication5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.Utils.StringUtils;

public class ChangePasswordActivity extends MyActivity implements View.OnClickListener{

    private EditText old_ps, new_ps, again_ps;
    private Button btn_commit,btn_cancel;
    private ImageButton img_look1,img_look2;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        old_ps = findViewById(R.id.old_ps);
        new_ps = findViewById(R.id.new_ps);
        again_ps = findViewById(R.id.again_ps);
        btn_commit = findViewById(R.id.btn_commit_ps);
        btn_cancel = findViewById(R.id.btn_cancel_ps);
        img_look1 = findViewById(R.id.img_look1);
        img_look2 = findViewById(R.id.img_look2);
        img_look1.setOnClickListener(this);
        img_look2.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


        dialog = new AlertDialog.Builder(ChangePasswordActivity.this).setTitle("系统提示")
                .setMessage("是否放弃更改？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消",null)
                .create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_look1:
                if(img_look1.isSelected()){
                    img_look1.setSelected(false);
                    img_look1.setBackgroundResource(R.drawable.unlook); //不可见
                    new_ps.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //不可见
                    String str = new_ps.getText().toString().trim();
                    new_ps.extendSelection(str.length());
                }else{
                    img_look1.setSelected(true);
                    img_look1.setBackgroundResource(R.drawable.look); //可见
                    new_ps.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); //可见
                    String str = new_ps.getText().toString().trim();
                    new_ps.extendSelection(str.length());
                }
                break;
            case R.id.img_look2:
                if(img_look2.isSelected()){
                    img_look2.setSelected(false);
                    img_look2.setBackgroundResource(R.drawable.unlook); //不可见
                    again_ps.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //不可见
                    String str = again_ps.getText().toString().trim();
                    again_ps.extendSelection(str.length());
                }else{
                    img_look2.setSelected(true);
                    img_look2.setBackgroundResource(R.drawable.look); //可见
                    again_ps.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); //可见
                    String str = again_ps.getText().toString().trim();
                    again_ps.extendSelection(str.length());
                }
                break;
            case R.id.btn_commit_ps:
                String oldPassword = LoginActivity.passwd;
                String oldPs = old_ps.getText().toString().trim();
                String newPs = new_ps.getText().toString().trim();
                String againPs = again_ps.getText().toString().trim();
                if (StringUtils.isBlank(oldPs) || StringUtils.isBlank(newPs) || StringUtils.isBlank(againPs)){
                    Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }else if (!oldPs.equals(oldPassword)){
                    Toast.makeText(this,"原密码错误！",Toast.LENGTH_SHORT).show();
                }
                else if (!newPs.equals(againPs)){
                    Toast.makeText(this,"两次输入不一致！",Toast.LENGTH_SHORT).show();
                }else if (newPs.equals(oldPassword)){
                    Toast.makeText(this,"新密码不能与原密码相同！",Toast.LENGTH_SHORT).show();
                }else {
                    // 修改密码功能
                    String sql = "update user set password='"+ newPs +"' where name = '" + LoginActivity.name + "'";
                    dbprocess2.execSql(sql);
                    Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.btn_cancel_ps:
                dialog.show();
                break;
        }
    }
}