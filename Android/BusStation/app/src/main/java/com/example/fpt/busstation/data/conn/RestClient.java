package com.example.fpt.busstation.data.conn;

import android.content.Context;

import com.example.fpt.busstation.MainApplication;

/**
 * Created by cuong on 9/12/2017.
 */

public class RestClient extends BaseRestClient {

    public static BaseRestClient getInstance() {
        if (mInstance == null) {
            mInstance = new RestClient();
        }
        return mInstance;
    }

    @Override
    public Context getContext() {
        return MainApplication.getInstance().getApplicationContext();
    }
}
