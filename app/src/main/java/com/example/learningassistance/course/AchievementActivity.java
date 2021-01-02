package com.example.learningassistance.course;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AchievementActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.semester) TextView semester;
    @BindView(R.id.start_search) TextView search;

    private Dialog dialog1;
    private String semesterid;

    Handler handler = new Handler(msg -> {
        if (msg.what == 0){
            Toast.makeText(this, "网络请求错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        JSONArray array = JSON.parseArray(msg.getData().getString("data"));
        List<String[]> courseList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String name = object.getString("coursename");
            String num = "未有成绩";
            if (object.getString("grade") != null){
                num = object.getString("grade");
            }
            String[] course = {name,num};
            courseList.add(course);
        }
        Utils.setRecycler(this,R.id.recycler_history_mark,new SemesterAdapter(courseList));
        return false;
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        ButterKnife.bind(this);

        Utils.setTitle(this,"历史成绩");

        Intent intent = getIntent();
        JSONObject data = JSON.parseObject(intent.getStringExtra("data"));

        dialog1 = setDialog();
        semester.setOnClickListener(this);
        search.setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            if (data.getString("sno").length() == 0 || semesterid == null){
                Toast.makeText(this, "错误查询", Toast.LENGTH_SHORT).show();
                return;
            }
            map.put("studentid", data.getString("sno"));
            map.put("semesterid",semesterid);
            Utils.getNetData("grade/getfinalgrade",map,handler);
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.semester:
                dialog1.show();
                break;
            case R.id.btn_semester_1:
                semester.setText("大一上学期");
                semesterid = "1";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_2:
                semester.setText("大一下学期");
                semesterid = "2";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_3:
                semester.setText("大二上学期");
                semesterid = "3";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_4:
                semester.setText("大二下学期");
                semesterid = "4";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_5:
                semester.setText("大三上学期");
                semesterid = "5";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_6:
                semester.setText("大三下学期");
                semesterid = "6";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_7:
                semester.setText("大四上学期");
                semesterid = "7";
                dialog1.dismiss();
                break;
            case R.id.btn_semester_8:
                semester.setText("大四下学期");
                semesterid = "8";
                dialog1.dismiss();
                break;
            default:
                break;
        }
    }

    private Dialog setDialog() {
        Dialog dialog = new Dialog(this,R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.semester_dialog, null, false);
        view.findViewById(R.id.btn_semester_1).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_2).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_3).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_4).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_5).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_6).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_7).setOnClickListener(this);
        view.findViewById(R.id.btn_semester_8).setOnClickListener(this);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogAnimation);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        view.measure(0, 0);
        lp.height = view.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        return dialog;
    }

    public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.ViewHolder>{
        List<String[]> list;

        public SemesterAdapter(List<String[]> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public SemesterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_mark, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SemesterAdapter.ViewHolder holder, int position) {
            String[] course = list.get(position);
            holder.name.setText(course[0]);
            holder.num.setText(course[1]);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name,num;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.mark_course_name);
                num = itemView.findViewById(R.id.mark_course_num);
            }
        }
    }

}
