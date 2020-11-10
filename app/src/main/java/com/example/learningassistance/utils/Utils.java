package com.example.learningassistance.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;


public class Utils {

    public static void setTitle(final Activity view, final String titleText){
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

    public static void setRecycler(View view, int resourceId, RecyclerView.Adapter adapter){
        RecyclerView recyclerView = view.findViewById(resourceId);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    public static void setRecycler(Activity view, int resourceId, RecyclerView.Adapter adapter){
        RecyclerView recyclerView = view.findViewById(resourceId);
        LinearLayoutManager manager = new LinearLayoutManager(view.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
