package com.example.myapplication5.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.myapplication5.Father.MyActivity;
import com.example.myapplication5.LoginActivity;
import com.example.myapplication5.MyOutcomeActivity;
import com.example.myapplication5.R;
import com.example.myapplication5.Utils.StringUtils;

public class TipsManageActivity extends MyActivity implements TextWatcher {
private EditText etSearch;
private TextView tvShow;
private  String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_manage);
        etSearch = findViewById(R.id.et_content);
        etSearch.addTextChangedListener(this);
        tvShow = findViewById(R.id.tv_show);
        String sql = "select * from tips where name='"+ LoginActivity.name +"'";
        dbprocess2.queryTips(sql,tvShow,this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        content = etSearch.getText().toString().trim();
        String sql = "select * from tips where name='"+ LoginActivity.name+"' and info like '%"+content+"%'";
        System.out.println(sql);
        if (!StringUtils.isBlank(content)) {
//            查找包含指定内容
            dbprocess2.queryTips(sql,tvShow,this);
        }
        etSearch.setClickable(true);
        etSearch.setEnabled(true);
        etSearch.setTextColor(Color.BLUE);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}