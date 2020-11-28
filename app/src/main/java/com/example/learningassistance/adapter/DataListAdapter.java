package com.example.learningassistance.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.utils.FileUtils;

import java.io.File;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {
    private List<Data> dataList;

    public DataListAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data data = dataList.get(position);

        final String ext = FileUtils.getFileExt(data.getUrl());

        holder.name.setText(data.getName());
        holder.icon.setImageResource(FileUtils.getImgId(ext));

//        设置下载文件的点击事件
        holder.view.setOnClickListener(v ->{
                Intent intent = new Intent("com.action.MORE_DATA_DETAIL");
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",data);
                intent.putExtras(bundle);
                intent.putExtra("ext",ext);
                v.getContext().startActivity(intent);
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
