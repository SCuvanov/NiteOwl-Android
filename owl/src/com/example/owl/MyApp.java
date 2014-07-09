package com.example.owl;

import android.app.Application;
import android.content.Context;

import com.example.owl.utils.Utils;


public class MyApp extends Application {

    private static MyApp instance;

    public MyApp() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.loadFonts();
    }

    public static MyApp getApp() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onTerminate() {

        super.onTerminate();
    }

}
