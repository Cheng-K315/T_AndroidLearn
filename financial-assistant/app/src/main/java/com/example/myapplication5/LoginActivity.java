package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.Service.AdminActivity;
import com.example.myapplication5.Utils.SPSaveInfo;
import com.example.myapplication5.Utils.StringUtils;

public class LoginActivity extends MyActivity implements View.OnClickListener{
/* 保存登录信息 */
public static String passwd = null; //保存登陆密码
public static String name = null; //保存登录用户
public static String role = null; //角色 管理员/用户
     EditText etName,etPassword;
private Button btnLogin,btnRegister;
private ImageButton imgLook;
private ImageView imgHead;
//private Spinner spRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        控件初始化
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        imgLook = findViewById(R.id.img_look);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        imgLook.setOnClickListener(this);
        imgHead = findViewById(R.id.img_head);
//        下拉列表初始化
        initSpinner();
//        焦点改变，自动填写密码 前提是不改密码，否则又得手动输入
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//              头像转一圈
                rotate(imgHead);
                if(hasFocus){
                    String password = SPSaveInfo.getUserInfo(LoginActivity.this,etName.getText().toString().trim());
                    etPassword.setText(password);
                }
            }
        });
//        创建一个管理员
//        String sql = "insert into user(name,password,role) values('admin','admin','管理员')";
//        dbprocess2.execSql(sql);
    }

//  旋转动画
    private void rotate(View iv) {
        RotateAnimation ta = new RotateAnimation(0, 360, iv.getWidth() / 2, iv.getHeight() / 2);
        // 设置动画播放的时间
        ta.setDuration(1500);
        // 开始播放动画
        iv.startAnimation(ta);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                register();
                break;
            case R.id.img_look:
                if(imgLook.isSelected()){
                    imgLook.setSelected(false);
                    imgLook.setBackgroundResource(R.drawable.unlook); //不可见
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //不可见
                    String str = etPassword.getText().toString().trim();
                    etPassword.extendSelection(str.length());
                }else{
                    imgLook.setSelected(true);
                    imgLook.setBackgroundResource(R.drawable.look); //可见
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); //可见
                    String str = etPassword.getText().toString().trim();
                    etPassword.extendSelection(str.length());

                }
                break;
            case R.id.btn_login:
//                rotate(imgHead);
                Login();
                break;
        }
    }



    private void register() {
        String inputName = etName.getText().toString().trim();
        String inputPassword = etPassword.getText().toString().trim();
        String inputRole = role; // 通过spinner选中事件监听到的
        if(StringUtils.isBlank(inputName)){
//            用户名不能为空
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isBlank(inputPassword)){
//            密码不能为空
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isBlank(inputRole)){
//            角色不能为空
            Toast.makeText(this, "角色尚未选择", Toast.LENGTH_SHORT).show();
            return;
        }
        String getPassword = searchForPassword(inputName);
        if(!getPassword.equals("用户不存在")){
            Toast.makeText(this,"用户名已经被注册，请更换一个",Toast.LENGTH_SHORT).show();
            return;
        }
        if(inputRole.equals("管理员")){
            Toast.makeText(this,"权限不够，新用户只能为普通用户",Toast.LENGTH_SHORT).show();
            return;
        }
        String sql = "insert into user(name,password,role) values('"+inputName+"','"+inputPassword+"','用户')";
        dbprocess2.execSql(sql);
        Toast.makeText(this,"恭喜你注册成功，你现在可以选择登录",Toast.LENGTH_SHORT).show();
    }

    private void Login() {
        /* 提前return可以跳过后面的条件判断*/
        String inputName = etName.getText().toString().trim();
        String inputPassword = etPassword.getText().toString().trim();
        String inputRole = role; // 通过spinner选中事件监听到的
//        可以直接调用的工具类TextUtils.isEmpty(inputName)
        if(StringUtils.isBlank(inputName)){
//            用户名不能为空
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isBlank(inputPassword)){
//            密码不能为空
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isBlank(inputRole)){
//            角色不能为空
            Toast.makeText(this, "角色尚未选择", Toast.LENGTH_SHORT).show();
            return;
        }
        String getPassword = searchForPassword(inputName);
        String getRole = searchForRole(inputName);
        if(getPassword.equals("用户不存在")){
            Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(inputPassword.equals(getPassword)&&inputRole.equals(getRole)){
//            保存登录信息
            passwd = getPassword;
            name = inputName;
//            保存密码
            SPSaveInfo.saveUserInfo(this,name,passwd);
            if(role.equals("管理员")){
            /*管理员界面*/
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                Toast.makeText(this,"管理员登录成功",Toast.LENGTH_SHORT).show();
            }else if(role.equals("用户")){
            /*用户界面*/
            Intent intent = new Intent(this,MainScreenActivity.class);
            startActivity(intent);
            Toast.makeText(this,"用户登录成功",Toast.LENGTH_SHORT).show();
            }

        }
        else if(!inputPassword.equals(getPassword)){
            System.out.println("-----密码错误------");
            Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!inputRole.equals(getRole)){
            System.out.println("-----角色不匹配------");
            Toast.makeText(this,"角色不匹配",Toast.LENGTH_SHORT).show();
            return;
        }
    }
//  根据用户名查询密码
    private String searchForPassword(String name){
        String sql = "select password from user where name ='" + name + "'";
        String getPassword = dbprocess2.queryOne(sql);
        System.out.println("数据库获取到用户密码：('"+name+"','"+getPassword +"')");
        return getPassword;
    }

//    根据用户名查询权限
    private String searchForRole(String name){
        String sql = "select role from user where name ='" + name + "'";
        String getRole = dbprocess2.queryOne(sql);
        System.out.println("数据库获取到用户权限：('"+name+"','"+getRole +"')");
        return getRole;
    }

    //    Spinner 下拉列表数据加载 样式、监听设置
    public void initSpinner(){
        // 初始化控件
        Spinner spinner = (Spinner) findViewById(R.id.sp_role);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.Role);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] Roles = getResources().getStringArray(R.array.Role);
//                if(!StringUtils.isBlank(Roles[pos])){
//                    Toast.makeText(LoginActivity.this, "你选择了:"+Roles[pos], Toast.LENGTH_SHORT).show();
//                }
//                获取类别
                role = Roles[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

}