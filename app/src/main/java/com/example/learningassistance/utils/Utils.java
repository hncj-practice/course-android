package com.example.learningassistance.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;


public class Utils {
    public static final String IMG_DIR = "/storage/emulated/0/Android/data/com.example.learningassistance/files/localImg/";

    public static void setTitle(final Activity view, final String titleText){
        TextView title_text = view.findViewById(R.id.title_text);
        title_text.setText(titleText);

        ImageView title_back = view.findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
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


    public static Intent setIntent(String value, String action) {
        Intent intent = new Intent(action);
        intent.putExtra("data",value);
        return intent;
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
            if (jsonObject.getInteger("code") == 200){
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
}
