package com.example.learningassistance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.ExamListAdapter;
import com.example.learningassistance.adapter.TopicAdapter;
import com.example.learningassistance.entity.ExamList;
import com.example.learningassistance.entity.Topic;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseFragment extends Fragment implements View.OnClickListener {
    private int id;
    private String cid;
    public View view;

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
                examLists.add(examList);
                arraySize += 1;
            }
        }
        if (examLists.size() > 0){
            Utils.setRecycler(view,R.id.recycler_exam,new ExamListAdapter(examLists));
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
                String name = jsonObject.getString("topictitle");
                String commitTime = Utils.timeStampToDate(jsonObject.getString("committime"));
                String content = jsonObject.getString("topiccontent");

//                    暂时把这个当做名字了,其实他是话题号
                String topicId = jsonObject.getString("topicid");

                Topic topic = new Topic(R.drawable.icon_default_avatar, topicId, commitTime, name, content);
                topic.setTopicId(topicId);
                topics.add(topic);
            }
        }
        if (topics.size() > 0){
            Utils.setRecycler(view,R.id.recycler_topic_list,new TopicAdapter(topics,CourseFragment.this));
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
                Utils.getNetData("paper/getpaperbycourseid",map,examHandler);
                break;
            case R.id.course_topic:
                view = inflater.inflate(R.layout.fg_course_topic,container,false);
                Utils.getNetData("topic/gettopicbycid",map,topicHandler);
                break;
            case R.id.course_more:
                view = inflater.inflate(R.layout.fg_course_more,container,false);
                loadMoreFragment(view);
                break;
            default:
                break;
        }
        return view;
    }

    //    加载课程中的更多碎片
    public void loadMoreFragment(View view){
        LinearLayout learnData = view.findViewById(R.id.course_more_learn_data);
        LinearLayout member = view.findViewById(R.id.course_more_member);
        LinearLayout achievement = view.findViewById(R.id.course_more_achievement);

        learnData.setOnClickListener(this);
        member.setOnClickListener(this);
        achievement.setOnClickListener(this);
    }

    /**
     * 为more中的各项设置监听
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.course_more_learn_data:
                intent.setAction("com.action.COURSE_DETAIL_LEARN_DATA");
                break;
            case R.id.course_more_member:
                intent.setAction("com.action.COURSE_DETAIL_MEMBER");
                break;
            case R.id.course_more_achievement:
                intent.setAction("com.action.COURSE_DETAIL_ACHIEVEMENT");
                break;
            default:
                break;
        }
        intent.putExtra("courseId",cid);
        startActivity(intent);
    }

}
