package com.example.learningassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.utils.RoundRectImageView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String HOST = "http://123.56.156.212/Interface/";

    private ImageView avatar,login;
    private EditText username,password;
    @BindView
   (R.id.login_type) Spinner userType;
    private Integer loginType;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            JSONObject json = (JSONObject) msg.obj;
            if (json.getString("code").equals("200")){
                Map<String, String> map = new HashMap<>();
                String data = json.getString("data");
                Intent intent = new Intent(Login.this,MainActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            } else if (json.getString("code").equals("401")){
                Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initComponent();

    }

    /**
     * 加载页面的所有组件
     */
    public void initComponent(){
        avatar = findViewById(R.id.login_avatar);
        RoundRectImageView.setCircle(avatar,R.drawable.icon_user_avater,70,this);
        login = findViewById(R.id.login_button);
        RoundRectImageView.setCircle(login,R.drawable.icon_login,50,this);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        username.setText("888888888");
        password.setText("000000");

        userType.setOnItemSelectedListener(this);
    }

    /**
     * 获取接口返回的数据
     * @param url 接口地址
     * @param params 请求参数
     */
    public void getNetRequest(final String url , final Map<String, String> params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = Jsoup.connect(url)
                            .timeout(8000)
                            .data(params)
                            .userAgent("Mozilla")
                            .ignoreContentType(true)
                            .post()
                            .body()
                            .text();
                    Message message = new Message();
                    message.obj = JSON.parseObject(result);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 开始登陆
     * @param view 登录的按钮
     */
    public void startLogin(View view) {
        if (username.getText().toString().length() != 9 || password.getText().length() < 6){
            Toast.makeText(this,"账号密码填写错误",Toast.LENGTH_SHORT).show();
            return;
        }
        String url = HOST + "account/login";
        Map<String,String> map = new HashMap<>();
        map.put("username",username.getText().toString());
        map.put("password",password.getText().toString());
        map.put("type", String.valueOf(loginType));
        getNetRequest(url,map);
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