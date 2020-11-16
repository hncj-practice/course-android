package com.example.learningassistance.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.learningassistance.R;

public class SelectAnswerAdapter extends RecyclerView.Adapter<SelectAnswerAdapter.ViewHolder> {
    private String[] answers;
    Boolean click = false;
//    记录上一次的选择
    View tempView = null;

    public SelectAnswerAdapter(String[] answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_select_answer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.answer.setText(answers[position]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据click的状态判定选项是否被选中,具体的转换细节我也没看懂.......
                if (!click){
                    holder.button.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.style_exam_answer_selected));
                    tempView = holder.button;
                    click = true;
                } else {
                    tempView.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.style_exam_answer_default));
                    tempView = null;
                    click = false;
                    holder.view.performClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView answer;
        View view;
        View button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.exam_select_button);
            answer = itemView.findViewById(R.id.exam_select_answer);
            view = itemView;
        }
    }
}
