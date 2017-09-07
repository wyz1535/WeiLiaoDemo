package com.leyifu.weiliaodemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * Created by hahaha on 2017/8/2 0002.
 */

public class BezierWaveView extends View {

    private int widthPixels;
    private int heightPixels;
    private int offSet;
    private Path mPath;
    private Paint mPaint;

    public BezierWaveView(Context context) {
        this(context, null);
    }

    public BezierWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics meatrics = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(meatrics);
        widthPixels = meatrics.widthPixels;
        heightPixels = meatrics.heightPixels;

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(8);
        setAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        //贝塞尔曲线
        mPath.moveTo(-widthPixels + offSet, heightPixels / 2);

//        mPath.quadTo(-widthPixels * 3 / 4 + offSet, heightPixels / 2 - 100, -widthPixels / 2 + offSet, heightPixels / 2);
//        mPath.quadTo(-widthPixels / 4 + offSet, heightPixels / 2 + 100, 0 + offSet, heightPixels / 2);
//        mPath.quadTo(widthPixels / 4 + offSet, heightPixels / 2 - 100, widthPixels / 2 + offSet, heightPixels / 2);
//        mPath.quadTo(widthPixels * 3 / 4 + offSet, heightPixels / 2 + 100, widthPixels + offSet, heightPixels / 2);
        
        //简化写法
        for (int i = 0; i < 2; i++) {
            mPath.quadTo(-widthPixels * 3 / 4 + (widthPixels * i) + offSet, heightPixels / 2 - 50, -widthPixels / 2 + (widthPixels * i) + offSet, heightPixels / 2);
            mPath.quadTo(-widthPixels / 4 + (widthPixels * i) + offSet, heightPixels / 2 + 50, +(widthPixels * i) + offSet, heightPixels / 2);
        }

        //空白部分填充
        mPath.lineTo(widthPixels, -heightPixels);
        mPath.lineTo(0, -heightPixels);

        canvas.drawPath(mPath, mPaint);
    }

    private void setAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, widthPixels);
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //定义动画的变化率
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offSet = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
