package com.example.learningassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;

import com.arialyy.aria.core.task.DownloadTask;
import com.example.learningassistance.utils.AccessUtil;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private String LOCAL_PATH = Utils.IMG_DIR;
    private static final int LOCKED = 0;
    private static final int UNLOCK = 1;

    @BindView(R.id.login_avatar) ImageView avatar;
    @BindView(R.id.login_button) ImageView login;
    @BindView(R.id.login_username) EditText username;
    @BindView(R.id.login_password) EditText password;
    @BindView(R.id.login_type) TextView userType;

    private Integer loginType;
    private Dialog dialog1;

    private int Lock;

    public SharedPreferences loginHistory;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                String data = msg.getData().getString("data");
                JSONObject jsonObject = JSON.parseObject(data);

                String type;
                String username = jsonObject.getString("sno");
                type = "1";
                if (username == null){
                    username = jsonObject.getString("tno");
                    type = "2";
                }
                String avatar = jsonObject.getString("avatar");
                String localPath = LOCAL_PATH + username + "t" + type + ".png";
                jsonObject.put("localPath", localPath);

                loginHistory = getSharedPreferences("loginHistory", MODE_PRIVATE);
                SharedPreferences.Editor edit = loginHistory.edit();
                if (!Objects.equals(loginHistory.getString((username + "t" + type), ""), avatar)) {
                    startDownload(jsonObject.getString("avatar"), localPath);
                    edit.putString(username + "t" + type, avatar);
                }
                edit.putString("currentUserName", jsonObject.getString("name"));
                edit.putString("currentUserId", username);
                edit.putString("currentUserType",type);
                edit.putString("currentUserAvatar", jsonObject.getString("avatar"));
                edit.putString("localPath",localPath);
                edit.apply();

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("data", jsonObject.toJSONString());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
            Lock = UNLOCK;
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

        Lock = UNLOCK;

        File file = new File(LOCAL_PATH);
        if (!file.exists()){
            file.mkdirs();
        }

        RoundRectImageView.setCircle(avatar,R.drawable.icon_default_avatar,50,this);
        RoundRectImageView.setCircle(login,R.drawable.icon_login,30,this);

        dialog1 = setDialog();
        userType.setText("学生");
        loginType = 1;
        userType.setOnClickListener(this);

        username.setText("081417162");
        password.setText("000000");

        /*username.addTextChangedListener(new TextWatcher() {
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
                    if (!Objects.equals(loginHistory.getString(s.toString() + "t" + loginType, "null"), "null")){
                        RoundRectImageView.setUserAvatar(avatar,LOCAL_PATH + s.toString() + "t" + loginType + ".png",50);
                    }
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_type:
                dialog1.show();
                break;
            case R.id.btn_choose_stu:
                userType.setText("学生");
                loginType = 1;
                dialog1.dismiss();
                break;
            case R.id.btn_choose_tea:
                userType.setText("教师");
                loginType = 2;
                dialog1.dismiss();
                break;
            default:
                break;
        }
    }

    private Dialog setDialog() {
        Dialog dialog = new Dialog(this,R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.login_dialog, null, false);
        view.findViewById(R.id.btn_choose_tea).setOnClickListener(this);
        view.findViewById(R.id.btn_choose_stu).setOnClickListener(this);
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

    public void startDownload(String url,String filePath){
        if (url.equals("default")){
            return;
        }
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
        if (Lock == LOCKED){
            Toast.makeText(this,"请勿重复点击",Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.getText().toString().length() != 9 || password.getText().length() < 6){
            Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("username",username.getText().toString());
        map.put("password",password.getText().toString());
        map.put("type", String.valueOf(loginType));
        Lock = LOCKED;
        Utils.getNetData("account/login",map,handler);
    }

}