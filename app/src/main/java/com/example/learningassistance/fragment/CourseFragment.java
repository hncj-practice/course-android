package com.example.learningassistance.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.DataListAdapter;
import com.example.learningassistance.adapter.ExamListAdapter;
import com.example.learningassistance.adapter.TopicAdapter;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.entity.ExamList;
import com.example.learningassistance.entity.Topic;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseFragment extends Fragment {
    private int id;
    private String cid;
    public View view;

    private TextView noData;
    private TextView noExam;
    private TextView noTopic;

    public CourseFragment(int id,String cid) {
        this.id = id;
        this.cid = cid;
    }

    Handler examHandler = new Handler(msg -> {
        List<ExamList> examLists = new ArrayList<>();
        if (msg.what == 1) {
            JSONArray jsonArray = JSON.parseArray(msg.getData().getString("data"));
            int arraySize = 0;
            assert jsonArray != null;
            while (arraySize < jsonArray.size()) {
                JSONObject jsonObject = jsonArray.getJSONObject(arraySize);
                String name = jsonObject.getString("papername");
                String status = jsonObject.getString("status");
                String startTime = Utils.timeStampToDate(jsonObject.getString("starttime"));
                String endTime = Utils.timeStampToDate(jsonObject.getString("endtime"));
                String time = startTime + "至" + endTime;

                ExamList examList = new ExamList(name, time, status);
                examList.setExamId(jsonObject.getString("paperid"));
                examLists.add(examList);
                arraySize += 1;
            }
        }
        if (examLists.size() > 0){
            Utils.setRecycler(view,R.id.recycler_exam,new ExamListAdapter(examLists,this));
        } else {
            noExam.setVisibility(View.VISIBLE);
        }
        return false;
    });

    Handler topicHandler = new Handler(msg -> {
        List<Topic> topics = new ArrayList<>();
        if (msg.what == 1) {
            JSONArray jsonArray = JSON.parseArray(msg.getData().getString("data"));
            assert jsonArray != null;
            int arraySize = jsonArray.size();
            while (arraySize-- > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(arraySize);
                String title = jsonObject.getString("topictitle");
                String commitTime = Utils.timeStampToDate(jsonObject.getString("committime"));
                String content = jsonObject.getString("topiccontent");
                String name = jsonObject.getString("name");
                String imgUrl =  jsonObject.getString("avatar");
                String topicId = jsonObject.getString("topicid");

                Topic topic = new Topic(imgUrl, name, commitTime, title, content);
                topic.setTopicId(topicId);
                topics.add(topic);
            }
        }
        if (topics.size() > 0){
            Utils.setRecycler(view,R.id.recycler_topic_list,new TopicAdapter(topics,CourseFragment.this));
        } else {
            noTopic.setVisibility(View.VISIBLE);
        }
        return false;
    });

    Handler dataHandler = new Handler(msg -> {
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
            Utils.setRecycler(view,R.id.recycler_data,new DataListAdapter(dataList));
        } else {
            noData.setVisibility(View.VISIBLE);
        }
        return false;
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Map<String,String> map = new HashMap<>();
        map.put("courseid", cid);
        switch (id){
            case R.id.course_exam:
                view = inflater.inflate(R.layout.fg_course_exam,container,false);
                noExam = view.findViewById(R.id.exam_null);
                Utils.getNetData("paper/getpaperbycourseid",map,examHandler);
                break;
            case R.id.course_topic:
                view = inflater.inflate(R.layout.fg_course_topic,container,false);
                noTopic = view.findViewById(R.id.topic_null);
                Utils.getNetData("topic/gettopicbycid",map,topicHandler);
                break;
            case R.id.course_more:
                view = inflater.inflate(R.layout.fg_course_more,container,false);
                noData = view.findViewById(R.id.data_null);
                map.put("page","1");
                map.put("num","6");
                Utils.getNetData("data/getdatabycourseid",map,dataHandler);
                break;
            default:
                break;
        }
        return view;
    }

}
