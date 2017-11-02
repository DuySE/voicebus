package com.example.fpt.busstation.data.conn.response;

import android.util.Log;

import com.example.fpt.busstation.data.conn.ApiContansts;
import com.example.fpt.busstation.data.db.BusDto;
import com.example.fpt.busstation.data.db.StationDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuong on 10/24/2017.
 */

public class StationResponse {
    public static final String TAG = RouteResponse.class.getSimpleName();

    private static List<StationDto> stationDtoList;

    public static List<StationDto> convertData(String datas) {
        stationDtoList = new ArrayList<>();
        if(!datas.equals("[]")){

            JSONArray jsonArrayStation = null;
            try{
                jsonArrayStation = new JSONArray(datas);
                if(jsonArrayStation.length()>0){
                    for(int i = 0;i<jsonArrayStation.length();i++){
                        JSONObject jsonObjectStation = jsonArrayStation.getJSONObject(i);
                        StationDto stationDto = new StationDto();
                        stationDto.setLat(jsonObjectStation.getDouble(ApiContansts.KEY_LAT));
                        stationDto.setLng(jsonObjectStation.getDouble(ApiContansts.KEY_LNG));
                        stationDto.setStationAddress(jsonObjectStation.getString(ApiContansts.KEY_ADDRESS));
                        stationDto.setDistance(jsonObjectStation.getDouble(ApiContansts.KEY_DISTANCE));
                        stationDto.setDuration(jsonObjectStation.getDouble(ApiContansts.KEY_DURATION));
                        stationDto.setStationName(jsonObjectStation.getString(ApiContansts.KEY_NAME));
                        List<BusDto> busDtoList = new ArrayList<>();
                        JSONArray jsonArrayBus = jsonObjectStation.getJSONArray(ApiContansts.KEY_ROUTES);
                        if(jsonArrayBus.length()>0){
                            for(int j = 0;j<jsonArrayBus.length();j++){
                                JSONObject jsonObjectBus = jsonArrayBus.getJSONObject(j);
                                BusDto busDto = new BusDto();
                                busDto.setBusNumber("Tuyến số "+jsonObjectBus.getString(ApiContansts.KEY_BUSNUM));
                                busDto.setBusRoute(jsonObjectBus.getString(ApiContansts.KEY_ROUTENAME));
                                busDto.setDirection("Lượt đi");
                                busDtoList.add(busDto);
                            }
                        }
                        stationDto.setListBus(busDtoList);
                        stationDtoList.add(stationDto);

                    }
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        return stationDtoList;
    }
}
