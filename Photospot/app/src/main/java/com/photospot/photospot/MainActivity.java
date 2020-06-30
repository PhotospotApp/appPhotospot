package com.photospot.photospot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    Go to login screen on click of bottom button in the main activity
    public void onClick(View view) {
        setContentView(R.layout.login_screen);
    }
}