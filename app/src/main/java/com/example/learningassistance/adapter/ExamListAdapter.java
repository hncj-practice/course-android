package com.example.learningassistance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.ExamList;

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
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(examLists.get(position).getName());
        holder.time.setText(examLists.get(position).getTime());
        holder.status.setText(examLists.get(position).getStatus());
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
