package com.photospot.photospot;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class LoginActivity extends Activity {

    AnimationDrawable backgroundAnimation;
    Drawable backgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //Hiding statusbar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Getting the background drawable (gradient)
        backgroundDrawable = findViewById(R.id.root_layout).getBackground();

        //Making the background gradient animate
        backgroundAnimation = (AnimationDrawable) backgroundDrawable;
        backgroundAnimation.setEnterFadeDuration(10);
        backgroundAnimation.setExitFadeDuration(4000);
        backgroundAnimation.start();
    }
}
