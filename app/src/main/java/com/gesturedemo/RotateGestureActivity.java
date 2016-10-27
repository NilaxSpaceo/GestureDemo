package com.gesturedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.gesturedemo.utils.RotationGestureDetector;

public class RotateGestureActivity extends AppCompatActivity implements RotationGestureDetector.OnRotationGestureListener {
    private RotationGestureDetector mRotationDetector;
    private ImageView ivObject;
    private float angle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_gesture);
        initControls();

    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivObject = (ImageView) findViewById(R.id.ivObject);
        mRotationDetector = new RotationGestureDetector(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRotationDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {


        Log.d("RotationGestureDetector", "Rotation: " + Float.toString(angle));
        rotate(0 - angle, 0 - rotationDetector.getAngle());
        angle = rotationDetector.getAngle();

    }

    private void rotate(Float angle, float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(angle, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        ivObject.startAnimation(rotateAnim);
    }
}
