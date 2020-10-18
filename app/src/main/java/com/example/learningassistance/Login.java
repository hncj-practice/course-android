package com.example.learningassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.learningassistance.utils.RoundRectImageView.getRoundBitmapByShader;



public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String HOST = "http://123.56.156.212/Interface/";

    private ImageView avatar,login;
    private EditText username,password;
    private Spinner userType;
    private Integer loginType;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            JSONObject json = (JSONObject) msg.obj;
            if (json.getString("code").equals("200")){
                Map<String, String> map = new HashMap<>();
                String data = json.getString("data");
//                JSONObject data = JSON.parseObject(data_array.get(0).toString());
//                map.put("status",data.getString("status"));
//                map.put("total",data.getString("total"));
//                map.put("avatar",data.getString("avatar"));
//                map.put("email",data.getString("email"));
//                map.put("name",data.getString("name"));
//                map.put("tno",data.getString("tno"));
//                map.put("pwd",data.getString("pwd"));
//                map.put("sex",data.getString("sex"));
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
        initComponent();

    }

    /**
     * 加载页面的所有组件
     */
    public void initComponent(){
        avatar = findViewById(R.id.login_avatar);
        setRadius(avatar,R.drawable.a,70);
        login = findViewById(R.id.login_button);
        setRadius(login,R.drawable.icon_login,50);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        username.setText("888888888");
        password.setText("000000");

        userType = findViewById(R.id.login_type);

        userType.setOnItemSelectedListener(this);
    }

    /**
     * 设置登陆所需各种图片的圆角
     * @param image 要设为圆形的ImageView组件
     * @param imageId 组件所需图片的id
     * @param imageSize 输出图片的大小
     */
    public void setRadius(ImageView image,int imageId,int imageSize){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,50, 0);
        image.setImageBitmap(outBitmap);
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