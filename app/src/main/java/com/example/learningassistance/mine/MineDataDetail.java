package com.example.learningassistance.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.learningassistance.R;

public class MineDataDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_data_detail);

        Intent intent = getIntent();
        String topName = intent.getStringExtra("topName");
        TextView top = findViewById(R.id.mine_detail_top);
        top.setText(topName);
    }

    public void goBack(View view) {
        finish();
    }
}