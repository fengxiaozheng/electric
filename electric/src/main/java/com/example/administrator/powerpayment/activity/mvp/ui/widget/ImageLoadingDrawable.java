package com.example.administrator.powerpayment.activity.mvp.ui.widget;
/*
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                    Created by fengxiaozheng 
 *                    on 2018/7/17.
 */

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.drawable.DrawableUtils;

public class ImageLoadingDrawable extends Drawable {

    private Paint mRingBackgroundPaint;
    private int mRingBackgroundColor;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 总进度
    private int mTotalProgress = 10000;
    // 当前进度
    private int mProgress;

    public ImageLoadingDrawable(){
        initAttrs();
    }

    private void initAttrs() {
        mRadius = 16;
        mStrokeWidth = 4;
        mRingBackgroundColor = 0xFFadadad;
        mRingColor = 0xFF0EB6D2;
        mRingRadius = mRadius + mStrokeWidth / 2;
        initVariable();
    }

    private void initVariable() {
        mRingBackgroundPaint = new Paint();
        mRingBackgroundPaint.setAntiAlias(true);
        mRingBackgroundPaint.setColor(mRingBackgroundColor);
        mRingBackgroundPaint.setStyle(Paint.Style.STROKE);
        mRingBackgroundPaint.setStrokeWidth(mStrokeWidth);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.FILL);
        mRingPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        drawBar(canvas,mTotalProgress,mRingBackgroundPaint);
        drawBar(canvas,mProgress,mRingPaint);
    }

    private void drawBar(Canvas canvas, int level, Paint paint) {
        if (level > 0 ) {
            Rect bound= getBounds();
            mXCenter = bound.centerX();
            mYCenter = bound.centerY();
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, -90, ((float) level / mTotalProgress) * 360, false, paint); //
        }
    }

    @Override
    protected boolean onLevelChange(int level) {
        mProgress = level;
        if(level > 0 && level < 10000) {
            invalidateSelf();
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mRingPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mRingPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(this.mRingPaint.getColor());
    }
}
