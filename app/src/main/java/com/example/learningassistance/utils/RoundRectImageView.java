package com.example.learningassistance.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.example.learningassistance.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressLint("AppCompatCustomView")
public class RoundRectImageView extends ImageView {

    private Paint paint;

    public RoundRectImageView(Context context) {
        this(context,null);
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundRectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint  = new Paint();
    }

    /**
     * 绘制圆角矩形图片
     * @author caizhiming
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = getBitmapFromDrawable(drawable);
//            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getRoundBitmapByShader(bitmap,getWidth(),getHeight(), 50,0);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);

        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 把资源图片转换成Bitmap
     * @param drawable
     * 资源图片
     * @return 位图
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //drawable.setBounds(-4, -4, width + 4, height + 4);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getRoundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius, int boarder) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float widthScale = outWidth * 1f / width;
        float heightScale = outHeight * 1f / height;

        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //创建输出的bitmap
        Bitmap desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //创建着色器
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //给着色器配置matrix
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        //创建矩形区域并且预留出border
        RectF rect = new RectF(boarder, boarder, outWidth - boarder, outHeight - boarder);
        //把传入的bitmap绘制到圆角矩形区域内
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (boarder > 0) {
            //绘制boarder
            Paint boarderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            boarderPaint.setColor(Color.GREEN);
            boarderPaint.setStyle(Paint.Style.STROKE);
            boarderPaint.setStrokeWidth(boarder);
            canvas.drawRoundRect(rect, radius, radius, boarderPaint);
        }
        return desBitmap;
    }

    /**
     * 设置图片为圆形
     * @param image 要设为圆形的ImageView组件
     * @param imageId 组件所需图片的id
     * @param imageSize 输出图片的大小
     * @param activity 需要改变圆角图片所在的activity
     */
    public static void setCircle(ImageView image, int imageId, int imageSize, Activity activity){
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), imageId);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,50, 0);
        image.setImageBitmap(outBitmap);
    }

    public static void setCircle(ImageView image, int imageId, int imageSize, Fragment fragment){
        Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), imageId);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,50, 0);
        image.setImageBitmap(outBitmap);
    }

    public static void setUserAvatar(ImageView image, String uri, int imageSize){
        Bitmap bitmap = BitmapFactory.decodeFile(uri);
        if (bitmap == null){
            bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.icon_default_avatar);
        }
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,50, 0);
        image.setImageBitmap(outBitmap);
    }

    /**
     * 设置图片的圆角
     * @param image 要设为圆角的ImageView组件
     * @param imageId 组件所需图片的id
     * @param imageSize 输出图片的大小
     * @param radius 圆角的值
     * @param activity 需要改变圆角图片所在的activity
     */
    public static void setRadius(ImageView image, int imageId, int imageSize,int radius ,Activity activity){
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), imageId);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,radius, 0);
        image.setImageBitmap(outBitmap);
    }

    public static void setRadius(ImageView image, int imageId, int imageSize,int radius , Fragment activity){
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), imageId);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,radius, 0);
        image.setImageBitmap(outBitmap);
    }

    public static void setRadius(ImageView image, String imgUrl, int imageSize,int radius) throws IOException {
        Bitmap bitmap = Picasso.get().load(imgUrl).get();
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, imageSize,imageSize,radius, 0);
        image.setImageBitmap(outBitmap);
    }

}