package com.example.learningassistance.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.Opinion;
import com.example.learningassistance.utils.RoundRectImageView;

import java.util.List;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.ViewHolder> {
    private List<Opinion> opinions;
    private Activity activity;

    public OpinionAdapter(List<Opinion> opinions, Activity activity) {
        this.opinions = opinions;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_opinion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Opinion opinion = opinions.get(position);

        holder.content.setText(opinion.getContent());
        RoundRectImageView.setRadius(holder.image,opinion.getImgId(),40,10,activity);
        holder.name.setText(opinion.getName());
        holder.time.setText(opinion.getTime());
    }

    @Override
    public int getItemCount() {
        return opinions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView time;
        public TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.course_opinion_avatar);
            this.name = itemView.findViewById(R.id.course_opinion_creator);
            this.time = itemView.findViewById(R.id.course_opinion_time);
            this.content = itemView.findViewById(R.id.course_opinion_content);
        }
    }
}
