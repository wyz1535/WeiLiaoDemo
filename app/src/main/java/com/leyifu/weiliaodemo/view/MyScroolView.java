package com.leyifu.weiliaodemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

/**
 * Created by hahaha on 2017/7/7 0007.
 */

public class MyScroolView extends ScrollView {

    private final int mSlop;
    private boolean isRestoring;
    private boolean isBeingDragged;
    private int pointerId;
    private float mPointY;
    private float downY;
    private float mDistance;
    private float mScale;

    public MyScroolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = MotionEventCompat.getActionMasked(ev);
        if (isRestoring && action == MotionEvent.ACTION_DOWN) {
            isRestoring = false;
        }
        if (!isEnabled() || isRestoring || (!isScroolToTop() && !isScroolToBottom())) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointerId = ev.getPointerId(0);
                isBeingDragged = false;
                downY = getMotionPointY(ev);
                if (downY == -1f) {
                    return super.onInterceptTouchEvent(ev);
                }
                mPointY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointerId == MotionEvent.INVALID_POINTER_ID) {
                    return super.onInterceptTouchEvent(ev);
                }
                float moveY = getMotionPointY(ev);
                if (moveY == -1f) {
                    return super.onInterceptTouchEvent(ev);
                }
                if (isScroolToTop() && !isScroolToBottom()) {
                    if (moveY - mPointY > mSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }
                } else if (!isScroolToTop() && isScroolToBottom()) {
                    if (mPointY - moveY > mSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }
                } else if (isScroolToTop() && isScroolToBottom()) {
                    if (Math.abs(mPointY - moveY) > mSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }
                } else {
                    return super.onInterceptTouchEvent(ev);
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                pointerId = MotionEvent.INVALID_POINTER_ID;
                isBeingDragged = false;
        }
        return isBeingDragged || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                pointerId = ev.getPointerId(0);
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float touchMoveY = getMotionPointY(ev);
                if (isScroolToTop() && !isScroolToBottom()) {
                    mDistance = touchMoveY - mPointY;
                    if (mDistance < 0) {
                        return super.onTouchEvent(ev);
                    }
                    mScale = calculateRate(mDistance);
                    pull(mScale);
                } else if (!isScroolToTop() && isScroolToBottom()) {
                     mDistance = mPointY - touchMoveY;
                    if (mDistance < 0) {
                        return super.onTouchEvent(ev);
                    }
                     mScale = calculateRate(mDistance);
                    push(mScale);
                } else if (isScroolToTop() && isScroolToBottom()) {
                     mDistance = touchMoveY - mPointY;
                    if (mDistance < 0) {
                         mScale = calculateRate(-mDistance);
                        push(mScale);
                    } else {
                         mScale = calculateRate(mDistance);
                        pull(mScale);
                    }
                } else {
                    return super.onTouchEvent(ev);
                }
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                pointerId = ev.getPointerId(MotionEventCompat.getActionIndex(ev));
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP: {
                if (isScroolToTop() && !isScroolToBottom()) {
                    animateRestore(true);
                } else if (!isScroolToTop() && isScroolToBottom()) {
                    animateRestore(false);
                } else if (isScroolToTop() && isScroolToBottom()) {
                    if (mDistance > 0) {
                        animateRestore(true);
                    } else {
                        animateRestore(false);
                    }
                } else {
                    return super.onTouchEvent(ev);
                }
                break;
            }
        }
        return true;
    }

    private void animateRestore(final boolean isPullRestore) {
        ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator(2f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (isPullRestore) {
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

    private void pull(float mScale) {
        this.setPivotY(0);
        this.setScaleY(mScale);
    }

    private void push(float mScale) {
        this.setPivotY(getHeight());
        this.setScaleY(mScale);
    }

    private float calculateRate(float mDistance) {
        float screenHeight = getResources().getDisplayMetrics().heightPixels;
        float originalDrawPresent = mDistance / screenHeight;
        float drawPresent = Math.min(1.0f, originalDrawPresent);
        float rate = (float) (2f * drawPresent - Math.pow(drawPresent, 2f));
        return 1 + rate / 5;
    }

    private float getMotionPointY(MotionEvent ev) {
        int pointerIndex = ev.findPointerIndex(pointerId);
        return pointerIndex < 0 ? -1f : ev.getY(pointerIndex);
    }

    private boolean isScroolToTop() {
        return !ViewCompat.canScrollVertically(this, -1);
    }

    private boolean isScroolToBottom() {
        return !ViewCompat.canScrollVertically(this, 1);
    }

    private void onSecondaryPointerUp(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        final int pointerId01 = event.getPointerId(pointerIndex);
        if (pointerId01 == pointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            pointerId = event.getPointerId(newPointerIndex);
        }
    }
}
