package com.example.learningassistance.course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.FixingAdapter;
import com.example.learningassistance.adapter.SelectQuestionAdapter;
import com.example.learningassistance.entity.Question;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamDetailActivity extends AppCompatActivity {
//    分别存储该套试卷的正确答案和用户的答案
//    private String[] userChoice;
//    private String[] rightAnswer;

    String question = "以下关于线性分类器说法不正确的（    ）$A:线性分类器的一种直观的最优决策边界为最大间隔边界$B:线性可分的情形下，线性分类器的决策边界可以是多样的$C:线性分类器打分越高的样例越离决策边界越近，具有更高的分类置信度$D:训练好的SVM模型直接给出样例的列别标签";
    String fixingQuestion = "粒度是对数据仓库中数据的综合程度高低的一个衡量。粒度越小,细节程度____,综合程度____,回答查询的种类____。";
    String fixingAnswer = "越高$越低$越多";
//    String rigthAnswer = "错";
//    int type = 1;

    private SelectQuestionAdapter selectAdapter;
    private JudgeAdapter judgeAdapter;
    private FixingAdapter fixingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("data"));

        selectAdapter = new SelectQuestionAdapter(initQuestion());
        Utils.setRecycler(this,R.id.recycler_question_select,selectAdapter);

        judgeAdapter = new JudgeAdapter(initJudge());
        Utils.setRecycler(this,R.id.recycler_question_judge,judgeAdapter);

        fixingAdapter = new FixingAdapter(initFixing());
        Utils.setRecycler(this,R.id.recycler_question_fixing,fixingAdapter);


    }

    private Map<Integer, String> getUserAnswer(){
        Map<Integer, String> userAnswer = new HashMap<>();
        int position = 0;

        Map<Integer, String> selectAnswers = selectAdapter.getUserAnswers();
        for (int i = 0 ;i < selectAnswers.size();i++){
            userAnswer.put( i + position , selectAnswers.get(i));
        }
        position = position + selectAnswers.size();

        Map<Integer, String> judgeAnswers = judgeAdapter.getUserAnswers();
        for (int i = 0 ;i < judgeAnswers.size();i++){
            userAnswer.put( i + position , judgeAnswers.get(i));
        }
        position = position + judgeAnswers.size();

        Map<Integer, String> fixingAnswers = fixingAdapter.getUserAnswers();
        for (int i = 0 ;i < fixingAnswers.size();i++){
            userAnswer.put( i + position , fixingAnswers.get(i));
        }

        return userAnswer;
    }

    public void getAchievement(View view) {
        Map<Integer, String> userAnswer = getUserAnswer();
        System.out.println("da");
    }

    /**
     * 判断题的适配器
     */
    public class JudgeAdapter extends RecyclerView.Adapter<JudgeAdapter.ViewHolder>{

        private List<String> questionList;
        private Map<Integer, String> userAnswers;

        public JudgeAdapter(List<String> questionList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_question_judge, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.question.setText(questionList.get(position));
            holder.rightL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.right.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.style_exam_answer_selected));
                    holder.wrong.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.style_exam_answer_default));
                    userAnswers.put(position,"对");
                }
            });
            holder.wrongL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.wrong.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.style_exam_answer_selected));
                    holder.right.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.style_exam_answer_default));
                    userAnswers.put(position,"错");
                }
            });
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public View right,wrong;
            public LinearLayout rightL,wrongL;
            public TextView question;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                right = itemView.findViewById(R.id.exam_judge_right);
                rightL = itemView.findViewById(R.id.judge_right_layout);
                wrong = itemView.findViewById(R.id.exam_judge_wrong);
                wrongL = itemView.findViewById(R.id.judge_wrong_layout);
                question = itemView.findViewById(R.id.exam_judge_question);
            }
        }

    }

    public List<Question> initQuestion(){
        List<Question> questionList = new ArrayList<>();
        String[] split = question.split("\\$", 2);
        Question question = new Question(split[0],split[1]);
        questionList.add(question);
        questionList.add(question);
        return questionList;
    }

    public List<String> initJudge(){
         List<String> questionList = new ArrayList<>();
         questionList.add("序列最小优化算法无法在原始问题增加松弛变量以后使用");
         questionList.add("序列最小优化算法无法在原始问题增加松弛变量以后使用");
         return questionList;
    }

    public List<Question> initFixing(){
        List<Question> questionList = new ArrayList<>();
        Question question = new Question(fixingQuestion,fixingAnswer);
        questionList.add(question);
        questionList.add(question);
        return questionList;
    }
}