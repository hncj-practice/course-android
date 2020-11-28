package com.example.learningassistance.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.course.CourseActivity;
import com.example.learningassistance.entity.CourseList;
import com.example.learningassistance.utils.RangleTransForm;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private List<CourseList> mCourseList;

    public CourseListAdapter(List<CourseList> mCourseList) {
        this.mCourseList = mCourseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        CourseList course = mCourseList.get(position);
        holder.lecturer.setText(course.getLecturer());

        Picasso.get().load(course.getImgUrl()).transform(new RangleTransForm(10,50)).into(holder.img);
        holder.name.setText(course.getName());
        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent("com.action.COURSE_DETAIL_ACTIVITY_START");
            intent.putExtra("courseName",holder.name.getText().toString());
            intent.putExtra("cid",course.getCid());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView img;
        public TextView name;
        public TextView lecturer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            img = itemView.findViewById(R.id.course_avatar);
            name = itemView.findViewById(R.id.course_name);
            lecturer = itemView.findViewById(R.id.course_lecturer);
        }
    }
}
