package com.lyhoangvinh.app;

import android.app.Application;

import com.google.gson.Gson;

public class App extends Application {

    private Gson mGSon;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public Gson getGSon() {
        return mGSon;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mGSon = new Gson();
    }
}
