package com.example.learningassistance.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.entity.CourseList;

import org.jsoup.Jsoup;
import org.litepal.LitePalApplication;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;


public class Utils {
    public static final String IMG_DIR = "/storage/emulated/0/Android/data/com.example.learningassistance/files/localImg/";

    public static void setTitle(final Activity view, final String titleText){
        TextView title_text = view.findViewById(R.id.title_text);
        title_text.setText(titleText);

        ImageView title_back = view.findViewById(R.id.title_back);
        title_back.setOnClickListener(v -> view.finish());
    }

    public static void setRecycler(View view, int resourceId, RecyclerView.Adapter adapter){
        RecyclerView recyclerView = view.findViewById(resourceId);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    public static void setRecycler(Activity view, int resourceId, RecyclerView.Adapter adapter){
        RecyclerView recyclerView = view.findViewById(resourceId);
        LinearLayoutManager manager = new LinearLayoutManager(view.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    public static void getNetData(String url, Map<String, String> data, final Handler handler){
        new Thread(()  -> {
            String result = null;
            try {
                result = Jsoup.connect("http://123.56.156.212/Interface/" + url)
                        .data(data)
                        .ignoreContentType(true)
                        .timeout(5000)
                        .post()
                        .body()
                        .text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message message = new Message();
            JSONObject jsonObject = JSON.parseObject(result);
            Bundle bundle = new Bundle();
            if (jsonObject.getInteger("code") != null && jsonObject.getInteger("code") == 200){
                String arrayStr = jsonObject.getString("data");
                bundle.putString("data",arrayStr);
                message.what = 1;
            } else {
                message.what = 0;
            }
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }

    public static void getCourseData(Map<String, String> data, final Handler handlerS){
        new Thread(()  -> {
            Map<Integer, String> result = new HashMap<>();
            try {
                int i = 1;
                while (i < 9){
                    data.put("semesterid", Integer.toString(i));
                    String response = Jsoup.connect("http://123.56.156.212/Interface/course/getcoursebysno")
                            .data(data)
                            .ignoreContentType(true)
                            .timeout(10000)
                            .post()
                            .body()
                            .text();
                    result.put(i, response);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) result);
            message.setData(bundle);
            handlerS.sendMessage(message);
        }).start();
    }

    public static void setRecentCourse(String s){
        String s1,s2,s3,s4;
        SharedPreferences preferences = LitePalApplication.getContext().getSharedPreferences("loginHistory", Context.MODE_PRIVATE);
        s1 = preferences.getString("recentCourse1","null");
        s2 = preferences.getString("recentCourse2","null");
        s3 = preferences.getString("recentCourse3","null");
        s4 = preferences.getString("recentCourse4","null");
        SharedPreferences.Editor editor = preferences.edit();
        if (s.equals(s1)){
            editor.putString("recentCourse1",s1);
        } else if (s.equals(s2)){
            editor.putString("recentCourse1",s2);
            editor.putString("recentCourse2",s1);
        } else if (s.equals(s3)){
            editor.putString("recentCourse1",s3);
            editor.putString("recentCourse2",s1);
            editor.putString("recentCourse3",s2);
        } else if (s.equals(s4)){
            editor.putString("recentCourse1",s4);
            editor.putString("recentCourse2",s1);
            editor.putString("recentCourse3",s2);
            editor.putString("recentCourse4",s3);
        } else {
            editor.putString("recentCourse1",s);
            editor.putString("recentCourse2",s1);
            editor.putString("recentCourse3",s2);
            editor.putString("recentCourse4",s3);
        }
        editor.apply();
    }

    public static List<String> getRecentCourse(){
        List<String> list = new ArrayList<>();
        SharedPreferences preferences = LitePalApplication.getContext().getSharedPreferences("loginHistory", Context.MODE_PRIVATE);
        list.add(preferences.getString("recentCourse1","null"));
        list.add(preferences.getString("recentCourse2","null"));
        list.add(preferences.getString("recentCourse3","null"));
        list.add(preferences.getString("recentCourse4","null"));
        return list;
    }

    /**
     * 将时间戳转换为日期格式的字符串
     */
    public static String timeStampToDate(String stamp){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(Long.parseLong(stamp)));
    }

    /**
     * (x,y)是否在view的区域内
     * @param view view组件
     * @param x 点击的x轴坐标
     * @param y 点击的y轴坐标
     * @return 是否在view中
     */
    public static boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left && x <= right;
    }

}
