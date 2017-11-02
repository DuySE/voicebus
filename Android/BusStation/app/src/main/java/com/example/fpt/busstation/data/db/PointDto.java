package com.example.fpt.busstation.data.db;

/**
 * Created by Vi Nguyen on 31/10/2017.
 */

/**
 * PointDto
 * used for drawRoute()
 */
public class PointDto {
    private Double Lat;
    private Double Lng;

    public PointDto() {
    }

    public PointDto(Double lat, Double lng) {
        Lat = lat;
        Lng = lng;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }
}
