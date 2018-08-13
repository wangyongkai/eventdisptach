package com.test.touch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * ************************************************************************************************************
 * 案例：
 * l3 l2 的onTouchEvent方法均返回false 表示不响应该事件  那么点击l2 到底谁来影响？
 * 首先点击的点落在l2内，能够响应的有activity l3 l1 l2三个view.
 * 如果l1也返回false 那只能返回给activity去响应了
 * <p>
 * 08-13 15:29:46.412 30810-30810/com.test.touch V/MainActivity: dispatchTouchEvent  event=ACTION_DOWN
 * ============================================================================                                                     *
 * 08-13 15:29:46.412 30810-30810/com.test.touch V/MyLinearLayout1: dispatchTouchEvent  event=ACTION_DOWN                             *
 * onInterceptTouchEvent  event=ACTION_DOWN                                                                                           *
 * flag=false   onInterceptTouchEvent after  super.onInterceptTouchEvent(ev)
 * ============================================================================
 * 08-13 15:29:46.412 30810-30810/com.test.touch V/MyLinearLayout2: dispatchTouchEvent  event=ACTION_DOWN
 * onInterceptTouchEvent  event=ACTION_DOWN
 * flag=falseonInterceptTouchEvent after  super.onInterceptTouchEvent(ev)
 * onTouchEventevent=ACTION_DOWN
 * flag=true  onTouchEventafter super.onTouchEvent(event)
 * flag=false  dispatchTouchEvent after super.dispatchTouchEvent(ev)
 * ============================================================================
 * 08-13 15:29:46.412 30810-30810/com.test.touch V/MyLinearLayout1: onTouchEventevent=ACTION_DOWN
 * flag=true  onTouchEventafter super.onTouchEvent(event)
 * flag=true  dispatchTouchEvent after super.dispatchTouchEvent(ev)
 * ============================================================================
 * 08-13 15:29:46.412 30810-30810/com.test.touch V/MainActivity: flag=true  dispatchTouchEvent after super.dispatchTouchEvent(event)
 * ============================================================================
 * 08-13 15:29:46.531 30810-30810/com.test.touch V/MainActivity: dispatchTouchEvent  event=ACTION_UP
 * <p>
 * 08-13 15:29:46.531 30810-30810/com.test.touch V/MyLinearLayout1: dispatchTouchEvent  event=ACTION_UP
 * onTouchEventevent=ACTION_UP
 * flag=true  onTouchEventafter super.onTouchEvent(event)
 * 08-13 15:29:46.532 30810-30810/com.test.touch V/MyLinearLayout1: flag=true  dispatchTouchEvent after super.dispatchTouchEvent(ev)
 * <p>
 * 08-13 15:29:46.532 30810-30810/com.test.touch V/MainActivity: flag=true  dispatchTouchEvent after super.dispatchTouchEvent(event)
 * 08-13 15:29:46.532 30810-30810/com.test.touch V/MyLinearLayout1: OnClick
 * <p>
 * *************************************************************************************************************
 * <p>
 * <p>
 * aciton1 所有方法不动  点击lin2
 * dispatchTouchEvent用来寻找哪个view的ontouchevent方法消耗事件。
 * 找到lin2消耗down事件，则up事件传递到lin2的dispatchTouchEvent方法时直接调用ontouch方法消耗up.
 * 但上层的每层传递依然跟之前一样dispatchTouchEvent-onInterceptTouchEvent。并没有up事件直接传递给lin2
 * **************************************************************************************************************
 * <p>
 * aciton2 所有方法不动  点击btn1
 * **************************************************************************************************************
 * <p>
 * aciton3 所有方法不动  点击btn2
 * **************************************************************************************************************
 * <p>
 * <p>
 * 怎么样让最底层的lin3响应事件？
 * 将lin1的dispatchTouchEvent方法return false即可 点击btn2 action4  ？？？？？？？？？？？？？
 * <p>
 * **************************************************************************************************************
 * <p>
 * <p>
 * action5 所有方法不动  lin1的onInterceptTouchEvent方法return true
 * **************************************************************************************************************
 * <p>
 * action6 所有方法不动 lin1的onInterceptTouchEvent方法return true    dispatchTouchEvent return false
 * **************************************************************************************************************
 * <p>
 * action7 所有方法不动 lin1的onInterceptTouchEvent方法return true    dispatchTouchEvent return false   onTouchEvent retrun true
 * **************************************************************************************************************
 * <p>
 * <p>
 * action8 所有方法不动 lin1的onInterceptTouchEvent方法return true    dispatchTouchEvent return true   onTouchEvent retrun true
 * **************************************************************************************************************
 * <p>
 * <p>
 * <p>
 * 总结：
 * <p>
 * <p>
 * 0.一层view的事件其实都是在一个方法中完成dispatchTouchEvent。这个方法会调用本层的onInterceptTouchEvent或者onTouchEvent。
 * <p>
 * 1.所有view默认设置，遵循点击谁谁响应，如果多层叠加，最上面的响应(这些建立在view必须有处理事件的能力，实现onclick接口等，没有吃事件的能力是不行的)
 * <p>
 * <p>
 * 2.dispatchTouchEvent用来拦截是否向和本层平级的下一层传递  onInterceptTouchEvent用来拦截是否向本层内的子view传递
 * <p>
 * <p>
 * 3.找到消耗事件view前，dispatchTouchEvent方法中会调用本层的onInterceptTouchEvent方法。找到消耗事件view后，
 * dispatchTouchEvent方法中会调用本层的onTouchEvent方法。
 * <p>
 * <p>
 * 4.事件都是从dispatchTouchEvent中进行分发。无论消耗前后。
 * <p>
 * <p>
 * 5.只有那些点击点落在其内的view才会有机会去响应事件 如果不在其内  不参与事件分发。例如我只点击了activity根布局的范围，那么布局上的其他view不参与事
 * 件。会直接调用activity的onTouchEvent去消耗事件
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


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG2, "onTouchEvent" + "event=" + event.actionToString(event.getAction()));
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
        Log.v(TAG2, "onTouchEvent" + "  flag=" + flag + "  after super.onTouchEvent(event)");
        return flag;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.v(TAG2, "dispatchTouchEvent" + "  event=" + event.actionToString(event.getAction()));
//        boolean flag = super.dispatchTouchEvent(ev);
//        Log.v(TAG2, "dispatchTouchEvent=flag=:" + flag);
//        return flag;
        boolean flag = super.dispatchTouchEvent(event);
        Log.v(TAG2, "flag=" + flag + "  dispatchTouchEvent after super.dispatchTouchEvent(event)");
        return flag;
    }


}
