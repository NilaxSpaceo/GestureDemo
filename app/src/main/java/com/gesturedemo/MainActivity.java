package com.gesturedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDragDemoClick(View view) {
        Intent iPanGesture =  new Intent(MainActivity.this , DragDropGestureActivity.class);
        startActivity(iPanGesture);
    }

    public void onPinchZoomClick(View view) {
        Intent iPanGesture =  new Intent(MainActivity.this , PinchZoomGestureActivity.class);
        startActivity(iPanGesture);
    }

    public void onRotateClick(View view) {
        Intent iPanGesture =  new Intent(MainActivity.this , RotateGestureActivity.class);
        startActivity(iPanGesture);
    }
}
