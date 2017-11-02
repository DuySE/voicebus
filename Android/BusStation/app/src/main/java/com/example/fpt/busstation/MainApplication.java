package com.example.fpt.busstation;

import android.app.Application;
import android.util.Log;

import com.example.fpt.busstation.data.conn.BaseRestClient;

/**
 * Created by cuong on 9/12/2017.
 */

public class MainApplication extends Application {
    public static final String TAG = MainApplication.class
            .getSimpleName();
    private static MainApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"here");
        mInstance = this;
    }
    public static synchronized MainApplication getInstance() {
        return mInstance;
    }
}
