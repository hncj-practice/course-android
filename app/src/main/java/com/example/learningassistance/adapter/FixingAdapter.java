package com.example.learningassistance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.Question;
import com.example.learningassistance.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixingAdapter extends RecyclerView.Adapter<FixingAdapter.ViewHolder> {
    private List<Question> questionList;
    private Map<Integer, String> userAnswers;

    public FixingAdapter(List<Question> questionList) {
        this.questionList = questionList;
        this.userAnswers = new HashMap<>();
    }

    public Map<Integer, String> getUserAnswers() {
        int i = questionList.size();
        while (i-- > 0){
            if (userAnswers.get(i) == null){
                userAnswers.put(i,"");
            }
        }
        return userAnswers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_question_fixing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.question.setText(question.getQuestion());
        int number = question.getAnswers().length;
        Utils.setRecycler(holder.itemView,R.id.recycler_fixing_answer,new FixingAnswerAdapter(number,position));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.exam_fixing_question);
        }
    }

    /**
     * 一个填空题中选项的列表 其中userAnswer存储每个选择题的答案
     */
    public class FixingAnswerAdapter extends RecyclerView.Adapter<FixingAnswerAdapter.ViewHolder>{
        private int num;
        private int index;
        private String[] userAnswer;

        private int onFocusId = -1;

        public FixingAnswerAdapter(int num, int index) {
            this.num = num;
            this.index = index;
            this.userAnswer = new String[num];
            for (int i = 0; i<this.userAnswer.length;i++){
                this.userAnswer[i] = " ";
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_fixing_answer, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus){
                        onFocusId = position;
                    } else if (!hasFocus && onFocusId == position){
                        String answer = holder.answer.getText().toString();
                        userAnswer[position] = (answer.length() < 1 ? " " :answer);
                        userAnswers.put(index,getStringAnswer(userAnswer));
                    }
                }
            });
        }

        private String getStringAnswer(String[] answers){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<answers.length;i++){
                if ( i > 0 ){
                    sb.append("$");
                }
                    sb.append(answers[i]);
            }
            return sb.toString();
        }

        @Override
        public int getItemCount() {
            return num;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            EditText answer;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                answer = itemView.findViewById(R.id.exam_fixing_answer);
            }
        }
    }
}
