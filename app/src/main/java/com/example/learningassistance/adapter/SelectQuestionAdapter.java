package com.example.learningassistance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.SelectQuestion;
import com.example.learningassistance.utils.Utils;

import java.util.List;

public class SelectQuestionAdapter extends RecyclerView.Adapter<SelectQuestionAdapter.ViewHolder> {
    private List<SelectQuestion> questionList;

    public SelectQuestionAdapter(List<SelectQuestion> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_question_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectQuestion selectQuestion = questionList.get(position);
        holder.question.setText(selectQuestion.getQuestion());
        Utils.setRecycler(holder.view,holder.answer.getId(),new SelectAnswerAdapter(selectQuestion.getAnswers()));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView answer;
        TextView question;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.recycler_select_answer);
            question = itemView.findViewById(R.id.exam_select_question);
            view = itemView;
        }
    }
}
