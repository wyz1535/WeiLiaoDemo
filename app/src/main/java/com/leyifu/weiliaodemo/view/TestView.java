package com.leyifu.weiliaodemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.leyifu.weiliaodemo.R;

/**
 * Created by hahaha on 2017/6/30 0030.
 */

public class TestView extends View {

    private static final String TAG = "TestView";
    private String mText;
    private int mTextSize;
    private TextPaint mPaint;
    private Paint.FontMetrics metrics;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        mText = ta.getString(R.styleable.TestView_android_text);
        mTextSize = ta.getDimensionPixelSize(R.styleable.TestView_android_textSize, 24);

        ta.recycle();

        mPaint = new TextPaint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

//          Android 测量时的 3 种布局模式
//        MeasureSpec.EXACTLY 模式  定义了宽高 比如 100dp match_parent
//     MeasureSpec.AT_MOST 模式   根据自己的内容计算宽高，但是数值不得超过 ViewGroup 给出的建议值 比如 wrap_content
//    Measure.UNSPECIFIED  自己想要多少就要多少 不根据父布局来  一般很少用

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heigtMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int resultW = widthSize;
        int resultH = heightSize;

        if (widthMode == MeasureSpec.AT_MOST) {
            if (!TextUtils.isEmpty(mText)) {
                int contentW = (int) mPaint.measureText(mText);
                contentW += getPaddingLeft() + getPaddingRight();
                resultW = contentW < widthSize ? contentW : widthSize;
            }
        }

        if (heigtMode == MeasureSpec.AT_MOST) {
            if (!TextUtils.isEmpty(mText)) {
                int contentH = mTextSize;
                contentH += getPaddingTop() + getPaddingBottom();
                resultH = contentH < heightSize ? contentH : heightSize;
            }
        }

        setMeasuredDimension(resultW,resultH);

        Log.e(TAG, "widthMeasureSpec: " + widthMeasureSpec + " heightMeasureSpec:" + heightMeasureSpec);
        Log.e(TAG, "resultW: " + resultW + " resultH:" + resultH);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        int cy = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;

        metrics = mPaint.getFontMetrics();
        cy += metrics.descent;

        canvas.drawColor(Color.RED);
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        canvas.drawText(mText,cx,cy,mPaint);

    }
}
