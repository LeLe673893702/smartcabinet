/*
 * Copyright (C) 2018 RGBSDK, Inc. All Rights Reserved.
 */
package com.llc.smartcabinet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

/**
 * 人脸矩形框
 * @author newler
 * @date 2020/1/11
 *
 */
public class RectangleView extends View {

    //  声明Paint对象
    private Paint mPaint = null;
    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;
    private int color;
    private String msg;
    private int style = 0;
    private Boolean isInit = true;

    public RectangleView(Context context, int left, int top, int right, int bottom,
                         int color, String msg, int style, Boolean isInit){
        super(context);
        //构建对象
        mPaint = new Paint();
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        this.msg = msg;
        this.style = style;
        this.isInit = isInit;
    }

    int rotation = 0;
    int frontRotation = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        //设置无锯齿
        mPaint.setAntiAlias(true);
        //canvas.drawARGB(50,255,227,0);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(StrokeWidth);
        mPaint.setColor(color);
        mPaint.setAlpha(100);
        // 绘制绿色实心矩形
        canvas.drawRect(left, top, right, bottom, mPaint);


        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(36.0f);
        canvas.drawText(msg, left, top, mPaint);
        //mPaint.setColor(Color.RED);
        //canvas.drawRect(rect,mPaint);
        */
        int leftV = left;
        int rightV = right;
        int topV = top;
        int bottomV = bottom;


        if(!isInit){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(36.0f);
            mPaint.setColor(color);
            canvas.drawText(msg, 30, 50, mPaint);
            canvas.restore();
            return;
        }

        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);

        paint.setTextSize(25);
        paint.setTextAlign(Paint.Align.CENTER);
        //paint.setColor(0xff20960c);
        //paint.setColor(0xff30cd16);
        paint.setStrokeWidth(2);
        //canvas.save(Canvas.MATRIX_SAVE_FLAG);
        //paint.setAlpha(255);
        //paint.setStyle(Paint.Style.FILL);

        //paint.setColor(0xffc8c8c8);
        paint.setColor(color);
        paint.setAlpha(80);
        //paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30);

        canvas.rotate(frontRotation,((leftV+rightV)/2),((topV+bottomV)/2));
        canvas.rotate(rotation,((leftV+rightV)/2),((topV+bottomV)/2));
        canvas.rotate(-frontRotation,((leftV+rightV)/2),((topV+bottomV)/2));
        canvas.rotate(-rotation,((leftV+rightV)/2),((topV+bottomV)/2));
        paint.setStyle(Paint.Style.STROKE);

        if(!TextUtils.isEmpty(msg)) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(28.0f);
            mPaint.setColor(color);
            canvas.drawText(msg, leftV, topV - 20, mPaint);
        }

        if(style == 0) {
            drawRectangle(leftV, rightV, topV, bottomV, paint, canvas);
        }else if (style == 1) {
            drawStyle1Rectangle(leftV, rightV, topV, bottomV, paint, canvas);
        }
        //drawRectangle(left, right, top, bottom, mPaint, canvas);
        canvas.restore();
    }

    public void drawRectangle(int leftV,int rightV,int topV,int bottomV,Paint paint,Canvas canvas){

        paint.setAlpha(90);
        canvas.drawRect(leftV , topV ,rightV, bottomV, paint);
        /*
        //canvas.drawRect(leftV + bigger2, topV + bigger2, rightV - bigger2, bottomV - bigger2, paint);
        paint.setStyle(Paint.Style.STROKE);
        int no_grids=12;
        int dist=(rightV-leftV)/no_grids;
        paint.setStrokeWidth(3);
        paint.setStrokeWidth(7);
        paint.setAlpha(255);
        // paint.setStrokeWidth(12);
        int lineEnd = (rightV-leftV) / 5;// 80;//rightV - leftV / 2;
        int distPlus=(int)dist*1/2;
        //Left
        canvas.drawLine(leftV, topV, leftV + lineEnd, topV, paint);
        canvas.drawLine(leftV, topV, leftV, topV + lineEnd, paint);
        //Right
        canvas.drawLine(rightV - lineEnd, topV, rightV, topV, paint);
        canvas.drawLine(rightV, topV, rightV, topV + lineEnd, paint);
        //BottomLeft
        canvas.drawLine(leftV, bottomV, leftV + lineEnd, bottomV, paint);
        canvas.drawLine(leftV, bottomV - lineEnd, leftV, bottomV, paint);
        //BottomRight
        canvas.drawLine(rightV - lineEnd, bottomV, rightV, bottomV, paint);
        canvas.drawLine(rightV, bottomV - lineEnd, rightV, bottomV, paint);

        canvas.drawLine((rightV+leftV)/2,topV+distPlus,(rightV+leftV)/2, topV-distPlus, paint);
        canvas.drawLine(leftV+distPlus,(topV+bottomV)/2,leftV-distPlus,(topV+bottomV)/2, paint);
        canvas.drawLine((rightV+leftV)/2,bottomV+distPlus,(rightV+leftV)/2, bottomV-distPlus, paint);
        canvas.drawLine(rightV+distPlus,(topV+bottomV)/2,rightV-distPlus,(topV+bottomV)/2, paint);
        // System.out.println("Time after rendering rectangle");
        paint.setStyle(Paint.Style.FILL);
        */
        paint.setTextSize(getPX(18));
    }

    public int getPX(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }

    protected void drawStyle1Rectangle(int leftV,int rightV,int topV,int bottomV,Paint paint,Canvas canvas){

        int bigger = 0;//(int)(0.1f*(rightV-leftV)) ;
        int bigger2 = bigger + 12;
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(30);
        paint.setStyle(Paint.Style.STROKE);

        paint.setAlpha(255);
        int lineEnd = (rightV-leftV) / 15;
        int dist=(rightV-leftV)/12;
        int distPlus=(int)dist*1/2;
        int cornerOffset = 1;
        paint.setStrokeWidth(4);

        //TopLeft
        canvas.drawLine(leftV-cornerOffset, topV, leftV + lineEnd, topV, paint);
        canvas.drawLine(leftV, topV, leftV, topV + lineEnd, paint);
//                    //TopRight
        canvas.drawLine(rightV - lineEnd, topV, rightV+cornerOffset, topV, paint);
        canvas.drawLine(rightV, topV, rightV, topV + lineEnd, paint);
//                    //BottomLeft
        canvas.drawLine(leftV-cornerOffset, bottomV, leftV + lineEnd+cornerOffset, bottomV, paint);
        canvas.drawLine(leftV, bottomV - lineEnd, leftV, bottomV, paint);
//                    //BottomRight
        canvas.drawLine(rightV - lineEnd, bottomV, rightV+cornerOffset, bottomV, paint);
        canvas.drawLine(rightV, bottomV - lineEnd, rightV, bottomV, paint);


        float fac = 1.5f;


        //细横线
        //TopLine
        canvas.drawLine(leftV+(int)(fac*lineEnd), topV, rightV-(int)(fac*lineEnd), topV, paint);
        //LeftLine
        canvas.drawLine(leftV, topV+(int)(fac*lineEnd), leftV,bottomV-(int)(fac*lineEnd), paint);
        //BottomLine
        canvas.drawLine(leftV+(int)(fac*lineEnd), bottomV, rightV-(int)(fac*lineEnd), bottomV, paint);
        //RightLine
        canvas.drawLine(rightV, topV+(int)(fac*lineEnd), rightV, bottomV-(int)(fac*lineEnd), paint);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30);

        canvas.save();
    }

}
