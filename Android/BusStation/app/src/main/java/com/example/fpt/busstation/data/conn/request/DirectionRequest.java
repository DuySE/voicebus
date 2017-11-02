package com.example.fpt.busstation.data.conn.request;

import android.util.Log;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.data.conn.ApiContansts;
import com.example.fpt.busstation.data.conn.RestClient;
import com.example.fpt.busstation.service.OnResponseStringListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuong on 11/1/2017.
 */

public class DirectionRequest {
    public static final String TAG = DirectionRequest.class.getSimpleName();

    public static void sendGetRequest(LatLng from, LatLng to, OnResponseStringListener listener) {
        Map<String,String> param= new HashMap<>();
        param.put("origin",from.latitude+","+from.longitude);
        param.put("destination",to.latitude+","+to.longitude);
        param.put("mode","walking");
        param.put("key", ApiContansts.GOOGLE_API_KEY);
        Log.d(TAG, param.toString());
        RestClient.getInstance().getRequest(ApiContansts.DIRECTION_URL_API,param,listener);
    }
}
