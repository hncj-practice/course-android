package com.example.learningassistance.course;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningassistance.R;
import com.example.learningassistance.utils.Utils;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        Utils.setTitle(this,"班级成员");
    }
}
