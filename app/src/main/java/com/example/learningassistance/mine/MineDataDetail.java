package com.example.learningassistance.mine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.MineDetailAdapter;
import com.example.learningassistance.entity.MineDetail;
import com.example.learningassistance.utils.AccessUtil;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MineDataDetail extends AppCompatActivity implements View.OnClickListener {
    private static final int TAKE_CAMERA = 101;
    private static final int SHOW_ALBUM = 102;

    private ImageView image;
    private Uri imageUri;
    private Bitmap bitmap;
    private String netUrl;
    private Dialog dialog1;

    private JSONObject jsonObject;
    private String localPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_data_detail);

        Utils.setTitle(this,"我的信息");

        imageUri = getUri();
        dialog1 = setDialog();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        jsonObject = JSON.parseObject(data);
        localPath = jsonObject.getString("localPath");
        List<MineDetail> list = initDetail();
        RecyclerView recyclerView = findViewById(R.id.recycler_mine_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MineDetailAdapter adapter = new MineDetailAdapter(list);
        recyclerView.setAdapter(adapter);

        image = findViewById(R.id.mine_detail_image);
        RoundRectImageView.setUserAvatar(image,localPath,50);
        image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mine_detail_image:
                //弹出对话框
                dialog1.show();
                break;
            case R.id.btn_show_avatar:
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_image, null, false);
                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                ImageView img = view.findViewById(R.id.large_image);
                img.setImageBitmap(BitmapFactory.decodeFile(localPath));
                dialog.setView(view);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                img.setOnClickListener(v1 -> dialog.dismiss());
                dialog1.dismiss();
                break;
            case R.id.btn_choose_img:
                //选择照片按钮
                Toast.makeText(this, "请选择照片", Toast.LENGTH_SHORT).show();
                intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, SHOW_ALBUM);
                dialog1.dismiss();
                break;
            case R.id.btn_open_camera:
                //拍照按钮
                Toast.makeText(this, "即将打开相机", Toast.LENGTH_SHORT).show();
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_CAMERA);
                dialog1.dismiss();
                break;
            case R.id.btn_cancel:
                //取消按钮
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    Crop.of(imageUri, imageUri).withMaxSize(600,600).asSquare().start(this);
                }
                break;
            case SHOW_ALBUM:
                if (resultCode == RESULT_OK) {
                    Crop.of(data.getData(), imageUri).withMaxSize(600, 600).asSquare().start(this);
                }
                break;
            case Crop.REQUEST_CROP:
                if (data != null){
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        File tempFile = createTempFile(bitmap);
                        RoundRectImageView.setUserAvatar(image,localPath,50);
                        sendImage(tempFile,this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public File createTempFile(Bitmap bitmap) throws IOException {
//        String path = getExternalFilesDir(null).getAbsolutePath() + ".png";
        File file = new File(localPath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return file;
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
                netUrl = jsonObject.getString("url");
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                /**
                 * 上传完图片后重新更新组件
                 */
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(context, "Upload Fail!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Dialog setDialog() {
        Dialog dialog = new Dialog(this,R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null, false);
        view.findViewById(R.id.btn_choose_img).setOnClickListener(this);
        view.findViewById(R.id.btn_open_camera).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_show_avatar).setOnClickListener(this);
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

    public Uri getUri(){
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try {
            if (outputImage.exists()){
                outputImage.delete();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            imageUri = FileProvider.getUriForFile(this,"com.example.learningassistance.FileProvider",outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        return imageUri;
    }

    private List<MineDetail> initDetail(){
        List<MineDetail> list = new ArrayList<>();
        list.add(new MineDetail(R.drawable.icon_user_name,"姓名",jsonObject.getString("name")));
        if (jsonObject.getString("status").equals("1")){
            list.add(new MineDetail(R.drawable.icon_user_id,"学号",jsonObject.getString("sno")));
            list.add(new MineDetail(R.drawable.icon_user_class,"班级",jsonObject.getString("cla")));
        } else if (jsonObject.getString("status").equals("2")){
            list.add(new MineDetail(R.drawable.icon_user_class,"工号",jsonObject.getString("tno")));
        }
        list.add(new MineDetail(R.drawable.icon_user_sex,"性别",jsonObject.getString("sex").equals("m") ? "男" : "女"));
        list.add(new MineDetail(R.drawable.icon_user_email,"电子邮箱",jsonObject.getString("email")));
        return list;
    }
}