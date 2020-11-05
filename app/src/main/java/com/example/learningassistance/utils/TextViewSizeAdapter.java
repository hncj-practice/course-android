package com.example.learningassistance.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

public class TextViewSizeAdapter extends androidx.appcompat.widget.AppCompatTextView implements ViewTreeObserver.OnGlobalLayoutListener {
    public TextViewSizeAdapter(Context context) {
        super(context);
    }

    public TextViewSizeAdapter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewSizeAdapter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        int line = getLineCount();
        if (line > 1){
            float textSize = getTextSize();
            textSize--;
            setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }
    }
}
