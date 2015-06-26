package com.BangScenarios.Utils;

import android.view.animation.TranslateAnimation;

/**
 * Created by Edin on 21.6.2015.
 */
public class Animations {

    public static TranslateAnimation getToLeft(int screenWidth) {
        TranslateAnimation leavingAnim = new TranslateAnimation(0, -screenWidth, 0, 0);
        leavingAnim.setFillEnabled(true);
        leavingAnim.setFillAfter(true);
        leavingAnim.setDuration(400);
        return leavingAnim;
    }

    public static TranslateAnimation getFromRight(int screenWidth) {
        TranslateAnimation comingAnim = new TranslateAnimation(screenWidth, 0, 0, 0);
        comingAnim.setFillEnabled(true);
        comingAnim.setFillAfter(true);
        comingAnim.setDuration(400);
        return comingAnim;
    }

    public static TranslateAnimation getToRight(int screenWidth) {
        TranslateAnimation leavingAnim = new TranslateAnimation(0, screenWidth, 0, 0);
        leavingAnim.setFillEnabled(true);
        leavingAnim.setFillAfter(true);
        leavingAnim.setDuration(400);
        return leavingAnim;
    }

    public static TranslateAnimation getFromLeft(int screenWidth) {
        TranslateAnimation comingAnim = new TranslateAnimation(-screenWidth, 0, 0, 0);
        comingAnim.setFillEnabled(true);
        comingAnim.setFillAfter(true);
        comingAnim.setDuration(400);
        return comingAnim;
    }
}
