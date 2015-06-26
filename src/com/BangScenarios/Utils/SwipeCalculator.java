package com.BangScenarios.Utils;

import android.view.MotionEvent;

/**
 * Created by Edin on 21.6.2015.
 */
public class SwipeCalculator {
    private static float startSwipeX;

    public static Motion getSwipeMotion(MotionEvent touchEvent, int swipeDistance) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startSwipeX = touchEvent.getX();
                break;

            case MotionEvent.ACTION_UP:
                float endSwipeX = touchEvent.getX();

                if (startSwipeX + swipeDistance < endSwipeX) {
                    return Motion.LEFT;
                } else if (startSwipeX - swipeDistance > endSwipeX) {
                    return Motion.RIGHT;
                }
                break;
        }
        return Motion.NO_MOTION;
    }
}
