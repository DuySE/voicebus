package com.example.fpt.busstation.data.conn.response;

import android.util.Log;

import com.example.fpt.busstation.data.conn.ApiContansts;
import com.example.fpt.busstation.data.db.BusRouteInstructionDto;
import com.example.fpt.busstation.data.db.BusTransferInstructionDto;
import com.example.fpt.busstation.data.db.CoordDto;
import com.example.fpt.busstation.data.db.PointDto;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.data.db.WalkInstructionDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Modified by Vi Nguyen on 10/31/2017.
 */

public class RouteResponse {
    public static final String TAG = RouteResponse.class.getSimpleName();

    private static List<RecommendRoutesDto> recommendRoutesDtoList;

    public static List<RecommendRoutesDto> convertData(String data) {
        recommendRoutesDtoList = new ArrayList<>();
        if (!data.equals("[]")) {


            JSONArray recommendRoutes = null;
            try {
                recommendRoutes = new JSONArray(data);
                if (recommendRoutes.length() > 0) {
                    for (int i = 0; i < recommendRoutes.length(); i++) {
                        JSONObject recommendRoute = recommendRoutes.getJSONObject(i);
                        RecommendRoutesDto recommendRoutesDto = new RecommendRoutesDto();
                        recommendRoutesDto.setDuration(recommendRoute.getDouble(ApiContansts.KEY_DURATION));
                        recommendRoutesDto.setListBusNo(recommendRoute.getString(ApiContansts.KEY_LISTBUS));
                        String listBusNo = recommendRoute.getString(ApiContansts.KEY_LISTBUS);
                        recommendRoutesDto.setTotalBus(recommendRoute.getInt(ApiContansts.KEY_TOTALBUS));
                        recommendRoutesDto.setListBusNo(listBusNo);
                        Log.d("??? total bus: ", "" + recommendRoute.getInt(ApiContansts.KEY_TOTALBUS));
                        recommendRoutesDto.setRecommendRouteId(recommendRoutesDto.generateRouteName());

                        List<Object> listInstruction = new ArrayList<>();
                        JSONArray arrayInstructions = recommendRoute.getJSONArray(ApiContansts.KEY_INSTRUCTION);
                        if (arrayInstructions.length() > 0) {
                            int count = 0;
                            String[] listBus = listBusNo.split(",");
                            for (int j = 0; j < arrayInstructions.length(); j++) {
                                JSONObject jsonInstruction = arrayInstructions.getJSONObject(j);
                                int typeIntruction = jsonInstruction.getInt(ApiContansts.KEY_TYPE);
                                switch (typeIntruction) {
                                    case 1:
                                        WalkInstructionDto walk = new WalkInstructionDto();
                                        walk.setType(typeIntruction);
                                        walk.setBeginType(jsonInstruction.getInt(ApiContansts.KEY_BEGINTYPE));
                                        walk.setEndType(jsonInstruction.getInt(ApiContansts.KEY_ENDTYPE));
                                        walk.setDuration(jsonInstruction.getDouble(ApiContansts.KEY_DURATION));
                                        walk.setDistance(jsonInstruction.getDouble(ApiContansts.KEY_DISTANCE));
                                        if (count < listBus.length)
                                            walk.setToBus(Integer.parseInt(listBus[count].trim()));
                                        count++;
                                        CoordDto bCoord = new CoordDto();
                                        JSONObject beginCoord = jsonInstruction.getJSONObject(ApiContansts.KEY_BEGINCOORD);
                                        bCoord.setLat(beginCoord.getDouble(ApiContansts.KEY_LAT));
                                        bCoord.setLng(beginCoord.getDouble(ApiContansts.KEY_LNG));
                                        bCoord.setName(beginCoord.getString(ApiContansts.KEY_NAME));
                                        walk.setBeginCoord(bCoord);
                                        CoordDto eCoord = new CoordDto();
                                        JSONObject endCoord = jsonInstruction.getJSONObject(ApiContansts.KEY_ENDCOORD);
                                        eCoord.setLat(endCoord.getDouble(ApiContansts.KEY_LAT));
                                        eCoord.setLng(endCoord.getDouble(ApiContansts.KEY_LNG));
                                        eCoord.setName(endCoord.getString(ApiContansts.KEY_NAME));
                                        walk.setEndCoord(eCoord);
                                        listInstruction.add(walk);
                                        break;
                                    case 3:
                                        BusTransferInstructionDto change = new BusTransferInstructionDto();
                                        change.setType(typeIntruction);
                                        if (count < listBus.length) {
                                            change.setToBus(Integer.parseInt(listBus[count].trim()));
                                            change.setFromBus(Integer.parseInt(listBus[count - 1].trim()));
                                        }
                                        count++;
                                        CoordDto changeCoord = new CoordDto();
                                        JSONObject cCoord = jsonInstruction.getJSONObject(ApiContansts.KEY_CHANGECOORD);
                                        changeCoord.setLat(cCoord.getDouble(ApiContansts.KEY_LAT));
                                        changeCoord.setLng(cCoord.getDouble(ApiContansts.KEY_LNG));
                                        changeCoord.setName(cCoord.getString(ApiContansts.KEY_NAME));
                                        change.setChangeCoord(changeCoord);
                                        listInstruction.add(change);
                                        break;
                                    case 2:
                                        List<CoordDto> listStations = new ArrayList<>();
                                        JSONArray arrayStation = jsonInstruction.getJSONArray(ApiContansts.KEY_STATIONS);
                                        if (arrayStation.length() > 0) {
                                            for (int k = 0; k < arrayStation.length(); k++) {
                                                JSONObject jsonRoute = arrayStation.getJSONObject(k);
                                                CoordDto coordDto = new CoordDto();
                                                coordDto.setLat(jsonRoute.getDouble(ApiContansts.KEY_LAT));
                                                coordDto.setLng(jsonRoute.getDouble(ApiContansts.KEY_LNG));
                                                coordDto.setName(jsonRoute.getString(ApiContansts.KEY_NAME));

                                                listStations.add(coordDto);
                                            }
                                        }
                                        List<PointDto> listPointDtos = new ArrayList<>();
                                        JSONArray arrayPath = jsonInstruction.getJSONArray(ApiContansts.KEY_PATH);
                                        if (arrayPath.length() > 0) {
                                            for (int l = 0; l < arrayPath.length(); l++) {
                                                JSONObject jsonPath = arrayPath.getJSONObject(l);
                                                PointDto pointDto = new PointDto();
                                                pointDto.setLat(jsonPath.getDouble(ApiContansts.KEY_LAT));
                                                pointDto.setLng(jsonPath.getDouble(ApiContansts.KEY_LNG));
                                                listPointDtos.add(pointDto);
                                            }
                                        }
                                        BusRouteInstructionDto bus = new BusRouteInstructionDto();
                                        bus.setType(typeIntruction);
                                        bus.setColor(jsonInstruction.getString(ApiContansts.KEY_COLOR));
                                        bus.setBusNum(jsonInstruction.getInt(ApiContansts.KEY_BUSNUM));
                                        bus.setDistance(jsonInstruction.getDouble(ApiContansts.KEY_DISTANCE));
                                        bus.setDuration(jsonInstruction.getDouble(ApiContansts.KEY_DURATION));
                                        listInstruction.add(bus);
                                        bus.setStations(listStations);
                                        bus.setPath(listPointDtos);
                                        break;
                                }
                            }
                        }
                        recommendRoutesDto.setInstruction(listInstruction);
                        recommendRoutesDtoList.add(recommendRoutesDto);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recommendRoutesDtoList;
    }
}
