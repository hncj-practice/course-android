package com.example.learningassistance.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.ExamList;
import com.example.learningassistance.utils.Utils;

import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    private List<ExamList> examLists;

    public ExamListAdapter(List<ExamList> examLists) {
        this.examLists = examLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_exam_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamList exam = examLists.get(position);
        holder.name.setText(exam.getName());
        holder.time.setText(exam.getTime());
        holder.status.setText(exam.getStatus());

//        获取考试id,转入考试activity
        holder.view.setOnClickListener(v -> {
            Intent intent = Utils.setIntent(exam.getName(),"com.action.COURSE_EXAM_DETAIL");

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return examLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name;
        public TextView time;
        public TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.exam_name);
            time = itemView.findViewById(R.id.exam_time);
            status = itemView.findViewById(R.id.exam_status);
        }
    }
}
