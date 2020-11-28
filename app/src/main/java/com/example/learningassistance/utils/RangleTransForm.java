package com.example.learningassistance.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class RangleTransForm implements Transformation {
    private int rangle;
    private int outSize;

    public RangleTransForm(int rangle, int outSize) {
        this.rangle = rangle;
        this.outSize = outSize;
    }

    @Override
    public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();
        float widthScale = outSize * 1f / width;
        float heightScale = outSize * 1f / height;

        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //创建输出的bitmap
        Bitmap desBitmap = Bitmap.createBitmap(outSize, outSize, Bitmap.Config.ARGB_8888);
        //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //创建着色器
        BitmapShader bitmapShader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //给着色器配置matrix
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        //创建矩形区域并且预留出border
        RectF rect = new RectF(0,0,outSize,outSize);
        //把传入的bitmap绘制到圆角矩形区域内
        canvas.drawRoundRect(rect, rangle, rangle, paint);
        source.recycle();
        return desBitmap;

    }

    @Override
    public String key() {
        return "right";
    }
}
