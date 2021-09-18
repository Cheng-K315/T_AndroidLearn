package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.Utils.StringUtils;

public class SettingsActivity extends MyActivity implements View.OnClickListener{
    RelativeLayout rl;
    LinearLayout llNewPassword,llMoreColor;
    private EditText etPassword;
    private Button btnSave;
    private RelativeLayout MoreBg,MoreVersion,MoreCache,MoreShare,MorePassword;
    private TextView tvPink,tvGreen,tvBlue;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        sp = getSharedPreferences("data",MODE_PRIVATE);
    }
    private void initView() {
        rl = findViewById(R.id.rl);
        MoreBg = findViewById(R.id.rl_more_bg);
        MoreVersion = findViewById(R.id.rl_more_version);
        MoreCache = findViewById(R.id.rl_more_cache);
        MoreShare = findViewById(R.id.rl_more_share);
        MorePassword = findViewById(R.id.rl_more_password);
        MoreBg.setOnClickListener(this);
        MoreVersion.setOnClickListener(this);
        MoreCache.setOnClickListener(this);
        MoreShare.setOnClickListener(this);
        MorePassword.setOnClickListener(this);
        llNewPassword = findViewById(R.id.ll_new_password);
        etPassword = findViewById(R.id.et_new_password);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        llMoreColor = findViewById(R.id.ll_more_color);
        tvPink = findViewById(R.id.pink_bg);
        tvGreen = findViewById(R.id.green_bg);
        tvBlue = findViewById(R.id.blue_bg);
        tvPink.setOnClickListener(this);
        tvGreen.setOnClickListener(this);
        tvBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rl_more_bg:
                if(MoreBg.isSelected()){
                    MoreBg.setSelected(false);
                    llMoreColor.setVisibility(View.VISIBLE);
                }else{
                    MoreBg.setSelected(true);
                    llMoreColor.setVisibility(View.GONE);
                }

                break;
            case R.id.rl_more_version:
                break;
            case R.id.rl_more_cache:
                String sql1 = "delete from tips";
                String sql2 = "delete from income";
                String sql3 = "delete from outcome";
                dbprocess2.execSql(sql1);
                dbprocess2.execSql(sql2);
                dbprocess2.execSql(sql3);
                System.out.println("缓存已清除");
                Toast.makeText(this,"缓存已清除",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_more_share:
                break;
            case R.id.rl_more_password:
//                gone 不占位 invisible 占位
                if(v.isSelected()){
//                    点击显示
                    v.setSelected(false);
                    llNewPassword.setVisibility(View.VISIBLE); // int类型
                }else{
//                    再次点击不显示
                    v.setSelected(true);
                    llNewPassword.setVisibility(View.GONE); // int类型
                }
                break;
            case R.id.btn_save:
                System.out.println("1");
                String newPassword = etPassword.getText().toString().trim();
                //查询原密码
//                String sql1 = "select password from user where name='小明'";
//                String oldPassword = dbprocess2.queryOne(sql1);
                String oldPassword = LoginActivity.passwd;
                if(StringUtils.isBlank(newPassword)){
                    Toast.makeText(this,"新密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(oldPassword.equals(newPassword)){
                    // 新旧密码相同
                    Toast.makeText(this,"新密码不能与原密码相同",Toast.LENGTH_SHORT).show();
                }else{
                    // 修改密码功能
                    String sql = "update user set password='"+ newPassword+"' where name = '小明'";
                    dbprocess2.execSql(sql);
                    Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pink_bg:
                Intent intent1 = new Intent(this,MainScreenActivity.class);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("bg_prefs","pink");
                editor.commit();
                startActivity(intent1);
                break;
            case R.id.green_bg:
                Intent intent2 = new Intent(this,MainScreenActivity.class);
                SharedPreferences.Editor editor1 = sp.edit();
                editor1.putString("bg_prefs","green");
                editor1.commit();
                startActivity(intent2);
                break;
            case R.id.blue_bg:
                Intent intent3 = new Intent(this,MainScreenActivity.class);
                SharedPreferences.Editor editor2 = sp.edit();
                editor2.putString("bg_prefs","blue");
                editor2.commit();
                startActivity(intent3);
                break;
        }
    }
}