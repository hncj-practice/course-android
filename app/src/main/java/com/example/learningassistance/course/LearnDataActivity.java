package com.example.learningassistance.course;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningassistance.R;
import com.example.learningassistance.adapter.DataListAdapter;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LearnDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learndata);
        Utils.setTitle(this,"资料");

        Utils.setRecycler(this,R.id.recycler_data, new DataListAdapter(initData()));
    }

    public List<Data> initData(){
        List<Data> dataList = new ArrayList<>();
        dataList.add(new Data("https://fyz1522426323.oss-cn-beijing.aliyuncs.com/美丽的神话MV_胡歌_白冰_高清mv在线观看_酷我音乐.mp4",3));
        dataList.add(new Data("https://fyz1522426323.oss-cn-beijing.aliyuncs.com/中央气象台.docx",2));
        dataList.add(new Data("https://tb-video.bdstatic.com/tieba-smallvideo/50_5dedbe722c517153666e7b6ddd9c64c6.mp4",3));
        dataList.add(new Data("https://tva2.sinaimg.cn/large/0072Vf1pgy1foxlholn3ej31hc0u0qnp.jpg",1));
        dataList.add(new Data("https://tb-video.bdstatic.com/tieba-smallvideo/50_5dedbe722c517153666e7b6ddd9c64c6.mp4",3));
        dataList.add(new Data("https://tva2.sinaimg.cn/large/0072Vf1pgy1foxlholn3ej31hc0u0qnp.jpg",1));
        dataList.add(new Data("https://tb-video.bdstatic.com/tieba-smallvideo/50_5dedbe722c517153666e7b6ddd9c64c6.mp4",3));
        dataList.add(new Data("https://tva3.sinaimg.cn/large/0075auPSly1fqb5pswimej31hc0u0kbj.jpg",1));
        return dataList;
    }
}
