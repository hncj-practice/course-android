package com.example.learningassistance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.Question;
import com.example.learningassistance.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQuestionAdapter extends RecyclerView.Adapter<SelectQuestionAdapter.ViewHolder> {
    private List<Question> questionList;

    private Map<Integer, String> userAnswers;

    public SelectQuestionAdapter(List<Question> questionList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_question_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.question.setText(question.getQuestion());
        Utils.setRecycler(holder.view,holder.answer.getId(),new SelectAnswerAdapter(question.getAnswers(),position));
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

    /**
     * 每道选择题选项的recyclerview适配器
     */
    public class SelectAnswerAdapter extends RecyclerView.Adapter<SelectAnswerAdapter.ViewHolder> {
        private String[] answers;
        private int index;
        Boolean click = false;
        //    记录上一次的选择
        View tempView = null;

        public SelectAnswerAdapter(String[] answers, int index) {
            this.answers = answers;
            this.index = index;
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
                    userAnswers.put(index,getStringAnswer(position));
                }
            });
        }

        /**
         * 获得String类型的答案,即将1,2,3转换为ABC
         * @param i int型的答案
         * @return String型的答案
         */
        public String getStringAnswer(int i){
            String t;
            switch (i){
                case 0:
                    t = "A";
                    break;
                case 1:
                    t = "B";
                    break;
                case 2:
                    t = "C";
                    break;
                case 3:
                    t = "D";
                    break;
                case 4:
                    t = "E";
                    break;
                case 5:
                    t = "F";
                    break;
                case 6:
                    t = "G";
                    break;
                case 7:
                    t = "H";
                    break;
                default:
                    t = "";
                    break;
            }
            return t;
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

}
