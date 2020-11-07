package com.example.learningassistance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.learningassistance.R;
import com.example.learningassistance.adapter.ExamListAdapter;
import com.example.learningassistance.entity.ExamList;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment implements View.OnClickListener {
    private int id;

    public CourseFragment(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (id){
            case R.id.course_exam:
                view = inflater.inflate(R.layout.fg_course_exam,container,false);
                Utils.setRecycler(view,R.id.recycler_exam,new ExamListAdapter(initExams()));
                break;
            case R.id.course_chapter:
                view = inflater.inflate(R.layout.fg_course_chapter,container,false);
                break;
            case R.id.course_more:
                view = inflater.inflate(R.layout.fg_course_more,container,false);
                loadMoreFragment(view);
                break;
            default:
                break;
        }
        return view;
    }

    public List<ExamList> initExams(){
        List<ExamList> lists = new ArrayList<>();
        lists.add(new ExamList("这是一个考试","这是考试时间","未开始"));
        lists.add(new ExamList("这是一个考试","这是考试时间","未开始"));
        lists.add(new ExamList("这是一个考试","这是考试时间","未开始"));
        lists.add(new ExamList("这是一个考试","这是考试时间","未开始"));
        lists.add(new ExamList("这是一个考试","这是考试时间","未开始"));
        lists.add(new ExamList("这是一个考试","这是考试时间","未开始"));
        return lists;
    }

//    加载课程中的更多碎片
    public void loadMoreFragment(View view){
        LinearLayout learnData = view.findViewById(R.id.course_more_learn_data);
        LinearLayout member = view.findViewById(R.id.course_more_member);
        LinearLayout achievement = view.findViewById(R.id.course_more_achievement);

        learnData.setOnClickListener(this);
        member.setOnClickListener(this);
        achievement.setOnClickListener(this);
    }

    /**
     * 为more中的各项设置监听
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.course_more_learn_data:
                intent.setAction("com.action.COURSE_DETAIL_LEARN_DATA");
                break;
            case R.id.course_more_member:
                intent.setAction("com.action.COURSE_DETAIL_MEMBER");
                break;
            case R.id.course_more_achievement:
                intent.setAction("com.action.COURSE_DETAIL_ACHIEVEMENT");
                break;
            default:
                break;
        }
        Toast.makeText(v.getContext(),"click",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
