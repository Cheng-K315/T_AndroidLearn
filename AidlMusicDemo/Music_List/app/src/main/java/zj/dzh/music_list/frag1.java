package zj.dzh.music_list;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static java.lang.Integer.parseInt;

public class frag1 extends Fragment {
    private View view;
    public String[] name = {"林俊杰——幸存者", "易烊千玺——陷落美好", "Changes——Justin Bieber","林俊杰——那些你很冒险的梦","林俊杰——交换余生",
            "Zella Day——East Of Eden","Sia——Unstoppable","艾辰——错位时空","Taylor Swift——The Man","薛之谦——被人","周杰伦——等你下课",
            "Intentions——Justin Bieber&Quavo","王嘉尔、林俊杰——过","林俊杰——将故事写成我们"};
    public static int[] icons = {R.drawable.music0, R.drawable.music1, R.drawable.music2,R.drawable.music3,R.drawable.music4,
    R.drawable.music5,R.drawable.music6,R.drawable.music7,R.drawable.music8,R.drawable.music9,R.drawable.music10,R.drawable.music11,
    R.drawable.music12,R.drawable.music13};
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.music_list, null);
        //将list view的每个item项背景设置成半透明
        ListView listView = view.findViewById(R.id.lv);
        listView.getBackground().setAlpha(100);

        MyBaseAdapter adapter = new MyBaseAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(frag1.this.getContext(), Music_Activity.class);//创建Intent对象，启动check，进行页面跳转
                //将数据存入Intent对象
                intent.putExtra("name", name[position]);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }
        });
        return view;
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int i) {
            return name[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view = View.inflate(frag1.this.getContext(), R.layout.item_layout, null);
            TextView tv_name = view.findViewById(R.id.item_name);
            ImageView iv = view.findViewById(R.id.iv);
            tv_name.setText(name[i]);
            iv.setImageResource(icons[i]);
            return view;
        }
    }


}
