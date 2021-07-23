package com.learn.fragmentdemo;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private MyFragment1 fg1;
    private MyFragment2 fg2;
    private MyFragment3 fg3;
    private MyFragment4 fg4;

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fg1 = new MyFragment1("第一个Fragment页面");
        fg2 = new MyFragment2("第二个Fragment页面");
        fg3 = new MyFragment3("第三个Fragment页面");
        fg4 = new MyFragment4("第四个Fragment页面");
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        System.out.println("position add" + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        System.out.println("position destroy" + position);
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case MainActivity.PAGE_ONE:
                fragment = fg1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = fg2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = fg3;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = fg4;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
