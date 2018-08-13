package com.test.touch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by fuweiwei on 2016/a1/20.
 */
@SuppressLint("AppCompatCustomView")
public class MyButton1 extends Button {
    private static final String TAG = "MyButton";

    public MyButton1(Context context) {
        super(context);
    }

    public MyButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG, "onTouchEvent" + "  event=" + event.toString());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Log.v(TAG, "onTouchEvent;----> ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.v(TAG, "onTouchEvent;----> ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                // Log.v(TAG, "onTouchEvent;----> ACTION_UP");
                break;
            default:
                break;
        }
        boolean flag = super.onTouchEvent(event);
        Log.v(TAG, "flag=" + flag + "onTouchEvent after  super.onTouchEvent(event)");
        return flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG, "dispatchTouchEvent" + "  event=" + ev.toString());
        boolean flag = super.dispatchTouchEvent(ev);
        Log.v(TAG, "flag=" + flag + "dispatchTouchEvent after super.dispatchTouchEvent(ev)");
        return flag;
    }


}
