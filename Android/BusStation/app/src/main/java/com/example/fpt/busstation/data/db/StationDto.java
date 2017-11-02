package com.example.fpt.busstation.data.db;

import java.util.List;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class StationDto {
    private String stationName;
    private String stationAddress;
    private Double lat;
    private Double lng;
    private List<BusDto> listBus;
    private Double duration;
    private Double distance;
    public StationDto(String stationName, String stationAddress) {
        this.stationName = stationName;
        this.stationAddress = stationAddress;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public StationDto() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<BusDto> getListBus() {
        return listBus;
    }

    public void setListBus(List<BusDto> list) {
        this.listBus = list;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }
}
