package com.example.learningassistance.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.Option;

import java.util.List;

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.ViewHolder> {
    private List<Option> mOptionList;
    private String data;

    public MineAdapter(List<Option> mOptionList, String data) {
        this.mOptionList = mOptionList;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_mine_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Option option = mOptionList.get(position);
        holder.optionName.setText(option.getName());
        holder.optionImg.setImageResource(option.getImgId());
//        为选项设置监听器
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindClick(v,position);
            }
        });
    }

    public void bindClick(View v, int position){
        switch (position){
            case 0:
                Activity activity = (Activity) v.getContext();
                Intent intent = new Intent("com.example.learningassistance.ACTION_START");
                intent.putExtra("data",data);
                intent.putExtra("topName",mOptionList.get(position).getName());
                activity.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return mOptionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView optionImg;
        TextView optionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            optionImg = itemView.findViewById(R.id.mine_item_image);
            optionName = itemView.findViewById(R.id.mine_item_name);
        }
    }
}
