package com.example.learningassistance.course;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.MemberAdapter;
import com.example.learningassistance.entity.Member;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberActivity extends AppCompatActivity {
    @BindView(R.id.member_student)
    TextView student;
    @BindView(R.id.member_teacher)
    TextView teacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);

        Utils.setTitle(this,"班级成员");

        List<Member> teachers = initTeacher();
        List<Member> students = initStudent();
        teacher.setText("教师(" + teachers.size() + "人)");
        Utils.setRecycler(this,R.id.recycler_member_teacher,new MemberAdapter(teachers));
        student.setText("学生(" + students.size() + "人)");
        Utils.setRecycler(this,R.id.recycler_member_student,new MemberAdapter(students));
    }

    private List<Member> initTeacher(){
        List<Member> members = new ArrayList<>();
        members.add(new Member("这是老师",R.drawable.icon_user_avater));
        return members;
    }

    private List<Member> initStudent(){
        List<Member> members = new ArrayList<>();
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        members.add(new Member("这是学生",R.drawable.icon_user_avater));
        return members;
    }

}
