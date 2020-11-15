package com.example.learningassistance.course;

import androidx.appcompat.app.AppCompatActivity;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.example.learningassistance.R;
import com.example.learningassistance.entity.Data;
import com.example.learningassistance.utils.AccessUtil;
import com.example.learningassistance.utils.FileUtils;
import com.example.learningassistance.utils.MyProgressBar;
import com.example.learningassistance.utils.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DataDetailActivity extends AppCompatActivity {
    private String pathName;
    private Data data;

    @BindView(R.id.data_detail_progress) MyProgressBar progressBar;
    @BindView(R.id.data_detail_button) TextView button;
    @BindView(R.id.data_detail_icon) ImageView icon;
    @BindView(R.id.data_detail_name) TextView docName;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);
        ButterKnife.bind(this);
        AccessUtil.verifyStoragePermissions(this);
        Aria.download(this).register();
        Utils.setTitle(this,"");

        Intent intent = getIntent();
        data = (Data) intent.getSerializableExtra("data");
        String ext = intent.getStringExtra("ext");
        String directory = getExternalFilesDir(null) + File.separator + data.getType();
        pathName = directory + File.separator + data.getName();
        docName.setText(data.getName());
        icon.setImageResource(FileUtils.getImgId(ext));

        File file = new File(directory);
        if (!file.exists()){
            file.mkdirs();
        }

        changeButton(pathName);
    }

    /**
     * 如果文件已存在在本地,则将按钮设置为点击打开
     * @param realPath 本地文件的路径
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void changeButton(String realPath){
        File file = new File(realPath);
        if (file.exists()){
            button.setText(R.string.clickToRead);
            button.setBackground(getDrawable(R.drawable.style_topic_send_button_selected));
            button.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.data_detail_button) void click(View view){
        if (button.getText().toString().equals(getString(R.string.clickToDownload))){
            Aria.download(this)
                    .load(data.getUrl())
                    .setFilePath(pathName)
                    .ignoreFilePathOccupy()
                    .create();
        } else {
            startRead();
        }
    }

    @Download.onTaskPre void pre(DownloadTask task){
        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setTitle("开始下载");
        start.setMessage("是否开始下载?");
        start.setCancelable(false);
        start.setPositiveButton("开始", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }
        });
        start.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.cancel();
            }
        });
        start.show();
    }

    @Download.onTaskRunning protected void running(DownloadTask task){
        progressBar.setProgress(task.getPercent());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Download.onTaskComplete void complete(DownloadTask task){
        AlertDialog.Builder success = new AlertDialog.Builder(this);
        success.setTitle("下载完成");
        success.setMessage("指定文件已下载到本地");
        success.setCancelable(true);
        progressBar.setVisibility(View.INVISIBLE);
        button.setText(R.string.clickToRead);
        button.setBackground(getDrawable(R.drawable.style_topic_send_button_selected));
        button.setTextColor(getResources().getColor(R.color.white));
        success.show();
    }

    public void startRead() {
        FileUtils.openFile(this,new File(pathName),FileUtils.getFileExt(data.getName()));
    }
}