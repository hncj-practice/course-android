package com.example.learningassistance.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.learningassistance.R;
import com.example.learningassistance.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAchievement extends AppCompatActivity {
    @BindView(R.id.show_achievement)
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_achievement);
        ButterKnife.bind(this);

        Utils.setTitle(this,"最终成绩");
        Intent intent = getIntent();
        String achievement = intent.getStringExtra("achievement");
        show.setText(achievement);
    }
}