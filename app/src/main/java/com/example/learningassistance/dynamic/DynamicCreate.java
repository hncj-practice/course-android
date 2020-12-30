package com.example.learningassistance.dynamic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class DynamicCreate extends AppCompatActivity {
    @BindView(R.id.dynamic_create_avatar)
    ImageView avatar;
    @BindView(R.id.dynamic_create_name)
    TextView name;
    @BindView(R.id.dynamic_create_content)
    TextView content;
    @BindView(R.id.dynamic_create_complete)
    TextView complete;
    @BindView(R.id.dynamic_create_grid)
    GridLayout gridLayout;

    private int size;
    private int num = 0;
    private String path;
    private TextView textView;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_create);
        ButterKnife.bind(this);

        Utils.setTitle(this,"写问题");

        SharedPreferences preferences = getSharedPreferences("loginHistory",MODE_PRIVATE);
        path = preferences.getString("localPath","QAQ");
        String userName = preferences.getString("currentUserName","HAHA");
        RoundRectImageView.setUserAvatar(avatar,path,40);
        name.setText(userName);


        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        size = (point.x - 60) / 3 - 20;

        textView = createTextView();
        gridLayout.addView(textView);
        textView.setOnClickListener(v -> {
            Toast.makeText(this, "请选择照片", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 1);
        });

        complete.setOnClickListener(v -> {
            if (list.size() == num){
                /**
                 * 通过提供的接口发送一个添加动态的请求给服务器
                 */
                finish();
            } else {
                Toast.makeText(this, "图片尚未完成上传", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = new ImageView(gridLayout.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        GridLayout.LayoutParams gl = new GridLayout.LayoutParams(layoutParams);
        gl.rightMargin = 10;
        gl.leftMargin = 10;
        gl.bottomMargin = 10;
        imageView.setLayoutParams(gl);

        if (requestCode == 1 && resultCode == RESULT_OK){
            Picasso.get().load(data.getData()).transform(new MyTransForm.SquareTransForm(size)).into(imageView);
            gridLayout.addView(imageView);
            num = num + 1;
            /*try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                sendImage(createTempFile(bitmap),this);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    public void sendImage(File file, final Context context) throws FileNotFoundException {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("file", file);
        client.post("http://192.168.1.100:8080/spring_mvc2_war/doUpload", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                JSONObject jsonObject = JSON.parseObject(s);
                String netUrl = jsonObject.getString("url");
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                /**
                 * 上传完图片后重新更新组件
                 */
                list.add(netUrl);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(context, "Upload Fail!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public File createTempFile(Bitmap bitmap) throws IOException {
//        String path = getExternalFilesDir(null).getAbsolutePath() + ".png";
        File file = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return file;
    }

    public TextView createTextView(){
        TextView textView = new TextView(gridLayout.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        GridLayout.LayoutParams gl = new GridLayout.LayoutParams(layoutParams);
        gl.rightMargin = 10;
        gl.leftMargin = 10;
        gl.bottomMargin = 10;
        textView.setLayoutParams(gl);
        textView.setBackgroundColor(getResources().getColor(R.color.gray));
        textView.setText("+");
        textView.setTextSize(size / 6);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        return textView;
    }
}