package com.example.learningassistance.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.MineDetailAdapter;
import com.example.learningassistance.entity.MineDetail;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MineDataDetail extends AppCompatActivity {
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_data_detail);

        Utils.setTitle(this,"我的信息");

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        jsonObject = JSON.parseObject(data);

        List<MineDetail> list = initDetail();
        RecyclerView recyclerView = findViewById(R.id.recycler_mine_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MineDetailAdapter adapter = new MineDetailAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    private List<MineDetail> initDetail(){
        List<MineDetail> list = new ArrayList<>();
        list.add(new MineDetail(R.drawable.icon_user_avater,"姓名","这是描述的具体值"));
        if (jsonObject.getString("status").equals("1")){
            list.add(new MineDetail(R.drawable.icon_user_avater,"学号",jsonObject.getString("sno")));
            list.add(new MineDetail(R.drawable.icon_user_avater,"班级",jsonObject.getString("cla")));
        } else if (jsonObject.getString("status").equals("2")){
            list.add(new MineDetail(R.drawable.icon_user_avater,"工号",jsonObject.getString("tno")));
        }
        list.add(new MineDetail(R.drawable.icon_user_avater,"性别",jsonObject.getString("sex").equals("m") ? "男" : "女"));
        list.add(new MineDetail(R.drawable.icon_user_avater,"电子邮箱",jsonObject.getString("email")));
        return list;
    }
}