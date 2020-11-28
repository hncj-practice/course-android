package com.example.learningassistance.course;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.CourseListAdapter;
import com.example.learningassistance.entity.CourseList;
import com.example.learningassistance.utils.Utils;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity {
    private JSONObject userData;
    private List<CourseList> courseLists = new ArrayList<>();

    Handler handler = new Handler(msg -> {
        Map<Integer, String> result = (Map<Integer, String>) msg.getData().getSerializable("data");
        for(int j = 1; j < 9; j++ ){
            if (result.get(j).equals("{ \"code\" : 401, \"message\" : \"查询结果为空\" }")){
                continue;
            }
            JSONObject json = JSON.parseObject(result.get(j));
            String data = json.getString("data");
            JSONArray array = JSON.parseArray(data);
            for (int i = 0; i < array.size(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                CourseList courseList = new CourseList(jsonObject.getString("coverimg"),jsonObject.getString("cname"),jsonObject.getString("tname"));
                courseList.setCid( jsonObject.getInteger("cid") );
                courseLists.add(courseList);
            }
        }
        if (courseLists.size() != 0) {
            Utils.setRecycler(CourseActivity.this, R.id.recycler_course, new CourseListAdapter(courseLists));
        }
        return true;
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Utils.setTitle(this,"全部课程");
        Intent intent = getIntent();
        userData = JSON.parseObject(intent.getStringExtra("data"));

        Map<String, String> map = new HashMap<>();
        map.put("studentid",userData.getString("sno"));
        getCourseData(map);
    }

    /**
     * 通过学号获得该学生至今所学的所有课程信息
     */
    public void getCourseData(Map<String, String> data){
        new Thread(()  -> {
            Map<Integer, String> result = new HashMap<>();
            try {
                Integer i = 1;
                while (i < 9){
                    data.put("semesterid",i.toString());
                    String response = Jsoup.connect("http://123.56.156.212/Interface/course/getcoursebysno")
                            .data(data)
                            .ignoreContentType(true)
                            .timeout(10000)
                            .post()
                            .body()
                            .text();
                    result.put(i, response);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) result);
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }

}
