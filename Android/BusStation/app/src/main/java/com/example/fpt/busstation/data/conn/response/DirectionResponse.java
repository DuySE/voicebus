package com.example.fpt.busstation.data.conn.response;

import com.example.fpt.busstation.data.db.DirectionDTO;
import com.example.fpt.busstation.data.db.DistanceDTO;
import com.example.fpt.busstation.data.db.DurationDTO;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuong on 11/1/2017.
 */

public class DirectionResponse {
    public static final String TAG = DirectionResponse.class.getSimpleName();
    private static List<DirectionDTO> directionDTOs;
    public static List<DirectionDTO> convertData(String data){
        if(data!=null){
            directionDTOs = new ArrayList<>();
            JSONObject jsonData = null;
            try {
                jsonData = new JSONObject(data);
                JSONArray jsonRoutes = jsonData.getJSONArray("routes");
                for (int i = 0; i < jsonRoutes.length(); i++) {
                    JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                    DirectionDTO dto = new DirectionDTO();

                    JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                    JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                    JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                    JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                    JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                    JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                    JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

                    dto.setDistance(new DistanceDTO(jsonDistance.getString("text"), jsonDistance.getInt("value")));
                    dto.setDuration( new DurationDTO(jsonDuration.getString("text"), jsonDuration.getInt("value")));
                    dto.setEndAddress(jsonLeg.getString("end_address"));
                    dto.setStartAddress(jsonLeg.getString("start_address"));
                    dto.setStartLocation(new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng")));
                    dto.setEndLocation(new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng")));
                    dto.setPoints(decodePolyLine(overview_polylineJson.getString("points")));

                    directionDTOs.add(dto);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return directionDTOs;
    }
    private static List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
