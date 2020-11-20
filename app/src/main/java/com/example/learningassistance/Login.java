package com.example.learningassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;

import com.arialyy.aria.core.task.DownloadTask;
import com.example.learningassistance.utils.AccessUtil;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String LOCAL_PATH = Utils.IMG_DIR;

    @BindView(R.id.login_avatar) ImageView avatar;
    @BindView(R.id.login_button) ImageView login;
    @BindView(R.id.login_username) EditText username;
    @BindView(R.id.login_password) EditText password;
    @BindView(R.id.login_type) Spinner userType;

    private Integer loginType;

    public SharedPreferences loginHistory;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String data = msg.getData().getString("data");
                    JSONObject jsonObject = JSON.parseObject(data);

                    String type = jsonObject.getString("status");
                    String username = null;
                    if (type.equals("1")){
                        username = jsonObject.getString("sno");
                    } else if (type.equals("2")){
                        username = jsonObject.getString("tno");
                    }
                    String avatar = jsonObject.getString("avatar");
                    String localPath = LOCAL_PATH + username+"t"+type + ".png";
                    jsonObject.put("localPath",localPath);

                    loginHistory = getSharedPreferences("loginHistory", MODE_PRIVATE);
                    if (!loginHistory.getString((username + "t" + type),"").equals(avatar)){
                        startDownload(jsonObject.getString("avatar"),localPath);
                        SharedPreferences.Editor edit = loginHistory.edit();
                        edit.putString(username + "t" + type,avatar);
                        edit.apply();
                    }

                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra("data",jsonObject.toJSONString());
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Aria.download(this).register();

        AccessUtil.verifyStoragePermissions(this);

        File file = new File(LOCAL_PATH);
        if (!file.exists()){
            file.mkdirs();
        }

        RoundRectImageView.setCircle(avatar,R.drawable.icon_user_avater,50,this);
        RoundRectImageView.setCircle(login,R.drawable.icon_login,30,this);

        userType.setOnItemSelectedListener(this);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 9){
                    loginHistory = getSharedPreferences("loginHistory",MODE_PRIVATE);
//                    当首次登陆时,设置为默认头像,否则在本地区图片
                    if (!loginHistory.getString(s.toString() + "t" + loginType, "null").equals("null")){
                        RoundRectImageView.setUserAvatar(avatar,LOCAL_PATH + s.toString() + "t" + loginType + ".png",50);
                    }
                }
            }
        });

    }

    public void startDownload(String url,String filePath){
        Aria.download(this)
                .load(url)
                .ignoreFilePathOccupy()
                .setFilePath(filePath)
                .create();
    }

    @Download.onTaskComplete void onComplete(DownloadTask task){
        Log.d("tag","complete");
    }

    /**
     * 开始登陆
     * @param view 登录的按钮
     */
    public void startLogin(View view) {
        if (username.getText().toString().length() != 9 || password.getText().length() < 6){
            Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("username",username.getText().toString());
        map.put("password",password.getText().toString());
        map.put("type", String.valueOf(loginType));

        Utils.getNetData("account/login",map,handler);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loginType = parent.getSelectedItemPosition() + 1 ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}