package com.example.learningassistance.course;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningassistance.R;
import com.example.learningassistance.utils.Utils;

public class AchievementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        Utils.setTitle(this,"历史成绩");
    }
}
