package com.example.paintingview2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PPView extends View {
    public Paint paint;
    private float preX, preY;

    private Map<DrawPath,PaintValue> linkedHashMap=new LinkedHashMap<>();
    private Map<DrawPath,PaintValue> lastHashMap=new LinkedHashMap<>();

    private DrawPath drawPath=new DrawPath();
    private PaintValue paintValue=new PaintValue();
    private int defaultEraserColor= Color.WHITE;
    public PPView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10F);
    }
    private Boolean isEraser=false;
    private Boolean picMode=false;
    private Paint mBackgroundPaint;
    public void setBackground(int color,int transparent) {
        int chCl= ColorUtils.setAlphaComponent(color,transparent);
        setBackgroundColor(color);
        invalidate();
    }
    public void setColor(int color,int transparent){
        int chCl= ColorUtils.setAlphaComponent(color,transparent);
        paintValue.setColor(chCl);
        Log.d("coloraaaaaa","cl"+chCl);
        invalidate();
    }
    public void setWidth(int progress){
        Log.d("qqqqq",""+(100F/progress)*10F+"progress"+progress);
        paintValue.setPaintWidth((float) progress);
        Log.d("qqqqQAQ",""+paintValue.getPaintWidth());
        invalidate();
    }
    public void eraserIt(Boolean mode){
        picMode=mode;
        isEraser=!isEraser;
        paintValue.setErase(isEraser);
        invalidate();
    }
    public void back(){
        DrawPath path=getLastKey(linkedHashMap);
        PaintValue paintValue=getLastValue(linkedHashMap);
        Log.d("bacccccccc", "back: "+paintValue.getColor());
        linkedHashMap.remove(path);
        if ((paintValue!=null)&&(path!=null)){
            lastHashMap.put(path,paintValue);
        }
        invalidate();
    }
    public void next(){
        DrawPath ph=getLastKey(lastHashMap);
        PaintValue pv=getLastValue(lastHashMap);
        Log.d("bacccccccc", "next: "+pv.getColor());
        if ((ph!=null)&&(pv!=null)){
            linkedHashMap.put(ph,pv);
            lastHashMap.remove(ph);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        Log.d("onDrawwwwwwwwwwww", "onDraw: ");
        for (Map.Entry<DrawPath,PaintValue> entry : linkedHashMap.entrySet()) {
            Log.d("puttQAQ","color"+entry.getValue().getColor());
            setMyPaint(entry.getValue());
            canvas.drawPath(entry.getKey(),paint);
        }
        setMyPaint(paintValue);
        canvas.drawPath(drawPath, paint);

    }
    public PaintValue getLastValue(Map<DrawPath,PaintValue> ll) {
        PaintValue out = new PaintValue();
        for (PaintValue key : ll.values() ){
            out = key;
        }
        return out;
    }

    public DrawPath getLastKey(Map<DrawPath,PaintValue> ll) {
        DrawPath out = null;
        for (DrawPath key : ll.keySet()) {
            out = key;
        }
        return out;
    }




    private void setMyPaint(PaintValue value) {

        if (value.getErase()){
            if (picMode){
                setWillNotDraw(false);
                setLayerType(LAYER_TYPE_HARDWARE, null);
                //paint.setColor(defaultEraserColor);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                paint.setStrokeWidth(value.getPaintWidth());
            }
            else {
                paint.setColor(defaultEraserColor);
                paint.setStrokeWidth(value.getPaintWidth());
            }

        }

        else {
            paint.setXfermode(null);
            paint.setColor(value.getColor());
            paint.setStrokeWidth(value.getPaintWidth());
        }
    }

    private Long downTime;
    private Float clickx;private Float clicky;

    public Bitmap loadBitmapFromView() {
        int w = getWidth();
        int h = getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c =new Canvas(bmp);
        draw(c);
        return bmp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("eventtttttt", "ACTION_DOWN: ");
                drawPath.reset();
                drawPath.moveTo(x,y);
                clickx=x;
                clicky=y;
                preX = x;
                preY = y;

                downTime=event.getDownTime();

                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("eventtttttt", "ACTION_MOVE: ");
                drawPath.quadTo(preX,preY, (x + preX) / 2, (y + preY) / 2);
                preX = x;
                preY = y;
                if (((event.getEventTime()-downTime)>2000)&&( ((Math.abs(clickx-x)<30) &&(Math.abs(clicky-y)<30)) )){
                    linkedHashMap.clear();
                    lastHashMap.clear();
                    defaultEraserColor=paintValue.getColor();
                    Log.d("coloraaaaaa","defaultEraserColor"+defaultEraserColor);
                    setBackgroundColor(defaultEraserColor);
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.d("eventtttttt", "ACTION_UP: ");
                drawPath.lineTo(preX,preY);
                if (preX==x&&preY==y){
                    drawPath.lineTo(x,y+2);
                    drawPath.lineTo(x+1,y+2);
                    drawPath.lineTo(x+1,y);
                }
                Log.d("llllll","downTime : "+downTime+"e : "+event.getEventTime()+"-- :"+(event.getEventTime()-downTime));
                Log.d("llllll","preX : "+clickx+"x : "+x+"preY :"+clicky+"y : "+y);

                PaintValue pp=new PaintValue();

                Log.d("putt","link"+pp.getColor());
                linkedHashMap.put(drawPath,paintValue);
                drawPath=new DrawPath();
                paintValue=new PaintValue(paintValue.getColor(),paintValue.getPaintWidth(),paintValue.getErase());
                break;
        }
        invalidate();
        return true;
    }
}
