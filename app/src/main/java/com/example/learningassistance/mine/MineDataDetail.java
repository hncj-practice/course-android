package com.example.learningassistance.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.MineDetailAdapter;
import com.example.learningassistance.entity.MineDetail;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MineDataDetail extends AppCompatActivity {
    private TextView title_text;
    private ImageView title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_data_detail);

        Utils.setTitle(this,"我的信息");

        List<MineDetail> list = initDetail();
        RecyclerView recyclerView = findViewById(R.id.recycler_mine_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MineDetailAdapter adapter = new MineDetailAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    public void goBack(View view) {
        finish();
    }

    private List<MineDetail> initDetail(){
        List<MineDetail> list = new ArrayList<>();
        list.add(new MineDetail(R.drawable.icon_user_avater,"选项名","这是描述的具体值"));
        list.add(new MineDetail(R.drawable.icon_user_avater,"选项名","这是描述的具体值"));
        list.add(new MineDetail(R.drawable.icon_user_avater,"选项名","这是描述的具体值"));
        list.add(new MineDetail(R.drawable.icon_user_avater,"选项名","这是描述的具体值"));
        list.add(new MineDetail(R.drawable.icon_user_avater,"选项名","这是描述的具体值"));
        list.add(new MineDetail(R.drawable.icon_user_avater,"选项名","这是描述的具体值"));
        return list;
    }
}