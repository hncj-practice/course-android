package com.example.learningassistance.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learningassistance.R;

public class Utils {
    public static void setTitle(final Activity view, String titleText){
        TextView title_text = view.findViewById(R.id.title_text);
        title_text.setText(titleText);

        ImageView title_back = view.findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }
}
