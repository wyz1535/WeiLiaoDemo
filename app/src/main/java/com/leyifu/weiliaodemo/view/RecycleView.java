package com.leyifu.weiliaodemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by hahaha on 2017/7/7 0007.
 */

public class RecycleView extends RecyclerView {

    private static final String TAG = "RecycleView";
    private int mTouchSlop;
    private boolean isRestoring;
    private boolean isBeingDragged;
    private int mActivePointId;
    private float mInitialMotionY;
    private float mScale;

    public RecycleView(Context context) {
        this(context, null);
    }

    public RecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        Log.e(TAG, "mTouchSlop: " + mTouchSlop);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        Log.e(TAG, "action: " + action);
        if (isRestoring && action == MotionEvent.ACTION_DOWN) {
            isRestoring = false;
        }

        if (isEnabled() || isRestoring || (!isScroolTop() && !isScroolBottom())) {
            return super.onInterceptTouchEvent(event);
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                mActivePointerId表示在多点触控是当前活动手指的id
                mActivePointId = event.getPointerId(0);
                Log.e(TAG, "mActivePointId: " + mActivePointId);
                isBeingDragged = false;
                float initialMotionY = getMotionEventY(event);
                if (initialMotionY == -1f) {
                    return super.onInterceptTouchEvent(event);
                }
                mInitialMotionY = initialMotionY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointId == MotionEvent.INVALID_POINTER_ID) {
                    return super.onInterceptTouchEvent(event);
                }
                float moveY = getMotionEventY(event);
                if (moveY == -1f) {
                    return super.onInterceptTouchEvent(event);
                }

                if (isScroolTop() && !isScroolBottom()) {
                    if (moveY - mInitialMotionY > mTouchSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }
                } else if (!isScroolTop() && isScroolBottom()) {
                    if (mInitialMotionY - moveY > mTouchSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }
                } else if (isScroolTop() && isScroolBottom()) {
                    if (Math.abs(moveY - mInitialMotionY) > mTouchSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }
                } else {
                    return super.onInterceptTouchEvent(event);
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                mActivePointId = MotionEvent.INVALID_POINTER_ID;
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_UP:
                float upY = getMotionEventY(event);
                if (isScroolTop() && Math.abs(upY - mInitialMotionY) < 50) {
                    return super.onInterceptTouchEvent(event);
                }
                break;
        }
        return isBeingDragged || super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (MotionEventCompat.getActionMasked(e)) {
            case MotionEvent.ACTION_DOWN:
                mActivePointId = e.getPointerId(0);
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = getMotionEventY(e);
                if (isScroolTop() && !isScroolBottom()) {
                    float mDistance = moveY - mInitialMotionY;
                    if (mDistance < 0) {
                        return super.onTouchEvent(e);
                    }
                    mScale = calculateRate(mDistance);
                    pull(mScale);
                } else if (!isScroolTop() && isScroolBottom()) {
                    float mDistance = mInitialMotionY - moveY;
                    if (mDistance < 0) {
                        return super.onTouchEvent(e);
                    }
                    mScale = calculateRate(mDistance);
                    push(mScale);
                } else if (isScroolTop() && isScroolBottom()) {
                    float mDistance = moveY - mInitialMotionY;
                    if (mDistance < 0) {
                        float mScale = calculateRate(-mDistance);
                        push(mScale);
                    } else {
                        float mScale = calculateRate(mDistance);
                        pull(mScale);
                    }
                } else {
                    return super.onTouchEvent(e);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isScroolTop() && !isScroolBottom()) {
                    animateRestore(true);
                } else if (!isScroolTop() && isScroolBottom()) {
                    animateRestore(false);
                } else if (isScroolTop() && isScroolBottom()) {
                    if (getMotionEventY(e) - mInitialMotionY > 0) {
                        animateRestore(true);
                    } else {
                        animateRestore(false);
                    }
                } else {
                    return super.onTouchEvent(e);
                }
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mActivePointId = e.getPointerId(MotionEventCompat.getActionIndex(e));
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(e);
                break;
        }
        return super.onTouchEvent(e);
    }

    private void animateRestore(final boolean b) {
        ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator(2f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (b) {
                    pull(value);
                } else {
                    push(value);
                }
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isRestoring = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isRestoring = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    private void onSecondaryPointerUp(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.getActionIndex(event);

        final int pointerId = event.getPointerId(pointerIndex);
        if (pointerId == mActivePointId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointId = event.getPointerId(newPointerIndex);
        }
    }

    private void pull(float mScale) {
        this.setPivotY(0);
        this.setScaleY(mScale);
    }

    private void push(float mScale) {
        this.setPivotY(this.getHeight());
        this.setScaleY(mScale);
    }

    private float calculateRate(float mDistance) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float originalDragPercent = mDistance / screenHeight;
        float dragPercent = Math.min(1f, originalDragPercent);
        float rate = 2f * dragPercent - (float) Math.pow(dragPercent, 2f);
        Log.e(TAG, "screenHeight: " + screenHeight + " originalDragPercent="
                + originalDragPercent + " dragPercent=" + dragPercent + " rate=" + rate);
        return 1 + rate / 5f;
    }

    private float getMotionEventY(MotionEvent event) {
        int index = event.findPointerIndex(mActivePointId);

        return index < 0 ? -1f : event.getY(index);
    }

    private boolean isScroolTop() {
        return !ViewCompat.canScrollHorizontally(this, -1);
    }

    private boolean isScroolBottom() {
        return !ViewCompat.canScrollVertically(this, 1);
    }
}