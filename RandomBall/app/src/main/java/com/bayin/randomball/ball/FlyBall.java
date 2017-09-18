package com.bayin.randomball.ball;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

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
    private float offsetDegree = 0;//初始化小球偏移角度
    private long normalTime = 3000;
    private long shootedTime = 1000;

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
        mBallWidth = (int) (mScreenWidth * 0.12);
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
    public FlyBall(Context context, int drawable, int number, float degree, boolean isShoot) {
        this(context);
        this.mDrawable = drawable;
        this.mNumber = number;
        this.mIsShoot = isShoot;
        this.offsetDegree = degree;
    }

    private FlyBall getView(){
        return this;
    }

    public void setDrawable(int drawable) {
        this.mDrawable = drawable;
        invalidate();
    }

    private void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        setBackgroundColor(Color.BLUE);
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
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", 0, endY);
        translationY.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        if (mIsShoot){
            animatorSet.setDuration(shootedTime);
            animatorSet.addListener(new Animator.AnimatorListener() {
                final long duration = 800;
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator rotation = ObjectAnimator.ofFloat(getView(), "rotation", getRotation(), getRotation() - 25);
                    rotation.setDuration(duration);
                    rotation.setRepeatCount(1);
                    rotation.setRepeatMode(ValueAnimator.REVERSE);

                    ObjectAnimator x = ObjectAnimator.ofFloat(getView(), "translationX", getTranslationX(), getTranslationX() - 20);
                    x.setDuration(duration);
                    x.setRepeatCount(1);
                    x.setRepeatMode(ValueAnimator.REVERSE);

                    AnimatorSet set = new AnimatorSet();
                    set.play(rotation).with(x);
                    set.setInterpolator(new DecelerateInterpolator());
                    set.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        else
            animatorSet.setDuration(normalTime);
        animatorSet.play(translationY);
        animatorSet.start();
        if (offsetDegree < 0) {
            animate().rotationBy(40).setDuration(1000).start();
        } else {
            animate().rotationBy(-40).setDuration(1000).start();
        }
    }

    //绘制圆球
    private void drawCircle(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mDrawable);
        bitmapBall = ThumbnailUtils.extractThumbnail(bitmap, mBallWidth, mBallWidth);
        canvas.save();
        canvas.rotate(offsetDegree);
        canvas.restore();
        canvas.drawBitmap(bitmapBall, 0, 0, mPaint);
    }
}
