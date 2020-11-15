package com.example.learningassistance.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.learningassistance.R;
import com.example.learningassistance.utils.Utils;

public class ExamDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("data"));


    }
}