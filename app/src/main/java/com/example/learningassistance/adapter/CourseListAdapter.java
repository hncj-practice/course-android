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
import com.example.learningassistance.entity.CourseList;
import com.example.learningassistance.utils.RoundRectImageView;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private List<CourseList> mCourseList;
    private Activity activity;

    public CourseListAdapter(List<CourseList> mCourseList,Activity activity) {
        this.mCourseList = mCourseList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_course_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseList course = mCourseList.get(position);
        holder.lecturer.setText(course.getLecturer());
        holder.img.setImageResource(course.getImgId());
        RoundRectImageView.setRadius(holder.img,course.getImgId(),50,10 ,activity);
        holder.name.setText(course.getName());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView lecturer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.course_avatar);
            name = itemView.findViewById(R.id.course_name);
            lecturer = itemView.findViewById(R.id.course_lecturer);
        }
    }
}
