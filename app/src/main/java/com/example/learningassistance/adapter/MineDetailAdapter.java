package com.example.learningassistance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.entity.MineDetail;

import java.util.List;

public class MineDetailAdapter extends RecyclerView.Adapter<MineDetailAdapter.ViewHolder> {
    private List<MineDetail> mDetailList;

    public MineDetailAdapter(List<MineDetail> mDetailList) {
        this.mDetailList = mDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_mine_detail_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MineDetail detail = mDetailList.get(position);
        holder.detail.setText(detail.getDetail());
        holder.img.setImageResource(detail.getImgId());
        holder.name.setText(detail.getName());
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView name;
        public TextView detail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mine_detail_item_image);
            name = itemView.findViewById(R.id.mine_detail_item_name);
            detail = itemView.findViewById(R.id.mine_detail_item_detail);
        }
    }
}
