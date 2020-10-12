package com.example.paintingview2;

import android.graphics.Color;

public class PaintValue {
    public PaintValue() {

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Float getPaintWidth() {
        return paintWidth;
    }

    public void setPaintWidth(Float paintWidth) {
        this.paintWidth = paintWidth;
    }

    public Boolean getErase() {
        return isErase;
    }

    public void setErase(Boolean erase) {
        isErase = erase;
    }

    private int color= Color.BLACK;
    private Float paintWidth=10f;
    private Boolean isErase=false;

    public PaintValue(int color, Float paintWidth, Boolean isErase) {
        this.color = color;
        this.paintWidth = paintWidth;
        this.isErase = isErase;
    }
}
