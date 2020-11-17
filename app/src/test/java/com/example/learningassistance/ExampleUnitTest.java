package com.example.learningassistance;

import android.app.Activity;
import android.os.Handler;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.example.learningassistance.utils.FileUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <icon_user_avater href="http://d.android.com/tools/testing">Testing documentation</icon_user_avater>
 */
public class ExampleUnitTest {
    String question = "以下关于线性分类器说法不正确的（    ）$A:线性分类器的一种直观的最优决策边界为最大间隔边界$B:线性可分的情形下，线性分类器的决策边界可以是多样的$C:线性分类器打分越高的样例越离决策边界越近，具有更高的分类置信度$D:训练好的SVM模型直接给出样例的列别标签";

    @Test
    public void addition_isCorrect() {
        String[] split = question.split("\\$");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<split.length;i++){
            if ( i > 0 ){
                sb.append("$");
            }
            sb.append(split[i]);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test(){
        Map<Integer,String> list = new HashMap<>();
        int max = 5;
        while (max-- > 0){
            if (list.get(max) == null){
                list.put(max,"第"+max+"个");
            }
        }
    }

}