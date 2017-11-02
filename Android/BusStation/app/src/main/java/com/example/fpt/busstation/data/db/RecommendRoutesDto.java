package com.example.fpt.busstation.data.db;

import android.util.Log;

import java.util.List;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class RecommendRoutesDto {
    private String recommendRouteId;
    private double distance;
    private double duration;
    private int totalBus;
    private String listBusNo;
    private List<Object> instruction;

    public RecommendRoutesDto(String recommendRouteId, double distance, double duration, int totalBus, String listBusNo, List<Object> instruction) {
        this.recommendRouteId = recommendRouteId;
        this.distance = distance;
        this.duration = duration;
        this.totalBus = totalBus;
        this.listBusNo = listBusNo;
        this.instruction = instruction;
    }

    public RecommendRoutesDto(String recommendRouteId, double distance, double duration, int totalBus, String listBusNo) {
        this.recommendRouteId = recommendRouteId;
        this.distance = distance;
        this.duration = duration;
        this.totalBus = totalBus;
        this.listBusNo = listBusNo;
    }

    public RecommendRoutesDto() {
    }

    public String generateRouteName() {
        String generatedName = "";
        StringBuilder sb = new StringBuilder();
        String[] busNumArr = listBusNo.split(",");
        if (!listBusNo.equals("")) {
            sb.append("Tuyến đường ");
            for (String number : busNumArr) {
                sb.append(number);
                sb.append("-");
            }
           generatedName = sb.substring(0, sb.length() - 1);
        }
        Log.d("??? generate name: ", generatedName);
        return generatedName;
    }

    public String getRecommendRouteId() {
        return recommendRouteId;
    }

    public void setRecommendRouteId(String recommendRouteId) {
        this.recommendRouteId = recommendRouteId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getTotalBus() {
        return totalBus;
    }

    public void setTotalBus(int totalBus) {
        this.totalBus = totalBus;
    }

    public String getListBusNo() {
        return listBusNo;
    }

    public void setListBusNo(String listBusNo) {
        this.listBusNo = listBusNo;
    }

    public List<Object> getInstruction() {
        return instruction;
    }

    public void setInstruction(List<Object> instruction) {
        this.instruction = instruction;
    }

}