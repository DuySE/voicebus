package com.example.fpt.busstation.data.db;

import java.util.List;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

/**
 * BusRouteInstructionDto
 * contain instructions for bus route
 * be converted from response data
 * used for bindIntem in cardview "Bus-route" and draw route on map in MainActivity: drawRoute()
 */
public class BusRouteInstructionDto {
    private int type;
    private List<CoordDto> stations;
    private List<PointDto> path;
    private String color;
    private int busNum;
    private double duration;
    private double distance;

    public BusRouteInstructionDto() {
    }

    public BusRouteInstructionDto(int type, List<CoordDto> stations, List<PointDto> path, String color, int busNum, double duration, double distance) {
        this.type = type;
        this.stations = stations;
        this.path = path;
        this.color = color;
        this.busNum = busNum;
        this.duration = duration;
        this.distance = distance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CoordDto> getStations() {
        return stations;
    }

    public void setStations(List<CoordDto> stations) {
        this.stations = stations;
    }

    public List<PointDto> getPath() {
        return path;
    }

    public void setPath(List<PointDto> path) {
        this.path = path;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBusNum() {
        return busNum;
    }

    public void setBusNum(int busNum) {
        this.busNum = busNum;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

