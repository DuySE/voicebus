package com.example.fpt.busstation.data.db;

/**
 * Created by cuong on 10/25/2017.
 */

public class IntentDTO {
    private int type;
    private String mess;
    private String busnum;
    private String begin;
    private String end;

    public IntentDTO() {
    }

    public IntentDTO(int type, String mess, String busnum, String begin, String end) {
        this.type = type;
        this.mess = mess;
        this.busnum = busnum;
        this.begin = begin;
        this.end = end;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getBusnum() {
        return busnum;
    }

    public void setBusnum(String busnum) {
        this.busnum = busnum;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
