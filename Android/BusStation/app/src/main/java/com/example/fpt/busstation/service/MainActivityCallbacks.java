package com.example.fpt.busstation.service;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by cuong on 10/31/2017.
 */

public interface MainActivityCallbacks {
    void drawRouteCB(List<Object> instruction);
    void moveToMarkerAndShowInfo(int position);
    void showBottomSheetCB();
    void drawWalkingRouteCB(int position);
}
