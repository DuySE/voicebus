package com.example.fpt.busstation.data.db;

/**
 * Created by Vi Nguyen on 22/10/2017.
 */

/**
 * CoordDto
 * used for access and display instruction which type = 1
 */
public class CoordDto {
    private double Lat;
    private double Lng;
    private String name; /**current pos || station name || finish position*/

    public CoordDto(double lat, double lng, String name) {
        Lat = lat;
        Lng = lng;
        this.name = name;
    }

    public CoordDto() {
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
