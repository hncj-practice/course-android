package com.example.learningassistance.course;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.CourseListAdapter;
import com.example.learningassistance.entity.CourseList;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        List<CourseList> courseList = initCourse();
        RecyclerView recyclerView = findViewById(R.id.recycler_course);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        CourseListAdapter adapter = new CourseListAdapter(courseList,this);
        recyclerView.setAdapter(adapter);
    }

    public List<CourseList> initCourse(){
        List<CourseList> list = new ArrayList<>();
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        list.add(new CourseList(R.drawable.a,"云计算技术与应用","柳运昌"));
        return list;
    }

    public void goBack(View view) {
        finish();
    }
}
