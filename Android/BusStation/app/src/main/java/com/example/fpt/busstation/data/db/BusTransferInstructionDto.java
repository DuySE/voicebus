package com.example.fpt.busstation.data.db;

/**
 * Created by Vi Nguyen on 01/11/2017.
 */

public class BusTransferInstructionDto {
    private int type;
    private int fromBus;
    private int toBus;
    private CoordDto changeCoord;

    public BusTransferInstructionDto() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public CoordDto getChangeCoord() {
        return changeCoord;
    }

    public void setChangeCoord(CoordDto changeCoord) {
        this.changeCoord = changeCoord;
    }
}
