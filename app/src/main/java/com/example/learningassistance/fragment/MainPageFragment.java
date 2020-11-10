package com.example.learningassistance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.MineAdapter;
import com.example.learningassistance.entity.Option;
import com.example.learningassistance.utils.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;

public class MainPageFragment extends Fragment{
    private String data;
    private int id;

    /**
     * 碎片的有参构造,完成数据的传递
     * @param data 要传入碎片的数据
     * @param id 用来判断用户想要显示哪个碎片
     */
    public MainPageFragment(String data, int id) {
        this.data = data;
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (id){
            case R.id.menu_homepage:
                view = inflater.inflate(R.layout.fg_homepage, container, false);
                loadHomepageFragment(view);
                break;
            case R.id.menu_message:
                view = inflater.inflate(R.layout.fg_message, container, false);
                loadMessageFragment(view);
                break;
            case R.id.menu_dynamic:
                view = inflater.inflate(R.layout.fg_dynamic, container, false);
                loadDynamicFragment(view);
                break;
            case R.id.menu_my:
                view = inflater.inflate(R.layout.fg_mine, container, false);
                loadMineFragment(view);
                break;
            default:
                break;
        }

        return view;
    }

    public void loadHomepageFragment(View view){
        Toast.makeText(view.getContext(), "正在加载首页", Toast.LENGTH_SHORT).show();
    }

    public void loadMessageFragment(View view){
        Toast.makeText(view.getContext(), "正在加载消息", Toast.LENGTH_SHORT).show();
    }

    public void loadDynamicFragment(View view){
        Toast.makeText(view.getContext(), "正在加载动态", Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载Fragment"我的".
     * @param view "我的"在Activity中的fragment组件
     */
    public void loadMineFragment(View view){
        ImageView imageView = view.findViewById(R.id.mine_avatar);
        RoundRectImageView.setCircle(imageView,R.drawable.icon_user_avater,40,this);

        List<Option> options = initOption();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_mine);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        MineAdapter adapter = new MineAdapter(options,data);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 加载每个选项所需的各种元素
     * @return 返回一个包含所有选项的List集合
     */
    public List<Option> initOption(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("我的信息",R.drawable.icon_user_avater));
        options.add(new Option("我的课程",R.drawable.icon_user_avater));
        options.add(new Option("我的成绩",R.drawable.icon_user_avater));
        options.add(new Option("发布动态",R.drawable.icon_user_avater));
        options.add(new Option("设置",R.drawable.icon_user_avater));
        options.add(new Option("我的信息",R.drawable.icon_user_avater));
        return options;
    }

}
