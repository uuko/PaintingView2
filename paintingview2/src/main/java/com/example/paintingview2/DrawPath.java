package com.example.paintingview2;


import android.graphics.Path;

import java.util.LinkedList;
import java.util.List;

public class DrawPath extends Path {
    private List<MovePath> pathLinkedList=new LinkedList<>();

    @Override
    public void moveTo(float x, float y) {
        pathLinkedList.add(new MovePath(x,y));
        super.moveTo(x, y);
    }

    @Override
    public void reset() {
        pathLinkedList.clear();
        super.reset();
    }

    @Override
    public void lineTo(float x, float y) {
        pathLinkedList.add(new MovePath(x,y));
        super.lineTo(x, y);
    }

    @Override
    public void quadTo(float x1, float y1, float x2, float y2) {
        pathLinkedList.add(new MovePath(x1,y1,x2,y2));
        super.quadTo(x1, y1, x2, y2);
    }
}
