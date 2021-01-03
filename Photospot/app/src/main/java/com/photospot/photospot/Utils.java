package com.photospot.photospot;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

public class Utils {

    // Gets a drawable and animates it
    public static void animateGradient(Drawable drawable, int duration) {
        AnimationDrawable animationDrawable;
        animationDrawable = (AnimationDrawable) drawable;
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(duration);
        animationDrawable.start();
    }

}
