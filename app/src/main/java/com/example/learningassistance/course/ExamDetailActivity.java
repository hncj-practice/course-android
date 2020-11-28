package com.example.learningassistance.course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    String selectQuestion = "以下关于线性分类器说法不正确的（    ）$A:线性分类器的一种直观的最优决策边界为最大间隔边界$B:线性可分的情形下，线性分类器的决策边界可以是多样的$C:线性分类器打分越高的样例越离决策边界越近，具有更高的分类置信度$D:训练好的SVM模型直接给出样例的列别标签";
    String selectAnswer = "B";
    String fixingQuestion = "粒度是对数据仓库中数据的综合程度高低的一个衡量。粒度越小,细节程度____,综合程度____,回答查询的种类____。";
    String fixingAnswer = "越高$越低$越多";
    String judgeQuestion = "序列最小优化算法无法在原始问题增加松弛变量以后使用";
    String judgeAnswer = "错";

    private SelectQuestionAdapter selectAdapter;
    private JudgeAdapter judgeAdapter;
    private FixingAdapter fixingAdapter;

    private Map<Integer, Integer> marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        marks = new HashMap<>();
        for (int i = 0; i < 6; i++){
            marks.put(i,i * 5 + 1);
        }


        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("data"));

        selectAdapter = new SelectQuestionAdapter(initSelect());
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("交卷");
        dialog.setMessage("请认真检查是否有遗漏,是否交卷?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<Integer, String> userAnswers = getUserAnswer();
                Map<Integer, String> rigthAnswers = getRigthAnswers(initSelect(),initJudge(),initFixing());
                int achievement = 0;
                int position = rigthAnswers.size() - 1;
                while (position-- >= 0){
                    if ( userAnswers.get(position) != null && userAnswers.get(position).equals(rigthAnswers.get(position))){
                        achievement += marks.get(position);
                    }
                }
                Toast.makeText(view.getContext(), "最终得分为:"+ achievement +"分", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    public Map<Integer, String> getRigthAnswers(List<Question>... params){
        Map<Integer, String> rightAnswers = new HashMap<>();
        int i = 0;
        for (List<Question> q : params){
            for (Question question : q){
                rightAnswers.put(i++,question.getAnswers());
            }
        }
        return rightAnswers;
    }

    /**
     * 判断题的适配器
     */
    public class JudgeAdapter extends RecyclerView.Adapter<JudgeAdapter.ViewHolder>{

        private List<Question> questionList;
        private Map<Integer, String> userAnswers;

        public JudgeAdapter(List<Question> questionList) {
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
            holder.question.setText(questionList.get(position).getQuestion());
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

    public List<Question> initSelect(){
        List<Question> questionList = new ArrayList<>();
        Question question = new Question(selectQuestion,selectAnswer);
        questionList.add(question);
        questionList.add(question);
        return questionList;
    }

    public List<Question> initJudge(){
        List<Question> questionList = new ArrayList<>();
        Question question = new Question(judgeQuestion,judgeAnswer);
        questionList.add(question);
        questionList.add(question);
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