package com.leyifu.weiliaodemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by hahaha on 2017/8/7 0007.
 */

public class CirCleView extends View {

    private Paint mPaint;
    private Paint bigPaint;
    private Paint smallPaint;
    private Path mPath;

    public CirCleView(Context context) {
        this(context, null);
    }

    public CirCleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirCleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //弹跳的小球
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);

        //两端的大球球
        bigPaint = new Paint();
        bigPaint.setColor(Color.WHITE);
        bigPaint.setStyle(Paint.Style.STROKE);
        bigPaint.setStrokeWidth(8);
        bigPaint.setAntiAlias(true);

        //两端的小球球
        smallPaint = new Paint();
        smallPaint.setStyle(Paint.Style.FILL);
        smallPaint.setStrokeWidth(8);
        smallPaint.setColor(Color.BLUE);
        smallPaint.setAntiAlias(true);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        mPath.moveTo(widthPixels / 2 - 200 + 20, heightPixels / 2);
        mPath.quadTo(widthPixels / 2 , heightPixels / 2, widthPixels / 2 + 200 - 20, heightPixels / 2);
        canvas.drawPath(mPath,bigPaint);

        canvas.drawCircle(widthPixels / 2, heightPixels / 2 - 300, 20, mPaint);
        canvas.drawCircle(widthPixels / 2 - 200, heightPixels / 2, 20, bigPaint);
        canvas.drawCircle(widthPixels / 2 + 200, heightPixels / 2, 20, bigPaint);
        canvas.drawCircle(widthPixels / 2 - 200, heightPixels / 2, 8, smallPaint);
        canvas.drawCircle(widthPixels / 2 + 200, heightPixels / 2, 8, smallPaint);

    }
}
