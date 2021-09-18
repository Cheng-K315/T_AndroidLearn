package com.example.myapplication5.View;

import java.util.List;

import com.example.myapplication5.Bean.ShanData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Administrator
 * @时间 下午4:18:44
 * @描述 扇形统计图
 */
public class ShanView extends View {

    private int mHeight, mWidth;// 宽高
    private Paint mPaint;// 扇形的画笔
    private Paint mTextPaint;// 画文字的画笔
    private int centerX, centerY;// 中心坐标
    private int maxNum;// 扇形图的最大块数 超过的item就合并到其他
    // 颜色 默认的颜色
    private int[] mColors = { Color.parseColor("#9dff9d"), Color.parseColor("#50b9f1"),
            Color.parseColor("#ffa4c4"), Color.parseColor("#ffd2da"), Color.parseColor("#fff579") };

    private int radius = 100;// 半径
    private int total;// 数据的总和
    private int[] datas;// 数据集
    private String[] texts;// 每个数据对应的文字集
    private String text = null;// 待办工单数量

    public ShanView(Context context) {
        super(context);
    }

    public ShanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 初始化
    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(40);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽高 不要设置wrap_content
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int oldL = left;
        int oldT = top;
        int oldB = right;
        int oldR = bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画布中心点
        centerX = (getRight() - getLeft()) / 2;
        centerY = (getBottom() - getTop()) / 2;
        int min = mHeight > mWidth ? mWidth : mHeight;
        if (radius > min / 2) {
            radius = (int) ((min - getPaddingTop() - getPaddingBottom()) / 3.5);
            setRadius(radius);// 根据半径大小设置字体大小
        }
        // 无数据
        if (datas == null || datas.length == 0) {
            // 在中心点画白色圆圈
            mPaint.setColor(Color.parseColor("#ffffff"));
            canvas.drawCircle(centerX, centerY, radius, mPaint);
            // 画文字
//            text = "待办工单(0)";
            drawTextRect(canvas, Color.parseColor("#efcc06"));// 黄
        } else {
            canvas.save();// 画扇形
            drawCircle(canvas);
            canvas.restore();
            canvas.save();
            drawLineAndText(canvas);// 线与文字
            canvas.restore();
        }
    }

    /**
     ** 画扇形
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        RectF rect = new RectF((float) (centerX - radius), centerY - radius, centerX + radius,
                centerY + radius);
        int start = 0;
        int num = maxNum < datas.length ? maxNum : datas.length;
        for (int i = 0; i < num; i++) {
            float angles = (float) ((datas[i] * 1.0f / total) * 360);
            if (i == (num - 1)) {// 最后一块,添加该判断后不显示“其他”部分
                angles = 360 - start;
            }
            mPaint.setColor(mColors[i % mColors.length]);
            canvas.drawArc(rect, start, angles, true, mPaint);
            start += angles;
        }
        drawTextRect(canvas, Color.parseColor("#ffffff"));// 画文字 白
    }

    /**
     ** 画文字
     *
     * @param canvas 画布
     * @param color  字体颜色
     */
    private void drawTextRect(Canvas canvas, int color) {
        // 画文字
        Rect centerTextRec = new Rect();
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(radius / 4);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.getTextBounds(text, 0, text.length(), centerTextRec);
        int centerW = centerTextRec.width();
        int centerH = centerTextRec.height();
        canvas.drawText(text, 0, text.length(), centerX - (centerW / 2), centerY + (centerH / 3), paint);
    }

    /**
     ** 遍历数据画线与文字
     *
     * @param canvas
     */
    private void drawLineAndText(Canvas canvas) {
        int start = 0;
        canvas.translate(centerX, centerY);// 平移画布到中心
        mPaint.setStrokeWidth(4);// 设置线条的粗细
        int num = maxNum < datas.length ? maxNum : datas.length;
        for (int i = 0; i < num; i++) {
            float angles = (float) ((datas[i] * 1.0f / total) * 360);
            if (i == (num - 1)) {// 最后一块,添加该判断后不显示“其他”部分
                angles = 360 - start;
            }
            drawLine(canvas, start, angles, texts[i], mColors[i % mColors.length]);
            start += angles;
        }
//		// 画其他
//		if (start < 359) {
//			drawLine(canvas, start, 360 - start, "其他", Color.GRAY);
//		}
    }

    /**
     ** 画线与文字
     *
     * @param canvas
     * @param start
     * @param angles
     * @param text
     * @param color
     */
    private void drawLine(Canvas canvas, int start, float angles, String text, int color) {
        mPaint.setColor(color);
        int line = 75;// line为伸出去的直线的长度
        float startX = (float) ((radius - 20) * Math.cos((2 * start + angles) / 2 * Math.PI / 180));
        float startY = (float) ((radius - 20) * Math.sin((2 * start + angles) / 2 * Math.PI / 180));
        float stopX = (float) ((radius + line) * Math.cos((2 * start + angles) / 2 * Math.PI / 180));
        float stopY = (float) ((radius + line) * Math.sin((2 * start + angles) / 2 * Math.PI / 180));
        // 画凸起的指示直线
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);

        // 测量文字大小
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        int h = rect.height();
        int offset = 10;// 文字在横线的偏移量

        // 画横线
        int hLine = 50;// 横线的长度
        int endX;
        if (stopX > 0) {// 右
            endX = (centerX - getPaddingRight() - 20);
            endX = (int) (stopX + hLine);// hLine为横线的长度
        } else {// 左
            endX = (-centerX + getPaddingLeft() + 20);
            endX = (int) (stopX - hLine);// hLine为横线的长度
        }
        // 画横线
        canvas.drawLine(stopX, stopY, endX, stopY, mPaint);
        // 画圆点
        canvas.drawCircle(endX, stopY, (float) 3.6, mPaint);

        // 判断横线是画在左边还是右边
        int dx = (int) (endX - stopX);
        // 画文字
        canvas.drawText(text, 0, text.length(), dx > 0 ? endX + offset : endX - w - offset, stopY - 5,
                mTextPaint);
        // 测量百分比大小
        String percentage = angles / 3.60 + "";
        percentage = "(" + percentage.substring(0, percentage.length() > 4 ? 4 : percentage.length()) + "%)";
        mTextPaint.getTextBounds(percentage, 0, percentage.length(), rect);
        w = rect.width();// 百分比字体的宽度
        // 画百分比
        canvas.drawText(percentage, 0, percentage.length(), dx > 0 ? endX + offset : endX - w - offset,
                stopY + h, mTextPaint);
    }

    /**
     * 设置扇形区域颜色
     *
     * @param mColors
     */
    public void setColors(int[] mColors) {
        this.mColors = mColors;
        invalidate();// 绘制刷新
    }

    /**
     * 设置字体大小
     *
     * @param mTextSize
     */
    public void setTextSize(int mTextSize) {
        mTextPaint.setTextSize(mTextSize);
        invalidate();// 绘制刷新
    }

    /**
     ** 设置半径大小
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
        setTextSize(radius / 6);
        invalidate();// 绘制刷新
    }

    /**
     ** 设置字体描述
     *
     * @param str
     */
    public void setTextDescript(String str) {
        text = str;
    }

    /**
     * *设置最大块数
     *
     * @param maxNum
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        invalidate();// 绘制刷新
    }

    /**
     * 设置数据
     */
    public void setData(List<ShanData> listData) {
        if (listData != null) {
//            text = "待办工单";// 初始化
            total = 0;// 总数置为0
            int size = listData.size();
            datas = new int[size];
            texts = new String[size];
            for (int i = 0; i < size; i++) {
                ShanData shanData = listData.get(i);
                total += shanData.getData();
                datas[i] = shanData.getData();
                texts[i] = shanData.getText() + "(" + datas[i] + ")";
            }
            text = text + "(" + total + ")";
        }
        invalidate();// 绘制刷新
    }
}