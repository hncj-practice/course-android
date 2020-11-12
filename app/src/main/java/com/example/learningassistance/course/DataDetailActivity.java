package com.example.learningassistance.course;

import androidx.appcompat.app.AppCompatActivity;

import com.arialyy.aria.core.Aria;
import com.example.learningassistance.R;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.utils.AccessUtil;
import com.example.learningassistance.utils.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class DataDetailActivity extends AppCompatActivity {
    String name;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        Intent intent = getIntent();
        data = (Data) intent.getSerializableExtra("data");

        String directory = data.getLocalPath() + File.separator + data.getType();
        name = directory + File.separator + data.getName();

        File file = new File(directory);
        if (!file.exists()){
            file.mkdirs();
        }
        Boolean localStatus = isLocalDocument(name);
        AccessUtil.verifyStoragePermissions(this);
    }

    public Boolean isLocalDocument(String realPath){
        File file = new File(realPath);
        if (file.exists()){
            return true;
        }
        return false;
    }

    public void startDownload(View view) {
        Aria.download(this).load("https://tb-video.bdstatic.com/tieba-smallvideo/50_5dedbe722c517153666e7b6ddd9c64c6.mp4")
                .setFilePath(name)
                .create();
    }

    public void startRead(View view) {
        FileUtils.openFile(this,new File(name),FileUtils.getFileExt(data.getName()));
    }
}