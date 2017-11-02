package com.example.fpt.busstation.ui.main;

import com.example.fpt.busstation.ui.base.BaseMvpView;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.data.db.StationDto;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by cuong on 9/13/2017.
 */

public interface MainMvpView extends BaseMvpView {
    void placeStation(double lng, double lat, String address, String name, int position);
    void showBottomSheet();
    void hideBottomSheet();
    void showBusAndStation(List<StationDto> list);
    void showRouteInstruction(List<RecommendRoutesDto> list,boolean flag);
    void removeAllMarkerAndPolyline();
    void drawWalkingRoute(List<LatLng> list);
}
