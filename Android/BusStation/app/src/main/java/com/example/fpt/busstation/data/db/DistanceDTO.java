package com.example.fpt.busstation.data.db;

/**
 * Created by cuong on 11/1/2017.
 */

public class DistanceDTO {
    private String text;
    private int value;

    public DistanceDTO(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public DistanceDTO( ) {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
