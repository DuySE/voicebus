package com.example.fpt.busstation.ui.main;

import android.location.Location;

import com.example.fpt.busstation.ui.base.BaseMvpPresenter;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by cuong on 9/13/2017.
 */

public interface MainMvpPresenter<T extends MainMvpView> extends BaseMvpPresenter<T> {
    void startRecordAudio();
    void stopRecordAudio();
    void sendTTSRequest(String text);
    void sendRouteRequest(Double lng, Double lat,String begin, String end,int type);
    void sendStationRequest(Double lng, Double lat, String number, int type);
    void sendDetectRequest(Double lng, Double lat,String text);
    void sendDirectionWalkingRequest(LatLng from, LatLng to);
}
