package com.dbh4ck.talkinchatbot;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class MainApp extends Application {

    private static MainApp mainApp;
    private Handler mHandler;

    public static MainApp getMainApp() {
        return mainApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainApp = this;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void runOnUiThread(Runnable runnable) {
        if(mHandler == null){
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(runnable);
    }

}
