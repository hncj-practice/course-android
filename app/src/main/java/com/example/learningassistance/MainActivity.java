package com.example.learningassistance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_homepage,image_message,image_dynamic,image_my;
    private TextView text_homepage,text_message,text_dynamic,text_my;

    private MyFragment f1,f2,f3,f4;
    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        initComponent();

    }

    private void initComponent() {
        LinearLayout layout_homepage = findViewById(R.id.menu_homepage);
        LinearLayout layout_dynamic = findViewById(R.id.menu_dynamic);
        LinearLayout layout_message = findViewById(R.id.menu_message);
        LinearLayout layout_my = findViewById(R.id.menu_my);

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

        layout_my.setOnClickListener(this);
        layout_message.setOnClickListener(this);
        layout_dynamic.setOnClickListener(this);
        layout_homepage.setOnClickListener(this);
    }

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

    public void clickHomepage(View view) {
        refreshView();
        image_homepage.setImageResource(R.drawable.icon_homepage_pressed);
        text_homepage.setTextColor(R.color.selected);
    }

    public void clickMessage(View view) {
        refreshView();
        image_message.setImageResource(R.drawable.icon_message_pressed);
        text_message.setTextColor(R.color.selected);
    }

    public void clickDynamic(View view) {
        refreshView();
        image_dynamic.setImageResource(R.drawable.icon_dynamic_pressed);
        text_dynamic.setTextColor(R.color.selected);
    }

    public void clickMy(View view) {
        refreshView();
        image_my.setImageResource(R.drawable.icon_my_pressed);
        text_my.setTextColor(R.color.selected);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction);
    }
}