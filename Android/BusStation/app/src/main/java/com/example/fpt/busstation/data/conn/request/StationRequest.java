package com.example.fpt.busstation.data.conn.request;

import android.util.Log;

import com.example.fpt.busstation.data.conn.ApiContansts;
import com.example.fpt.busstation.data.conn.ApiURL;
import com.example.fpt.busstation.data.conn.RestClient;
import com.example.fpt.busstation.service.OnResponseStringListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuong on 10/24/2017.
 */

public class StationRequest {
    public static final String TAG = RouteRequest.class.getSimpleName();


    public static void sendGetRequest(Double lng, Double lat, String numbus,int type,OnResponseStringListener listener) {
        Map<String,String> param= new HashMap<>();
        param.put(ApiContansts.KEY_LAT2,lat+"");
        param.put(ApiContansts.KEY_LONG,lng+"");
        if(numbus.length()!=0)
            param.put(ApiContansts.KEY_BUSNUM,numbus);
        param.put(ApiContansts.KEY_TYPE,type+"");

        //Cuong
        //"https://api.myjson.com/bins/9x65n"
//        RestClient.getInstance().getRequest("https://api.myjson.com/bins/1d6bjb",param,listener);
        //FIXME - Vi - updated json

        RestClient.getInstance().getRequest(ApiURL.getDomain(),param,listener);
 
    }

}
