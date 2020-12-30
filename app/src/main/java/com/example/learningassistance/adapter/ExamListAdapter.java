package com.example.learningassistance.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.ExamList;
import com.example.learningassistance.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    private List<ExamList> examLists;
    private Fragment activity;
    private String type;
    private String id;

    private ExamList currentExam;

    public ExamListAdapter(List<ExamList> examLists, Fragment activity) {
        this.examLists = examLists;
        this.activity = activity;
    }

    Handler handler = new Handler(msg -> {
        if (msg.what == 1){
            Intent intent = new Intent("com.action.COURSE_SHOW_ACHIEVEMENT");
            intent.putExtra("achievement",msg.getData().getString("data"));
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent("com.action.COURSE_EXAM_DETAIL");
            intent.putExtra("data",currentExam.getName());
            intent.putExtra("paperid",currentExam.getExamId());
            activity.startActivity(intent);
        }
        return false;
    });

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_exam_item, parent, false);
        SharedPreferences preferences = view.getContext().getSharedPreferences("loginHistory", Context.MODE_PRIVATE);
        type = preferences.getString("currentUserType","");
        id = preferences.getString("currentUserId","");
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
            if (type.equals("1")){
                Map<String, String> map = new HashMap<>();
                map.put("studentid",id);
                map.put("paperid",exam.getExamId());
                currentExam = exam;
                Utils.getNetData("paper/findscorebysnoandpaperid",map,handler);
                return;
            }
            Intent intent = new Intent("com.action.COURSE_EXAM_DETAIL");
            intent.putExtra("data",exam.getName());
            intent.putExtra("paperid",exam.getExamId());
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
