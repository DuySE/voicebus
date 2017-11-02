package com.example.fpt.busstation.data.db;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class WalkInstructionDto {
    private int type;
    private int beginType; /**1 when standing current pos, 2 when standing at a station */
    private int endType; /**2 when need to go a station, 2 when need walk to finish point*/
    private CoordDto beginCoord;
    private CoordDto endCoord;
    private double duration;
    private double distance;
    private int fromBus; /** used in show instruction if user walk to current pos to first station*/
    private int toBus;/** used in show instruction if user need to leave from current bus to take another bus at same station*/


    public WalkInstructionDto(int type, int beginType, int endType, CoordDto beginCoord, CoordDto endCoord, double duration, double distance) {
        this.type = type;
        this.beginType = beginType;
        this.endType = endType;
        this.beginCoord = beginCoord;
        this.endCoord = endCoord;
        this.duration = duration;
        this.distance = distance;
    }

    public WalkInstructionDto(int type, int beginType, int endType, CoordDto beginCoord, CoordDto endCoord, double duration, double distance, int fromBus, int toBus) {
        this.type = type;
        this.beginType = beginType;
        this.endType = endType;
        this.beginCoord = beginCoord;
        this.endCoord = endCoord;
        this.duration = duration;
        this.distance = distance;
        this.fromBus = fromBus;
        this.toBus = toBus;
    }

    public WalkInstructionDto(int type, int beginType, int endType, CoordDto beginCoord, CoordDto endCoord, double duration, double distance, int toBus) {
        this.type = type;
        this.beginType = beginType;
        this.endType = endType;
        this.beginCoord = beginCoord;
        this.endCoord = endCoord;
        this.duration = duration;
        this.distance = distance;
        this.toBus = toBus;
    }

    public WalkInstructionDto() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBeginType() {
        return beginType;
    }

    public void setBeginType(int beginType) {
        this.beginType = beginType;
    }

    public int getEndType() {
        return endType;
    }

    public void setEndType(int endType) {
        this.endType = endType;
    }

    public CoordDto getBeginCoord() {
        return beginCoord;
    }

    public void setBeginCoord(CoordDto beginCoord) {
        this.beginCoord = beginCoord;
    }

    public CoordDto getEndCoord() {
        return endCoord;
    }

    public void setEndCoord(CoordDto endCoord) {
        this.endCoord = endCoord;
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

    public int getFromBus() {
        return fromBus;
    }

    public void setFromBus(int fromBus) {
        this.fromBus = fromBus;
    }

    public int getToBus() {
        return toBus;
    }

    public void setToBus(int toBus) {
        this.toBus = toBus;
    }
}
