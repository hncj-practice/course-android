package com.example.learningassistance.course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverviewActivity extends AppCompatActivity {
    private String cid;

    private String id;
    private String name;
    private String exam;
    private String topic;
    //平均分还没有接口暂不做处理
    private String average;

    private List<String> list = new ArrayList<>();

    Handler handler = new Handler(msg -> {
        if (msg.what == 1){
            JSONArray array = JSON.parseArray(msg.getData().getString("data"));
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                id = object.getString("sno");
                name = object.getString("sname");
                exam = object.getString("finpapernum") + "/" + object.getString("totalpapernum");
                topic = object.getString("commentnum");
                list.add(id +","+ name +","+ exam +","+ topic);
            }
            Utils.setRecycler(this,R.id.recycler_overview,new OverviewAdapter());
        } else {
            Toast.makeText(this, "未查询到相关信息", Toast.LENGTH_SHORT).show();
        }
        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Utils.setTitle(this,"学生统计");

        cid = getIntent().getStringExtra("courseId");
        Map<String, String> map = new HashMap<>();
        map.put("courseid",cid);
        Utils.getNetData("student/statistic",map,handler);
    }

    public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder>{
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_overview_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String[] split = list.get(position).split(",");
            holder.idV.setText(split[0]);
            holder.nameV.setText(split[1]);
            holder.examV.setText(split[2]);
            holder.topicV.setText(split[3]);
            holder.averageV.setText("0");

            if (position % 2 == 0){
                holder.layout.setBackgroundColor(Color.parseColor("#E8F3F8"));
            } else {
                holder.layout.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView idV,nameV,examV,topicV,averageV;
            public View layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                layout = itemView;
                idV = itemView.findViewById(R.id.recycler_overview_id);
                nameV = itemView.findViewById(R.id.recycler_overview_name);
                examV = itemView.findViewById(R.id.recycler_overview_exam);
                topicV = itemView.findViewById(R.id.recycler_overview_topic);
                averageV = itemView.findViewById(R.id.recycler_overview_average);
            }
        }
    }
}