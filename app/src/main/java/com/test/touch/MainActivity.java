package com.test.touch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * aciton1 所有方法不动  点击lin2
 * dispatchTouchEvent用来寻找哪个view的ontouchevent方法消耗事件。
 * 找到lin2消耗down事件，则up事件传递到lin2的dispatchTouchEvent方法时直接调用ontouch方法消耗up.
 * 但上层的每层传递依然跟之前一样dispatchTouchEvent-onInterceptTouchEvent。并没有up事件直接传递给lin2
 * <p>
 * aciton2 所有方法不动  点击btn1
 * <p>
 * aciton3 所有方法不动  点击btn2
 * <p>
 * <p>
 * 怎么样让最底层的lin3响应事件？
 * 将lin1的dispatchTouchEvent方法return false即可 点击btn2 action4
 * <p>
 * <p>
 * <p>
 * action5 所有方法不动  lin1的onInterceptTouchEvent方法return true
 * <p>
 * action6 所有方法不动 lin1的onInterceptTouchEvent方法return true    dispatchTouchEvent return false
 * <p>
 * action7 所有方法不动 lin1的onInterceptTouchEvent方法return true    dispatchTouchEvent return false   onTouchEvent retrun true
 * <p>
 * <p>
 * action8 所有方法不动 lin1的onInterceptTouchEvent方法return true    dispatchTouchEvent return true   onTouchEvent retrun true
 * <p>
 * <p>
 * <p>
 * 总结：
 * <p>
 * 1.所有view默认设置，遵循点击谁谁响应，如果多层叠加，最上面的响应(这些建立在view必须有处理事件的能力，实现onclick接口等，没有吃事件的能力是不行的)
 * 2.dispatchTouchEvent用来拦截是否向和本层平级的下一层传递  onInterceptTouchEvent用来拦截是否向本层内的子view传递
 * 3.找到消耗事件view前，dispatchTouchEvent方法中会调用本层的onInterceptTouchEvent方法。找到消耗事件view后，
 * dispatchTouchEvent方法中会调用本层的onTouchEvent方法。
 * 4.事件都是从dispatchTouchEvent中进行分发。无论消耗前后。
 */


public class MainActivity extends Activity {
    private MyButton1 mBtn1;
    private MyLinearLayout1 mLin1;

    private MyButton2 mBtn2;
    private MyLinearLayout2 mLin2;
    private MyLinearLayout3 mLin3;
    private static final String TAG = "MyButton1";
    private static final String TAG1 = "MyLinearLayout1";
    private static final String TAG2 = "MainActivity";
    private static final String TAG3 = "MyLinearLayout2";
    private static final String TAG4 = "MyLinearLayout3";
    private static final String TAG5 = "MyButton2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLin1 = (MyLinearLayout1) findViewById(R.id.lin1);
        mLin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG1, "OnClick");
            }
        });
        mBtn1 = (MyButton1) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "OnClick");
            }
        });
        mLin2 = (MyLinearLayout2) findViewById(R.id.lin2);
        mLin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG3, "OnClick");
            }
        });

        mLin3 = (MyLinearLayout3) findViewById(R.id.lin3);
        mLin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG4, "OnClick");
            }
        });

        mBtn2 = (MyButton2) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG5, "OnClick");
            }
        });

       /* mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, "onTouch");
                return false;
            }
        });
        mBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.v(TAG, "OnLong");
                return false;
            }
        });*/
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG2, "onTouchEvent" + "event=" + event.toString());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Log.v(TAG2, "onTouchEvent;----> ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //  Log.v(TAG2, "onTouchEvent;----> ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                // Log.v(TAG2, "onTouchEvent;----> ACTION_UP");
                break;
            default:
                break;
        }
        // boolean flag = super.onTouchEvent(event);
        // Log.v(TAG2, "onTouchEvent=flag=:" + flag + "  event=" + event.toString());
        //return flag;
        boolean flag = super.onTouchEvent(event);
        Log.v(TAG2, "onTouchEvent" + "flag=" + flag + "after super.onTouchEvent(event)");
        return flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG2, "dispatchTouchEvent" + "  event=" + ev.toString());
//        boolean flag = super.dispatchTouchEvent(ev);
//        Log.v(TAG2, "dispatchTouchEvent=flag=:" + flag);
//        return flag;
        boolean flag = super.dispatchTouchEvent(ev);
        Log.v(TAG2, "flag=" + flag + "dispatchTouchEvent after super.dispatchTouchEvent(ev)");
        return flag;
    }


}
