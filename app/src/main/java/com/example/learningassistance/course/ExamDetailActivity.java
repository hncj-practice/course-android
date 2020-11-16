package com.example.learningassistance.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.SelectQuestionAdapter;
import com.example.learningassistance.entity.SelectQuestion;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ExamDetailActivity extends AppCompatActivity {

    String question = "以下关于线性分类器说法不正确的（    ）$A:线性分类器的一种直观的最优决策边界为最大间隔边界$B:线性可分的情形下，线性分类器的决策边界可以是多样的$C:线性分类器打分越高的样例越离决策边界越近，具有更高的分类置信度$D:训练好的SVM模型直接给出样例的列别标签";
    String rigthAnswer = "C";
    int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("data"));

        Utils.setRecycler(this,R.id.recycler_question_select,new SelectQuestionAdapter(initQuestion()));

    }

    public List<SelectQuestion> initQuestion(){
        List<SelectQuestion> questionList = new ArrayList<>();
        String[] split = question.split("\\$", 2);
        SelectQuestion question = new SelectQuestion(split[0],split[1].split("\\$"));
        questionList.add(question);
        questionList.add(question);
        questionList.add(question);
        questionList.add(question);
        questionList.add(question);
        return questionList;
    }
}