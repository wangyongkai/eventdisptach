package com.test.touch;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * <p>
 * *************************************************************************************************************
 * <p>
 * <p>
 * aciton1 所有方法不动  点击lin2
 * <p>
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
 * 将lin1的dispatchTouchEvent方法return false即可 点击btn2 action4
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
 * #########################################################################################################################
 * 总体过程：
 * 1.找到事件消耗者？
 * 父类dispatchTouchEvent先调用父类的onInterceptTouchEvent方法如果return true则交给本层的onTouchEvent。 如果return false 说明不拦截，
 * 不拦截就没必要执行onTouchEvent。要分发给子类，
 * 然后父类调用子类的dispatchTouchEvent。子类如果是viewgroup
 * 则继续调用该层的onInterceptTouchEvent方法如果return false 说明不拦截，继续调用下一层子类的dispatchTouchEvent。
 * 子类再按照这个规则循环调用。直到onTouchEvent return true 说明找到事件消耗者，开始一层层往上super调用dispatchTouchEvent 告诉父类dispatchTouchEvent要return true.
 * 事件本应该子类消费，但是如果onTouchEvent return false 也就是子类不想消费，子类dispatchTouchEvent返回false给父类。此时，父类会调用本层的onTouchEvent
 * 去消耗事件。因为事件最应该子类去消耗，如果不想消耗，次最应该消耗就是父类。
 * <p>
 * 2.后续事件直接传递给事件消耗者。
 * 后续事件在事件消耗者view层级会dispatchTouchEvent直接调用onTouchEvent
 * 在view层级的上几个层级，依然会跟之前一样调用dispatchTouchEvent-onInterceptTouchEvent
 * #########################################################################################################################
 * 问题：
 * 谁该参与事件消耗？
 * 应该是遵循点击落在谁的区域内，谁在最上层。
 * #########################################################################################################################
 * 注意点：
 * <p>
 * 0.一层view的事件其实都是在一个方法中完成dispatchTouchEvent。这个方法会调用本层的onInterceptTouchEvent或者onTouchEvent。
 * <p>
 * 1.所有view默认设置，遵循点击谁谁响应，如果多层叠加，最上面的响应(这些建立在view必须有处理事件的能力，实现onclick接口等，没有吃事件的能力是不行的)
 * <p>
 * <p>
 * 2.dispatchTouchEvent用来告诉父类本层的view是否消耗这个事件  onInterceptTouchEvent用来拦截是否向本层内的子view传递
 * <p>
 * <p>
 * 3.找到消耗事件view前，dispatchTouchEvent方法中会调用本层的onInterceptTouchEvent方法。找到消耗事件view后，如果这个view是个viewgroup
 * dispatchTouchEvent方法中会调用本层的onTouchEvent方法而不调用onInterceptTouchEvent
 * <p>
 * <p>
 * 4.onInterceptTouchEvent只负责是否把事件交由本层处理，ontouchevent返回值才最终决定了dispatchTouchEvent的返回值。
 * <p>
 * <p>
 * 5.只有那些点击点落在其内的view才会有机会去响应事件 如果不在其内  不参与事件分发。例如我只点击了activity根布局的范围，那么布局上的其他view不参与事
 * 件。会直接调用activity的onTouchEvent去消耗事件
 * <p>
 * 6.view的dispatchTouchEvent如果返回一个false 表明自己不消耗事件 即使自己的子view想消耗也不行！！！！！！
 *
 *
 * 7.如果该消耗事件的view没有消耗事件，那么则交给他的父view消耗。
 *
 *
 * 8.super.dispatchTouchEvent(ev)两个功能：将事件分发给子类和调用本层的onInterceptTouchEvent/onTouchEvent。如果不用super则这两个功能无法实现。
 *
 *
 * 9.super.onTouchEvent(event)的功能是 onclick方法实现。
 *
 *
 * 简单总结
 我们可以发现这里，
 默认是return false， 不拦截
 也就是说如果是直接extends ViewGroup，
 return super.onInterceptTouchEvent(ev)
 就是 return false
 表示 不拦截


 Activity中super.dispatchTouchEvent(ev) 然后return false事件仍然照常传递

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


    @SuppressLint("ClickableViewAccessibility")
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


        mBtn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("MyButton2", "MyButton2------onTouch");
                return false;
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyButton2", "MyButton2------onClick");
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            Log.d(TAG2, "child" + i + "=   " + child.getClass().getSimpleName());
        }

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
