package com.example.learningassistance.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arialyy.aria.core.Aria;
import com.example.learningassistance.R;
import com.example.learningassistance.course.LearnDataActivity;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.utils.FileUtils;

import java.io.File;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {
    private List<Data> dataList;

    public File file;

    public DataListAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

/*    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.arg1 ){
                case 1:
                    Toast.makeText(activity, "已下载完成", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    break;
                default:
                    Toast.makeText(activity, "下载出错了", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data data = dataList.get(position);

        String ext = FileUtils.getFileExt(data.getName());

        holder.name.setText(data.getName());
        holder.icon.setImageResource(FileUtils.getImgId(ext));

//        设置下载文件的点击事件
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.action.MORE_DATA_DETAIL");
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",data);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.data_icon);
            name = itemView.findViewById(R.id.data_name);
            view = itemView;
        }
    }
}
