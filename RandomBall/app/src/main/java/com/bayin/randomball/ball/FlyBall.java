package com.bayin.randomball.ball;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.bayin.randomball.R;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/9/18.
 ****************************************/

public class FlyBall extends View {
    private int mDrawable = R.mipmap.ball_0_2x;
    private int mNumber = 0;
    private boolean mIsShoot;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mBallWidth;
    private Bitmap bitmapBall;
    private Paint mPaint;

    public FlyBall(Context context) {
        this(context, null);
    }

    public FlyBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mScreenHeight = ScreenUtils.getScreenHeight(context);
        mBallWidth = (int) (mScreenWidth * 0.15);
        initialize();
    }

    /**
     * constructor
     *
     * @param context
     * @param drawable 小球的图片
     * @param number   小球的号码
     * @param isShoot  是否为中奖小球
     */
    public FlyBall(Context context, int drawable, int number, boolean isShoot) {
        this(context);
        this.mDrawable = drawable;
        this.mNumber = number;
        this.mIsShoot = isShoot;
    }

    public void setDrawable(int drawable) {
        this.mDrawable = drawable;
        invalidate();
    }

    private void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mBallWidth, mBallWidth);
    }

    public void startFlyAnimation(int endY) {
        //改变小球的位置 ValueAnimator
//        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(this, "rotation", 0, 90);
        //动画监听事件，不断重绘view
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mCurrentPoint = (Point) animation.getAnimatedValue();
//                invalidate();
//            }
//        });

        //设置动画的弹跳差值器
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY",0, endY);
        translationY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5 * 1000);
        animatorSet.play(translationY);
        animatorSet.start();
        animate().rotationBy(90).setDuration(5000).start();
    }

    //绘制圆球
    private void drawCircle(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mDrawable);
        bitmapBall = ThumbnailUtils.extractThumbnail(bitmap, mBallWidth, mBallWidth);
        canvas.drawBitmap(bitmapBall, 0, 0, mPaint);
    }
}
