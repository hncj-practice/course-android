package com.example.learningassistance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.learningassistance.R;

public class CourseFragment extends Fragment {
    private int id;

    public CourseFragment(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (id){
            case R.id.course_mission:
                view = inflater.inflate(R.layout.fg_course_mission,container,false);
                break;
            case R.id.course_chapter:
                view = inflater.inflate(R.layout.fg_course_chapter,container,false);
                break;
            case R.id.course_more:
                view = inflater.inflate(R.layout.fg_course_more,container,false);
                break;
            default:
                break;
        }
        return view;
    }
}
