package com.example.fpt.busstation.data.db;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class RouteDto {
    private int type;
    private double lat;
    private double lng;
    private String name; // ten tram neu' co'

    public RouteDto(int type, double lat, double lng, String name) {
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    public RouteDto() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
