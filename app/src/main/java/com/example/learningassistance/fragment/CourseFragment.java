package com.example.learningassistance.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CourseFragment extends Fragment implements View.OnClickListener {
    private int id;
    private Integer cid;
    public View view;

    public CourseFragment(int id,Integer cid) {
        this.id = id;
        this.cid = cid;
    }

    Handler examHandler = new Handler(msg -> {
        List<ExamList> examLists = new ArrayList<>();
        switch (msg.what){
            case 1:
                JSONArray jsonArray = JSON.parseArray(msg.getData().getString("data"));
                int arraySize = jsonArray.size();
                while (arraySize-- > 0){
                    JSONObject jsonObject = jsonArray.getJSONObject(arraySize);
                    String name = jsonObject.getString("papername");
                    String status = jsonObject.getString("status");
                    String startTime = timeStampToDate(jsonObject.getString("starttime"));
                    String endTime = timeStampToDate(jsonObject.getString("endtime"));
                    String time = startTime + "至" + endTime;

                    ExamList examList = new ExamList(name,time,status);
                    examLists.add(examList);
                }
                break;
            default:
                break;
        }
        if (examLists.size() > 0){
            Utils.setRecycler(view,R.id.recycler_exam,new ExamListAdapter(examLists));
        }
        return false;
    });

    Handler topicHandler = new Handler(msg -> {
        List<Topic> topics = new ArrayList<>();
        switch (msg.what){
            case 1:
                JSONArray jsonArray = JSON.parseArray(msg.getData().getString("data"));
                int arraySize = jsonArray.size();
                while (arraySize-- > 0){
                    JSONObject jsonObject = jsonArray.getJSONObject(arraySize);
                    String name = jsonObject.getString("topictitle");
                    String commitTime = timeStampToDate(jsonObject.getString("committime"));
                    String content = jsonObject.getString("topiccontent");
                    String topicId = jsonObject.getString("topicid");

                    Topic topic = new Topic(R.drawable.icon_user_avater,topicId,commitTime,name,content);
                    topics.add(topic);
                }
                break;
            default:
                break;
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
        map.put("courseid", cid.toString());
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
        startActivity(intent);
    }

    public String timeStampToDate(String stamp){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(Long.parseLong(stamp)));
    }

}
