package com.test.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by yooki on 2017/10/31.
 */

public class MyLinearLayout3 extends LinearLayout {
    private static final String TAG = "MyLinearLayout3";

    public MyLinearLayout3(Context context) {
        super(context);
    }

    public MyLinearLayout3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v(TAG, "onInterceptTouchEvent" + "  event=" + ev.toString());
//        boolean flag = super.onInterceptTouchEvent(ev);
//        Log.v(TAG+getId(), "onInterceptTouchEvent=flag=:" + flag);
//        return flag;
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.v(TAG, "flag=" + flag + "onInterceptTouchEvent" + " after  super.onInterceptTouchEvent(ev)");
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG, "onTouchEvent" + "event=" + event.toString());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Log.v(TAG+getId(), "onTouchEvent;----> ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //  Log.v(TAG+getId(), "onTouchEvent;----> ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                // Log.v(TAG+getId(), "onTouchEvent;----> ACTION_UP");
                break;
            default:
                break;
        }
        // boolean flag = super.onTouchEvent(event);
        // Log.v(TAG+getId(), "onTouchEvent=flag=:" + flag + "  event=" + event.toString());
        //return flag;
        boolean flag = super.onTouchEvent(event);
        Log.v(TAG, "flag=" + flag + "onTouchEvent" + "after super.onTouchEvent(event)");
        return flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG, "dispatchTouchEvent" + "  event=" + ev.toString());
//        boolean flag = super.dispatchTouchEvent(ev);
//        Log.v(TAG+getId(), "dispatchTouchEvent=flag=:" + flag);
//        return flag;
        boolean flag = super.dispatchTouchEvent(ev);
        Log.v(TAG, "flag=" + flag + "dispatchTouchEvent after super.dispatchTouchEvent(ev)");
        return flag;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.v(TAG, "requestDisallowInterceptTouchEvent");
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}


