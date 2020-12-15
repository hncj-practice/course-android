package com.example.learningassistance.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

import com.squareup.picasso.Transformation;

import static android.graphics.Bitmap.createBitmap;

public class MyTransForm {

    public static class RangleTransForm implements Transformation {
        private int rangle;
        private int outSize;

        public RangleTransForm() {
            this.rangle = 10;
            this.outSize = 50;
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
            Bitmap desBitmap = createBitmap(outSize, outSize, Bitmap.Config.ARGB_8888);
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

    public static class SquareTransForm implements Transformation{
        private float size;

        public SquareTransForm(float size) {
            this.size = size;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            float width = source.getWidth();
            float height = source.getHeight();
            float scaleWidth,scaleHeight,x,y;
            Bitmap nBitmap;
            Bitmap cBitmap = null;
            Matrix matrix = new Matrix();

            if ((width/height)<= 1){
                scaleWidth = size / width;
                scaleHeight = scaleWidth;
                y = ((height*scaleHeight - size) / 2)/scaleHeight;
                x = 0;
            }else {
                scaleWidth = size / height;
                scaleHeight = scaleWidth;
                x = ((width*scaleWidth -size ) / 2)/scaleWidth;
                y = 0;
            }
            matrix.postScale(scaleWidth / 1f, scaleHeight / 1f);
            try {
                if (width - x > 0 && height - y > 0&&source!=null)
                    cBitmap = createBitmap(source, (int) Math.abs(x), (int) Math.abs(y), (int) Math.abs(width - x),
                            (int) Math.abs(height - y), matrix, false);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("SOURCE-->","x:"+source.getWidth()+" y:"+source.getHeight());
                return source;
            }

            width = cBitmap.getWidth();
            height = cBitmap.getHeight();
            if (!(width == height)){
                float nx,ny;
                if (width > height){
                    size = height;
                    nx = (width - size) / 2;
                    ny = 0;
                } else {
                    size = width;
                    ny = (height - size) / 2;
                    nx = 0;
                }

                nBitmap = createBitmap(cBitmap,(int) Math.abs(nx),(int) Math.abs(ny),(int) Math.abs(size),(int) Math.abs(size));
            } else {
                Log.d("CENTER-->","x:"+cBitmap.getWidth()+" y:"+cBitmap.getHeight());
                return cBitmap;
            }
            source.recycle();
            Log.d("NEW-->","x:"+nBitmap.getWidth()+" y:"+nBitmap.getHeight());
            return nBitmap;
        }

        @Override
        public String key() {
            return "null";
        }
    }
}
