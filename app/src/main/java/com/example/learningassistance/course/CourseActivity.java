package com.example.learningassistance.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Objects;

public class CourseActivity extends AppCompatActivity {
    private List<CourseList> courseLists = new ArrayList<>();

    Handler handlerS = new Handler(msg -> {
        Map<Integer, String> result = (HashMap) msg.getData().getSerializable("data");
        for(int j = 1; j < 9; j++ ){
            assert result != null;
            if (Objects.equals(result.get(j), "{ \"code\" : 401, \"message\" : \"查询结果为空\" }")){
                continue;
            }
            JSONObject json = JSON.parseObject(result.get(j));
            String data = json.getString("data");
            JSONArray array = JSON.parseArray(data);
            for (int i = 0; i < array.size(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                CourseList courseList = new CourseList(jsonObject.getString("coverimg"),jsonObject.getString("cname"),jsonObject.getString("tname"));
                courseList.setCid( jsonObject.getString("cid") );
                courseLists.add(courseList);
            }
        }
        if (courseLists.size() != 0) {
            Utils.setRecycler(CourseActivity.this, R.id.recycler_course, new CourseListAdapter(courseLists));
        } else {
            Toast.makeText(this, "未查询到相关信息", Toast.LENGTH_SHORT).show();
        }
        return true;
    });

    Handler handlerT = new Handler(msg -> {
        if (msg.what == 1){
            JSONArray array = JSON.parseArray(msg.getData().getString("data"));
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                CourseList courseList = new CourseList(jsonObject.getString("coverimg"),jsonObject.getString("cname"),jsonObject.getString("tname"));
                courseList.setCid( jsonObject.getString("cid") );
                courseLists.add(courseList);
            }
            Utils.setRecycler(this,R.id.recycler_course,new CourseListAdapter(courseLists));
        } else {
            Toast.makeText(this, "未查询到相关信息", Toast.LENGTH_SHORT).show();
        }
        return false;
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent = getIntent();
        JSONObject userData = JSON.parseObject(intent.getStringExtra("data"));

        SharedPreferences preferences = getSharedPreferences("loginHistory",MODE_PRIVATE);
        Map<String, String> map = new HashMap<>();
        if (preferences.getString("currentUserType","0").equals("1")){
            Utils.setTitle(this,"全部课程");
            map.put("studentid", userData.getString("sno"));
            Utils.getCourseData(map,handlerS);
        } else {
            Utils.setTitle(this,"我教的课");
            map.put("condition", userData.getString("tno"));
            map.put("page","1");
            map.put("num","6");
            map.put("type","1");
            Utils.getNetData("course/getcoursebytnoorcoursename",map,handlerT);
        }
    }

    /**
     * 通过学号获得该学生至今所学的所有课程信息
     */
    public void getCourseData(Map<String, String> data){
        new Thread(()  -> {
            Map<Integer, String> result = new HashMap<>();
            try {
                int i = 1;
                while (i < 9){
                    data.put("semesterid", Integer.toString(i));
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
            handlerS.sendMessage(message);
        }).start();
    }

}
