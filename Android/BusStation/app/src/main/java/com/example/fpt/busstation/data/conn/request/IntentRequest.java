package com.example.fpt.busstation.data.conn.request;

import android.util.Log;

import com.example.fpt.busstation.data.conn.ApiContansts;
import com.example.fpt.busstation.data.conn.ApiURL;
import com.example.fpt.busstation.data.conn.RestClient;
import com.example.fpt.busstation.service.OnResponseStringListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuong on 9/27/2017.
 */

public class IntentRequest {
    public static final String TAG = IntentRequest.class.getSimpleName();

    public static void sendGetRequest(String msg,OnResponseStringListener listener) {
        Map<String,String> param= new HashMap<>();
        param.put("type",8+"");
        param.put("msg",msg);
        Log.d(TAG, param.toString());
        Log.d(TAG,ApiURL.getDomain());
        RestClient.getInstance().getRequest(ApiURL.getDomain(),param,listener);
    }
}
