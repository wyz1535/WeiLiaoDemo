package com.leyifu.weiliaodemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.leyifu.weiliaodemo.R;

/**
 * Created by hahaha on 2017/11/7 0007.
 */

public class ViewCanvas extends View {

    private Paint mPaint;
    private Paint mPaint02;
    private Paint mPaint01;
    private Paint mPaint05;
    private Paint mPaint04;
    private Paint mPaint03;
    private Paint mPaint06;
    private Bitmap bitmap;

    public ViewCanvas(Context context) {
        this(context, null);
    }

    public ViewCanvas(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint01 = new Paint();
        mPaint02 = new Paint();
        mPaint03 = new Paint();
        mPaint04 = new Paint();
        mPaint05 = new Paint();
        mPaint06 = new Paint();

        mPaint.setAntiAlias(true);
        mPaint01.setAntiAlias(true);
        mPaint02.setAntiAlias(true);
        mPaint03.setAntiAlias(true);
        mPaint04.setAntiAlias(true);
        mPaint05.setAntiAlias(true);
        mPaint06.setAntiAlias(true);

        Shader mShader = new LinearGradient(50,200,350,200, Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        Shader mShader01 = new LinearGradient(375,200,675,200, Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
        Shader mShader02 = new LinearGradient(50,525,350,525, Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);

        Shader shader03 = new RadialGradient(525,525,150,Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//        Shader shader04 = new RadialGradient(200,900,150,Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
//        Shader shader05 = new RadialGradient(525,900,150,Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar_03);
        Shader shader04 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mPaint.setShader(mShader);
        mPaint01.setShader(mShader01);
        mPaint02.setShader(mShader02);
        mPaint03.setShader(shader03);
        mPaint04.setShader(shader04);
//        mPaint05.setShader(shader05);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(200,200,150,mPaint);
        canvas.drawCircle(525,200,150,mPaint01);
        canvas.drawCircle(200,525,150,mPaint02);
        canvas.drawCircle(525,525,150,mPaint03);
        canvas.drawCircle(200,900,150,mPaint04);
//        canvas.drawCircle(525,900,150,mPaint05);
//        canvas.drawCircle(525,1200,150,mPaint06);

        canvas.drawBitmap(bitmap,375,750,mPaint05);

    }
}
