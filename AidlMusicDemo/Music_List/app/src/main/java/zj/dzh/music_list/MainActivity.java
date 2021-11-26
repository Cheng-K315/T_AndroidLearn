package zj.dzh.music_list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout content;
    private TextView tv1;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置textview控件背景为半透明
        TextView textView = findViewById(R.id.textView);
        textView.getBackground().setAlpha(100);
        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
        content = (FrameLayout) findViewById(R.id.content);
        fm = getSupportFragmentManager();//若是继承FragmentActivity，fm=getFragmentManger();
        ft = fm.beginTransaction();
        ft.replace(R.id.content, new frag1());//默认情况下Fragment1
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        ft.commit();
    }
}
