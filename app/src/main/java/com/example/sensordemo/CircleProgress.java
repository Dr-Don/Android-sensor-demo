package com.example.sensordemo;
/**
 *显示百分比的环形进度条
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

public class CircleProgress extends View {

    //画笔
    private Paint paint;
    //圆环颜色
    private int cirColor;
    private int progressColor;
    //圆环宽度
    private float cirWidth;
    private float progressWidth;
    //百分比文字属性
    private int textColor;
    private float textSize;
    private float numSize;
    //最大进度
    private int max;
    //进度条起始角度
    private int startAngle;
    //是否显示中间的进度
    private boolean textShow;
    //当前进度
    private int progress;

    public CircleProgress(Context context){
        this(context,null);
    }

    public CircleProgress(Context context, AttributeSet attrs){
        this(context, attrs ,0);
    }

    /**
    * 构造函数
    */
    public CircleProgress(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);

        paint = new Paint();

        //读取自定义属性的值
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleProgress);

        //获取属性和默认值
        cirColor = mTypedArray.getColor(R.styleable.CircleProgress_cirColor, Color.RED);
        cirWidth = mTypedArray.getDimension(R.styleable.CircleProgress_cirWidth,5);
        progressColor = mTypedArray.getColor(R.styleable.CircleProgress_progressColor,Color.GREEN);
        progressWidth = mTypedArray.getDimension(R.styleable.CircleProgress_progressWidth,cirWidth);
        textColor = mTypedArray.getColor(R.styleable.CircleProgress_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.CircleProgress_textSize, 11);
        numSize = mTypedArray.getDimension(R.styleable.CircleProgress_numSize, 14);
        max = mTypedArray.getInteger(R.styleable.CircleProgress_max, 100);
        startAngle = mTypedArray.getInt(R.styleable.CircleProgress_startAngle, 90);
        textShow = mTypedArray.getBoolean(R.styleable.CircleProgress_textShow, true);
        mTypedArray.recycle();
    }

    /**
    * 绘制函数
    * */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int centerX = getWidth() / 2; // 获取圆心的x坐标
        int radius = (int) (centerX - cirWidth / 2); // 圆环的半径

        // step1 画最外层的大圆环
        paint.setStrokeWidth(cirWidth); // 设置圆环的宽度
        paint.setColor(cirColor); // 设置圆环的颜色
        paint.setAntiAlias(true); // 消除锯齿
        // 设置画笔样式
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerX, radius, paint); // 画出圆环

        // step2 画圆弧-画圆环的进度
        paint.setStrokeWidth(progressWidth); // 设置画笔的宽度使用进度条的宽度
        paint.setColor(progressColor); // 设置进度的颜色
        RectF oval = new RectF(centerX - radius, centerX - radius, centerX + radius, centerX + radius); // 用于定义的圆弧的形状和大小的界限

        int sweepAngle = 360 * progress / max; // 计算进度值在圆环所占的角度
        // 根据进度画圆弧
        canvas.drawArc(oval, startAngle, sweepAngle, false, paint);

        // step3 画文字指示
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        // 计算百分比
        int percent = (int) (((float) progress / (float) max) * 100);

        if (textShow && percent >= 0) {
            // 3.1 画百分比
            paint.setTextSize(numSize);
            paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置为加粗默认字体
            float numWidth = paint.measureText(percent + "%"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(percent + "%", centerX - numWidth / 2, centerX + numSize/2, paint); // 画出进度百分比
        }
    }

    /**
     * 设置进度条的最大值,一般为100
     */
    public synchronized void serMax(int max){
        if(max < 0){
            throw new IllegalArgumentException("max is less than 0!");
        }
        this.max = max;
    }

    /**
     * 获取进度
     */
    public synchronized int getProgress(){
        return progress;
    }

    /**
     * 设置进度
     * */
    public synchronized void setProgress(int progress){
        if(progress < 0){
            throw new IllegalArgumentException("progress is less than 0");
        }
        if(progress > max){
            progress = max;
        }
        this.progress = progress;
        postInvalidate();
    }
}
