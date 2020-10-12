package com.example.paintingview2;

public class MovePath {

    public MovePath(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public MovePath(Float x, Float y, Float moveX, Float moveY) {
        this.x = x;
        this.y = y;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    private Float x;
    private Float y;

    private Float moveX;
    private Float moveY;

}
