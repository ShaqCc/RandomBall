package com.bayin.randomball.ball;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/9/18.
 ****************************************/

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //fraction 与时间有关的系数，该值由差值器计算得出，由ValueAnimator调用 animateValue
        Point startPoint = (Point)startValue;
        Point endPoint = (Point)endValue;
        float x = startPoint.x + fraction*(endPoint.x - startPoint.x);
        float y = startPoint.y + fraction*(endPoint.y - startPoint.y);
        return new Point((int)x, (int)y);
    }
}
