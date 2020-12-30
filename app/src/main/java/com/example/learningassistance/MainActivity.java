package com.example.learningassistance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learningassistance.fragment.MainPageFragment;

@SuppressLint("ResourceAsColor")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String DATA ;
    private int SIZE;

    private LinearLayout layout_homepage,layout_message,layout_dynamic,layout_my;
    private ImageView image_homepage,image_message,image_dynamic,image_my;
    private TextView text_homepage,text_message,text_dynamic,text_my;

    private TextView title_text;
    private ImageView title_back;

    private MainPageFragment f1,f2,f3,f4;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        SIZE = point.x;

        Intent intent = getIntent();
        DATA = intent.getStringExtra("data");
        fm = getSupportFragmentManager();
        initComponent();
    }

    /**
     * 加载界面中的组件
     */
    private void initComponent() {
        layout_homepage = findViewById(R.id.menu_homepage);
        layout_dynamic = findViewById(R.id.menu_dynamic);
        layout_message = findViewById(R.id.menu_message);
        layout_my = findViewById(R.id.menu_my);

        image_homepage = findViewById(R.id.homepage_image);
        text_homepage = findViewById(R.id.homepage_text);
        image_homepage.setImageResource(R.drawable.icon_homepage_pressed);
        text_homepage.setTextColor(R.color.selected);

        image_message = findViewById(R.id.message_image);
        text_message = findViewById(R.id.message_text);

        image_dynamic = findViewById(R.id.dynamic_image);
        text_dynamic = findViewById(R.id.dynamic_text);

        image_my = findViewById(R.id.my_image);
        text_my = findViewById(R.id.my_text);

        title_text = findViewById(R.id.title_text);
        title_text.setText("首页");

        title_back = findViewById(R.id.title_back);
        title_back.setVisibility(View.INVISIBLE);

        layout_my.setOnClickListener(this);
        layout_message.setOnClickListener(this);
        layout_dynamic.setOnClickListener(this);
        layout_homepage.setOnClickListener(this);
        layout_homepage.performClick();
    }

    /**
     * 刷新底部栏的图片状态,都设置为未选中时的状态
     */
    private void refreshView() {
        image_homepage.setImageResource(R.drawable.icon_homepage_default);
        text_homepage.setTextColor(R.color.black);

        image_message.setImageResource(R.drawable.icon_message_default);
        text_message.setTextColor(R.color.black);

        image_dynamic.setImageResource(R.drawable.icon_dynamic_default);
        text_dynamic.setTextColor(R.color.black);

        image_my.setImageResource(R.drawable.icon_my_default);
        text_my.setTextColor(R.color.black);
    }

    /**
     * 刷新所有碎片,将所有碎片隐藏,方便后续选择
     * @param fragmentTransaction 碎片事务
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if (f1 != null){
            fragmentTransaction.hide(f1);
        }
        if (f2 != null){
            fragmentTransaction.hide(f2);
        }
        if (f3 != null){
            fragmentTransaction.hide(f3);
        }
        if (f4 != null){
            fragmentTransaction.hide(f4);
        }
    }

    /**
     * 刷新底部栏的选择状态,都设置为false
     */
    public void setSelected(){
        layout_homepage.setSelected(false);
        layout_message.setSelected(false);
        layout_dynamic.setSelected(false);
        layout_my.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fm.beginTransaction();
//        先初始化所有的状态
        hideAllFragment(transaction);
        refreshView();
        setSelected();
//        对选择的选项进行加载,将需要显示的碎片显示出来
        switch (v.getId()){
            case R.id.menu_homepage:
                image_homepage.setImageResource(R.drawable.icon_homepage_pressed);
                text_homepage.setTextColor(R.color.selected);
                layout_homepage.setSelected(true);
                if (f1 == null){
                    f1 = new MainPageFragment(DATA,R.id.menu_homepage,SIZE);
                    transaction.add(R.id.fragment,f1);
                } else {
                    transaction.show(f1);
                }
                break;
            case R.id.menu_message:
                image_message.setImageResource(R.drawable.icon_message_pressed);
                text_message.setTextColor(R.color.selected);
                layout_message.setSelected(true);
                if (f2 == null){
                    f2 = new MainPageFragment(DATA,R.id.menu_message);
                    transaction.add(R.id.fragment,f2);
                } else {
                    transaction.show(f2);
                }
                break;
            case R.id.menu_dynamic:
                image_dynamic.setImageResource(R.drawable.icon_dynamic_pressed);
                text_dynamic.setTextColor(R.color.selected);
                layout_dynamic.setSelected(true);
                if (f3 == null){
                    f3 = new MainPageFragment(DATA,R.id.menu_dynamic,SIZE);
                    transaction.add(R.id.fragment,f3);
                } else {
                    transaction.show(f3);
                }
                break;
            case R.id.menu_my:
                image_my.setImageResource(R.drawable.icon_my_pressed);
                text_my.setTextColor(R.color.selected);
                layout_my.setSelected(true);
                if (f4 == null){
                    f4 = new MainPageFragment(DATA,R.id.menu_my);
                    transaction.add(R.id.fragment,f4);
                } else {
                    transaction.show(f4);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
}