package com.example.learningassistance.course;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learningassistance.R;
import com.example.learningassistance.fragment.CourseFragment;
import com.example.learningassistance.utils.Utils;

public class CourseDetail extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout mission,chapter,more;
    public TextView missionT,chapterT,moreT;
    public CourseFragment missionF,chapterF,moreF;
    public FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("courseName"));
        fm = getSupportFragmentManager();

        initComponent();

    }

    public void initComponent(){
        FragmentTransaction ft = fm.beginTransaction();
        mission = findViewById(R.id.course_mission);
        missionT = findViewById(R.id.course_mission_text);
        missionT.setText("任务");
        missionF = new CourseFragment(R.id.course_mission);
        ft.add(R.id.course_fg,missionF);
        ft.hide(missionF);

        chapter = findViewById(R.id.course_chapter);
        chapterT = findViewById(R.id.course_chapter_text);
        chapterT.setText("章节");
        chapterF = new CourseFragment(R.id.course_chapter);
        ft.add(R.id.course_fg,chapterF);
        ft.hide(chapterF);

        more = findViewById(R.id.course_more);
        moreT = findViewById(R.id.course_more_text);
        moreT.setText("更多");
        moreF = new CourseFragment(R.id.course_more);
        ft.add(R.id.course_fg,moreF);
        ft.hide(moreF);
        ft.commit();

        mission.setOnClickListener(this);
        chapter.setOnClickListener(this);
        more.setOnClickListener(this);

        mission.performClick();
    }

    public void resetTable(FragmentTransaction ft){
        missionT.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        missionT.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_defalut));

        chapterT.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        chapterT.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_defalut));

        moreT.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        moreT.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_defalut));

        ft.hide(missionF);
        ft.hide(chapterF);
        ft.hide(moreF);
    }

    public void setSelected(TextView view,FragmentTransaction ft, Fragment fragment){
        view.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        view.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_selected));
        ft.show(fragment);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = fm.beginTransaction();
        resetTable(ft);
        switch (v.getId()){
            case R.id.course_mission:
                setSelected(missionT,ft,missionF);
                break;
            case R.id.course_chapter:
                setSelected(chapterT,ft,chapterF);
                break;
            case R.id.course_more:
                setSelected(moreT,ft,moreF);
                break;
            default:
                break;
        }
        ft.commit();
    }
}
