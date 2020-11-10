package com.example.learningassistance.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.OpinionAdapter;
import com.example.learningassistance.entity.Opinion;
import com.example.learningassistance.entity.Topic;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TopicDetail extends AppCompatActivity {
    private ImageView topicCreator;
    private TextView topicName,topicTime,TopicTitle,TopicQuestion;

    private EditText sendText;
    private TextView sendButton;

    private Topic topic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        Intent intent = getIntent();
        topic = (Topic) intent.getSerializableExtra("topic");

        Utils.setTitle(this,"话题详情");

        initComponent();

        Utils.setRecycler(this, R.id.recycler_opinion, new OpinionAdapter(initOpinions(),this));

    }

    public class EditChangedListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0){
                sendButton.setBackground(getDrawable(R.drawable.style_topic_send_button_selected));
            } else {
                sendButton.setBackground(getDrawable(R.drawable.style_topic_send_button_default));
            }
        }
    }

    public List<Opinion> initOpinions(){
        List<Opinion> list = new ArrayList<>();
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        list.add(new Opinion(R.drawable.icon_user_avater,"江道宽","11-09","会尽快发改家回复的卡号地方啊看见哈付款很卡as控件很卡就分手"));
        return list;
    }

    private void initComponent(){
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
        RoundRectImageView.setRadius(topicCreator,topic.getImgId(),40,10,this);

        sendButton = findViewById(R.id.opinion_send_button);
        sendText = findViewById(R.id.opinion_send_text);
        sendText.addTextChangedListener(new EditChangedListener());
    }

}
