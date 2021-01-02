package com.example.learningassistance.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.OpinionAdapter;
import com.example.learningassistance.entity.Opinion;
import com.example.learningassistance.entity.Topic;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicDetail extends AppCompatActivity {
    private ImageView topicCreator;
    private TextView topicName,topicTime,TopicTitle,TopicQuestion;
    private LinearLayout replyLayout;
    private EditText sendText;
    private TextView sendButton;

    public Integer page = 1;
    private Map<String, String> map = new HashMap<>();

    private Topic topic;

    Handler handler = new Handler( msg -> {
        List<Opinion> opinions = new ArrayList<>();
        if (msg.what == 1) {
            String data = msg.getData().getString("data");
            JSONArray jsonArray = JSON.parseArray(data);
            int arraySize = 0;
            assert jsonArray != null;
            while (arraySize < jsonArray.size()) {
                JSONObject jsonObject = jsonArray.getJSONObject(arraySize);
                String imgUrl = jsonObject.getString("savatar");
                String name = jsonObject.getString("sname");
                String time = Utils.timeStampToDate(jsonObject.getString("commenttime"));
                String content = jsonObject.getString("commentcontent");

                Opinion opinion = new Opinion(imgUrl, name, time, content);
                opinions.add(opinion);
                arraySize += 1;
            }
        }
        if (opinions.size() > 0){
            Utils.setRecycler(TopicDetail.this,R.id.recycler_opinion,new OpinionAdapter(opinions));
        }
        return false;
    });

    Handler sendHandler = new Handler(msg -> {
        if (msg.what == 1) {
            Utils.getNetData("comment/getcommentbytopicid", map, handler);
        }
        return false;
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        Intent intent = getIntent();
        topic = (Topic) intent.getSerializableExtra("topic");

        Utils.setTitle(this,"话题详情");

        initComponent();


        map.put("topicid",topic.getTopicId());
        map.put("num","5");
        map.put("page",page.toString());
        Utils.getNetData("comment/getcommentbytopicid",map,handler);

    }

    private void initComponent(){
        replyLayout = findViewById(R.id.topic_reply_layout);
        topicName = findViewById(R.id.topic_detail_creator);
        topicName.setText(topic.getCreator());
        topicTime = findViewById(R.id.topic_detail_time);
        topicTime.setText(topic.getTime());
        TopicTitle = findViewById(R.id.topic_detail_title);
        TopicTitle.setText(topic.getTitle());
        TopicTitle.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        TopicQuestion = findViewById(R.id.topic_detail_question);
        TopicQuestion.setText(topic.getQuestion());

        topicCreator = findViewById(R.id.topic_detail_creator_avatar);
        Picasso.get().load(topic.getImgId()).transform(new MyTransForm.RangleTransForm()).into(topicCreator);

        sendText = findViewById(R.id.opinion_send_text);
        sendButton = findViewById(R.id.opinion_send_button);
//        发送自己的评论
        sendButton.setOnClickListener(v -> {
            String content = sendText.getText().toString();
            if (content.length() < 1){
                return;
            }
            SharedPreferences preferences = getSharedPreferences("loginHistory",MODE_PRIVATE);
            String userId = preferences.getString("currentUserId", "");
            long time = new Date().getTime();
            Map<String, String> map = new HashMap<>();
            map.put("sno",userId);
            map.put("topicid",topic.getTopicId());
            map.put("commentcontent",content);
            map.put("commenttime", Long.toString(time));
            Utils.getNetData("comment/addcomment",map,sendHandler);
            sendText.setText("");
        });
    }

    /**
     * 隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        View v = getCurrentFocus();
        if (!Utils.isTouchPointInView(replyLayout, x, y)) {
            if (v != null){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

}
