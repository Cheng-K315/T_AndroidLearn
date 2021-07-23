package com.learn.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private RelativeLayout rl_tab_menu_channel;
    private TextView tab_menu_channel;
    private TextView tab_menu_channel_num;
    private RelativeLayout rl_tab_menu_message;
    private TextView tab_menu_message;
    private TextView tab_menu_message_num;
    private RelativeLayout rl_tab_menu_better;
    private TextView tab_menu_better;
    private TextView tab_menu_better_num;
    private RelativeLayout rl_tab_menu_setting;
    private TextView tab_menu_setting;
    private TextView tab_menu_setting_num;
    private TextView txt_title;
    private MyFragment1 fg1;
    private MyFragment2 fg2;
    private MyFragment3 fg3;
    private MyFragment4 fg4;
    private FragmentManager fManager;
    private FragmentTransaction fragmentTransaction;

    private ViewPager vPager;
    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        fragmentTransaction = fManager.beginTransaction();

        bindViews();
        rl_tab_menu_channel.performClick();//模拟一次点击，既进去后选择第一项
    }



    //UI组件初始化与事件绑定
    private void bindViews() {
        rl_tab_menu_channel = (RelativeLayout) findViewById(R.id.rl_tab_menu_channel);
        tab_menu_channel = (TextView) findViewById(R.id.tab_menu_channel);
        tab_menu_channel_num = (TextView) findViewById(R.id.tab_menu_channel_num);
        rl_tab_menu_message = (RelativeLayout) findViewById(R.id.rl_tab_menu_message);
        tab_menu_message = (TextView) findViewById(R.id.tab_menu_message);
        tab_menu_message_num = (TextView) findViewById(R.id.tab_menu_message_num);
        rl_tab_menu_better = (RelativeLayout) findViewById(R.id.rl_tab_menu_better);
        tab_menu_better = (TextView) findViewById(R.id.tab_menu_better);
        tab_menu_better_num = (TextView) findViewById(R.id.tab_menu_better_num);
        rl_tab_menu_setting = (RelativeLayout) findViewById(R.id.rl_tab_menu_setting);
        tab_menu_setting = (TextView) findViewById(R.id.tab_menu_setting);
        tab_menu_setting_num = (TextView) findViewById(R.id.tab_menu_setting_num);
        txt_title = (TextView)findViewById(R.id.txt_topbar);

        mAdapter = new MyFragmentPagerAdapter(fManager);
        vPager = (ViewPager)findViewById(R.id.v_pager);
        vPager.setAdapter(mAdapter);
        vPager.setCurrentItem(0);
        vPager.addOnPageChangeListener(this);

        rl_tab_menu_channel.setOnClickListener(this);
        rl_tab_menu_message.setOnClickListener(this);
        rl_tab_menu_better.setOnClickListener(this);
        rl_tab_menu_setting.setOnClickListener(this);

    }


    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if(fg1!=null)fragmentTransaction.hide(fg1);
        if (fg2!=null)fragmentTransaction.hide(fg2);
        if (fg3!=null)fragmentTransaction.hide(fg3);
        if (fg4!=null)fragmentTransaction.hide(fg4);
    }

    //重置文本的选中状态
    private void setSelected() {
        tab_menu_channel.setSelected(false);
        tab_menu_message.setSelected(false);
        tab_menu_better.setSelected(false);
        tab_menu_setting.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        hideAllFragment(fragmentTransaction);
        setSelected();
        switch (v.getId()){
            case R.id.rl_tab_menu_channel:
                tab_menu_channel.setSelected(true);
                tab_menu_channel_num.setVisibility(View.INVISIBLE);
              /*  if (fg1 == null){
                    fg1 = new MyFragment1("第一个Fragment页面");
                    fragmentTransaction.add(R.id.my_fragment,fg1);
                }else {
                    fragmentTransaction.show(fg1);
                }   */
                vPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rl_tab_menu_message:
                tab_menu_message.setSelected(true);
                tab_menu_message_num.setVisibility(View.INVISIBLE);
               /* if (fg2 == null){
                    fg2 = new MyFragment2("第二个Fragment页面");
                    fragmentTransaction.add(R.id.my_fragment,fg2);
                }else {
                    fragmentTransaction.show(fg2);
                }  */
                vPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rl_tab_menu_better:
                tab_menu_better.setSelected(true);
                tab_menu_better_num.setVisibility(View.INVISIBLE);
              /*  if (fg3 == null){
                    fg3 = new MyFragment3("第三个Fragment页面");
                    fragmentTransaction.add(R.id.my_fragment,fg3);
                }else {
                    fragmentTransaction.show(fg3);
                } */
                vPager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.rl_tab_menu_setting:
                tab_menu_setting.setSelected(true);
                tab_menu_setting_num.setVisibility(View.INVISIBLE);
              /*  if (fg4 == null){
                    fg4 = new MyFragment4("第四个Fragment页面");
                    fragmentTransaction.add(R.id.my_fragment,fg4);
                }else {
                    fragmentTransaction.show(fg4);
                }  */
                vPager.setCurrentItem(PAGE_FOUR);
                break;
        }
//        fragmentTransaction.commit();
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2){
            setSelected();
            switch (vPager.getCurrentItem()){
                case PAGE_ONE:
                    tab_menu_channel.setSelected(true);
                    tab_menu_channel_num.setVisibility(View.INVISIBLE);
                    txt_title.setText(tab_menu_channel.getText().toString());
                    break;
                case PAGE_TWO:
                    tab_menu_message.setSelected(true);
                    tab_menu_message_num.setVisibility(View.INVISIBLE);
                    txt_title.setText(tab_menu_message.getText().toString());
                    break;
                case PAGE_THREE:
                    tab_menu_better.setSelected(true);
                    tab_menu_better_num.setVisibility(View.INVISIBLE);
                    txt_title.setText(tab_menu_better.getText().toString());
                    break;
                case PAGE_FOUR:
                    tab_menu_setting.setSelected(true);
                    tab_menu_setting_num.setVisibility(View.INVISIBLE);
                    txt_title.setText(tab_menu_setting.getText().toString());
                    break;
            }
        }
    }
}