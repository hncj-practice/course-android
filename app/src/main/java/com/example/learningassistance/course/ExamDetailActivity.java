package com.example.learningassistance.course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.FixingAdapter;
import com.example.learningassistance.adapter.SelectQuestionAdapter;
import com.example.learningassistance.entity.Question;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamDetailActivity extends AppCompatActivity {
    @BindView(R.id.exam_layout_select) LinearLayout select;
    @BindView(R.id.exam_layout_fixing) LinearLayout fixing;
    @BindView(R.id.exam_layout_judge) LinearLayout judge;

    private SelectQuestionAdapter selectAdapter = null;
    private JudgeAdapter judgeAdapter = null;
    private FixingAdapter fixingAdapter = null;

    private String type;
    private String sno;
    private String examId;
    private int achievement = 0;

    private Integer questionNum;
    private List<Question> selectLists = new ArrayList<>();
    private List<Question> judgeLists = new ArrayList<>();
    private List<Question> fixingLists = new ArrayList<>();

//    记录每一题的分数
    private Map<Integer, Integer> marks;

    Handler handler = new Handler(msg -> {
        String question;
        String answer;
        Question q;
        if (msg.what == 1){
            JSONArray array = JSON.parseArray(msg.getData().getString("data"));
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                question = object.getString("question");
                answer = object.getString("panswer");
                q = new Question(question,answer);
                addQuestion(object.getString("ptype"),q);
            }
            questionNum = selectLists.size() + fixingLists.size() + judgeLists.size();
            for (Integer i = 0; i < questionNum; i++) {
                marks.put(i,(i+1)*10);
            }
            showView();
        }
        return false;
    });

    Handler commitHandler = new Handler(msg -> {
        if (msg.what == 1){
            Intent intent = new Intent("com.action.COURSE_SHOW_ACHIEVEMENT");
            intent.putExtra("achievement",String.valueOf(achievement));
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "提交失败，请重试", Toast.LENGTH_SHORT).show();
        }
        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        ButterKnife.bind(this);

        marks = new HashMap<>();

        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("data"));

        SharedPreferences preferences = getSharedPreferences("loginHistory", MODE_PRIVATE);
        type = preferences.getString("currentUserType","");
        sno = preferences.getString("currentUserId","");

        examId = intent.getStringExtra("paperid");
        Map<String, String> map = new HashMap<>();
        map.put("paperid",examId);
        Utils.getNetData("problem/getproblembypaperid",map,handler);
    }

    private void addQuestion(String type, Question question){
        switch (type){
            case "1":
                selectLists.add(question);
                break;
            case "2":
                fixingLists.add(question);
                break;
            case "3":
                judgeLists.add(question);
                break;
            default:
                break;
        }
    }

    private void showView(){
        if (selectLists.size() < 1){
            select.setVisibility(View.GONE);
        } else {
            selectAdapter = new SelectQuestionAdapter(selectLists);
            Utils.setRecycler(this,R.id.recycler_question_select,selectAdapter);
        }
        if (judgeLists.size() < 1){
            judge.setVisibility(View.GONE);
        } else {
            judgeAdapter = new JudgeAdapter(judgeLists);
            Utils.setRecycler(this,R.id.recycler_question_judge,judgeAdapter);
        }
        if (fixingLists.size() < 1){
            fixing.setVisibility(View.GONE);
        } else {
            fixingAdapter = new FixingAdapter(fixingLists);
            Utils.setRecycler(this,R.id.recycler_question_fixing,fixingAdapter);
        }
    }

    /**
     * 获得用户的答案
     */
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

//    获得用户得到的总分并上传到数据库中
    public void getAchievement(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("交卷");
        dialog.setMessage("请认真检查是否有遗漏,是否交卷?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是", (dialog12, which) -> {
            Map<Integer, String> userAnswers = getUserAnswer();
            Map<Integer, String> rigthAnswers = getRigthAnswers(selectLists,judgeLists,fixingLists);
            int position = rigthAnswers.size() - 1;
            while (position-- >= 0){
                if ( userAnswers.get(position) != null && userAnswers.get(position).equals(rigthAnswers.get(position))){
                    achievement += marks.get(position);
                }
            }
            /**
             * 上传考试成绩到服务器同时跳转到成绩显示页面
             */
            if (type.equals("1")){
                Map<String, String> map = new HashMap<>();
                map.put("sno",sno);
                map.put("paperid",examId);
                map.put("grade",String.valueOf(achievement));
                Utils.getNetData("paper/updatetestgrade",map,commitHandler);
            } else {
                Intent intent = new Intent("com.action.COURSE_SHOW_ACHIEVEMENT");
                intent.putExtra("achievement",String.valueOf(achievement));
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("否", (dialog1, which) -> {
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

}