package com.example.learningassistance.utils;

import android.app.Application;
import android.content.Context;

public class MyContext extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
