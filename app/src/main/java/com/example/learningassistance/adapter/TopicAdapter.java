package com.example.learningassistance.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.Topic;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.RoundRectImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<Topic> topics;
    private Fragment fragment;

    public TopicAdapter(List<Topic> topics, Fragment fragment) {
        this.topics = topics;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viwe = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_topic_list_item, parent, false);
        return new ViewHolder(viwe);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdapter.ViewHolder holder, int position) {
        final Topic topic = topics.get(position);
        Picasso.get().load(topic.getImgId()).transform(new MyTransForm.RangleTransForm()).into(holder.avatar);
        holder.question.setText(topic.getQuestion());
        holder.name.setText(topic.getCreator());
        holder.time.setText(topic.getTime());
        holder.title.setText(topic.getTitle());
        holder.title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);

        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("topic",topic);
            intent.putExtras(bundle);
            intent.setAction("com.action.COURSE_TOPIC_DETAIL");
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,time,title,question;
        private LinearLayout layout;
        private ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.course_topic_creator);
            time = itemView.findViewById(R.id.course_topic_time);
            title = itemView.findViewById(R.id.course_topic_title);
            question = itemView.findViewById(R.id.course_topic_question);
            avatar = itemView.findViewById(R.id.course_topic_creator_avatar);

            layout = itemView.findViewById(R.id.course_topic_layout);
        }
    }
}
