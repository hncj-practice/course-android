package com.example.learningassistance.course;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.DataListAdapter;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearnDataActivity extends AppCompatActivity {

    Handler handler = new Handler(msg -> {
        ArrayList<Data> dataList = new ArrayList<>();
        if (msg.what == 1){
            JSONArray array = JSON.parseArray(msg.getData().getString("data"));
            int arraySize = 0;
            assert array != null;
            while (arraySize < array.size()){
                JSONObject jsonObject = array.getJSONObject(arraySize);
                String type = jsonObject.getString("datatype");
                String link = jsonObject.getString("datalink");
                String name = jsonObject.getString("dataname");

                Data data = new Data(name,link,type);
                dataList.add(data);

                arraySize += 1;
            }
        }
        if (dataList.size() > 0){
            Utils.setRecycler(LearnDataActivity.this,R.id.recycler_data,new DataListAdapter(dataList));
        }
        return false;
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learndata);
        Utils.setTitle(this,"资料");
        Intent intent = getIntent();
        String cid = intent.getStringExtra("courseId");

        Map<String, String> map = new HashMap<>();
        map.put("courseid",cid);
        map.put("page","1");
        map.put("num","6");
        Utils.getNetData("data/getdatabycourseid",map,handler);
    }
}
