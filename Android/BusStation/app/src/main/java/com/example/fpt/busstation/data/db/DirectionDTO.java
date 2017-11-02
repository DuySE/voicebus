package com.example.fpt.busstation.data.db;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by cuong on 11/1/2017.
 */

public class DirectionDTO {
    private DistanceDTO distance;
    private DurationDTO duration;
    private String endAddress;
    private LatLng endLocation;
    private String startAddress;
    private LatLng startLocation;

    private List<LatLng> points;

    public DirectionDTO() {
    }

    public DirectionDTO(DistanceDTO distance, DurationDTO duration, String endAddress, LatLng endLocation, String startAddress, LatLng startLocation, List<LatLng> points) {
        this.distance = distance;
        this.duration = duration;
        this.endAddress = endAddress;
        this.endLocation = endLocation;
        this.startAddress = startAddress;
        this.startLocation = startLocation;
        this.points = points;
    }

    public DistanceDTO getDistance() {
        return distance;
    }

    public void setDistance(DistanceDTO distance) {
        this.distance = distance;
    }

    public DurationDTO getDuration() {
        return duration;
    }

    public void setDuration(DurationDTO duration) {
        this.duration = duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}
