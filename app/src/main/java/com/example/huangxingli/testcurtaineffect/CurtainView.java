package com.example.huangxingli.testcurtaineffect;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/**
 * Created by huangxingli on 2014/12/11.
 */
public class CurtainView extends RelativeLayout implements View.OnTouchListener {
    Scroller scroller;
    boolean isMove=false;
    int curtainHeight;
    boolean isOpne=false;
    private int startY;

    public CurtainView(Context context) {
        super(context);
        init(context);
    }

    public CurtainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurtainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        scroller=new Scroller(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.curtainview,this);
        final ImageView curtainView= (ImageView) view.findViewById(R.id.curtain);
        ImageView line= (ImageView) view.findViewById(R.id.line);
        View v;

        curtainView.post(new Runnable() {
            @Override
            public void run() {
                curtainHeight=curtainView.getHeight();
                Log.v("TAG","===CURTAINhEIGHT IS "+ curtainHeight);
                scrollTo(0,curtainHeight);
            }
        });
        line.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isMove) {
            int actioon = event.getAction();
            int distanceX, distanceY;
            //int startX = 0;
           // startY = 0;
            Context co;


            switch (actioon) {
                case MotionEvent.ACTION_DOWN:
                    Log.v("TAG", "---ACTION  DOWN");

                   startY = (int)event. getRawY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.v("TAG", "===action_move");
                    int moveX = (int) event.getX();
                    int moveY = (int) event.getRawY();
                //    Log.v("TAG","--_________________________________RAW Y IS "+event.getRawY()+" ____________________________Y IS "+event.getY());
                  //  distanceX = moveX - startX;
                    distanceY = moveY - startY;
                    if (distanceY>0){
                        //向下滑动
                        if (distanceY<curtainHeight) {
                            scrollTo(0, -(int) distanceY + curtainHeight);

                        }
                    }else{
                        //向上滑动
                        scrollTo(0,-distanceY);

                    }


                    break;
                case MotionEvent.ACTION_UP:
                    Log.v("TAG","---ACTION_UP");
                    int upY= (int) event.getRawY();
                    int dis=upY- startY;

                    if (upY> startY) {
                        //向下滑动
                            Log.v("TAG","====向下滑动 curtainHeight is  "+curtainHeight);

                            if (Math.abs(dis) > curtainHeight / 2) {
                                //如果滚动的距离超过窗帘高度的一半，打开窗帘，否则关闭窗帘
                                Log.v("TAG","---滚动距离超过窗帘高度一半，打开窗帘");
                                startMoveAnimation(getScrollY(), -getScrollY(), 1000);
                                isOpne=true;

                            }else{
                                Log.v("TAG","===滚动距离没有超过窗帘高度一半，关闭窗帘");
                                startMoveAnimation(getScrollY(), curtainHeight - getScrollY(), 1000);
                                isOpne=false;
                            }


                    }else{
                        //向上滑动
                        Log.v("TAG","-===向上滚动");
                        if (isOpne){
                            if (Math.abs(dis)>curtainHeight/2){
                                //由打开转到关闭窗帘
                                Log.v("TAG","-====由打开装到关闭状态");
                                //scrollTo(0,curtainHeight);

                               //11111 此处跟2222处效果是一样的只是有一个时间，让其缓慢的滚动到目的地。同时注意此处startScroll(_)方法。dy参数是指滚动的距离,即偏移量
                                /**
                                 * Start scrolling by providing a starting point, the distance to travel,
                                 * and the duration of the scroll.
                                 *
                                 * @param startX Starting horizontal scroll offset in pixels. Positive
                                 *        numbers will scroll the content to the left.
                                 * @param startY Starting vertical scroll offset in pixels. Positive numbers
                                 *        will scroll the content up.
                                 * @param dx Horizontal distance to travel. Positive numbers will scroll the
                                 *        content to the left.
                                 * @param dy Vertical distance to travel. Positive numbers will scroll the
                                 *        content up.
                                 * @param duration Duration of the scroll in milliseconds.
                                 */
                               /* public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                                    mMode = SCROLL_MODE;
                                    mFinished = false;
                                    mDuration = duration;
                                    mStartTime = AnimationUtils.currentAnimationTimeMillis();
                                    mStartX = startX;
                                    mStartY = startY;
                                    mFinalX = startX + dx;
                                    mFinalY = startY + dy;
                                    mDeltaX = dx;
                                    mDeltaY = dy;
                                    mDurationReciprocal = 1.0f / (float) mDuration;
                                }*/
                                startMoveAnimation(getScrollY(), curtainHeight + dis, 1000);
                                //22222 此处是直接滚动到目的地，不是偏移量
                               // scrollTo(0,curtainHeight);
                                /**
                                 * Set the scrolled position of your view. This will cause a call to
                                 * {@link #onScrollChanged(int, int, int, int)} and the view will be
                                 * invalidated.
                                 * @param x the x position to scroll to
                                 * @param y the y position to scroll to
                                 */
                              /*  public void scrollTo(int x, int y) {
                                    if (mScrollX != x || mScrollY != y) {
                                        int oldX = mScrollX;
                                        int oldY = mScrollY;
                                        mScrollX = x;
                                        mScrollY = y;
                                        invalidateParentCaches();
                                        onScrollChanged(mScrollX, mScrollY, oldX, oldY);
                                        if (!awakenScrollBars()) {
                                            postInvalidateOnAnimation();
                                        }
                                    }
                                }*/
                                isOpne=false;

                            }else{
                                Log.v("TAG","====恢复到打开状态");
                                //恢复到打开状态,即向下滑动恢复到打开状态
                                //注意此处getScrollY的值跟dis的值是一样的！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
                                Log.v("TAG","=====DIS IS =="+dis+"--------getScrollY is "+getScrollY());
                                startMoveAnimation(getScrollY(), dis, 1000);
                                isOpne=true;

                            }
                        }
                    }
                    break;

            }

        }

        return  false;
    }

    private void startMoveAnimation(int y,int dy,int duration) {
        scroller.startScroll(0,y,0,dy,duration);
        postInvalidate();
    }



    @Override
    public void computeScroll() {

        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
            isMove=true;
        }else{
            isMove=false;
        }

        super.computeScroll();
    }
}
