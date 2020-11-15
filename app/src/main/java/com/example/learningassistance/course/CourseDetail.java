package com.example.learningassistance.course;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learningassistance.R;
import com.example.learningassistance.fragment.CourseFragment;
import com.example.learningassistance.utils.Utils;

public class CourseDetail extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout exam,topic,more;
    public TextView examT,topicT,moreT;
    public CourseFragment examF,topicF,moreF;
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
        exam = findViewById(R.id.course_exam);
        examT = findViewById(R.id.course_exam_text);
        examT.setText("考试");
        examF = new CourseFragment(R.id.course_exam);
        ft.add(R.id.course_fg,examF);
        ft.hide(examF);

        topic = findViewById(R.id.course_topic);
        topicT = findViewById(R.id.course_topic_text);
        topicT.setText("话题");
        topicF = new CourseFragment(R.id.course_topic);
        ft.add(R.id.course_fg,topicF);
        ft.hide(topicF);

        more = findViewById(R.id.course_more);
        moreT = findViewById(R.id.course_more_text);
        moreT.setText("更多");
        moreF = new CourseFragment(R.id.course_more);
        ft.add(R.id.course_fg,moreF);
        ft.hide(moreF);
        ft.commit();

        exam.setOnClickListener(this);
        topic.setOnClickListener(this);
        more.setOnClickListener(this);

        exam.performClick();
    }

    public void resetTable(FragmentTransaction ft){
        examT.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        examT.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_defalut));

        topicT.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        topicT.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_defalut));

        moreT.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
        moreT.setBackground(ContextCompat.getDrawable(this,R.drawable.style_course_table_text_defalut));

        ft.hide(examF);
        ft.hide(topicF);
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
            case R.id.course_exam:
                setSelected(examT,ft,examF);
                break;
            case R.id.course_topic:
                setSelected(topicT,ft,topicF);
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
