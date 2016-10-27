package com.gesturedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PinchZoomGestureActivity extends AppCompatActivity {

    private ImageView ivObject;
    private TextView tvEvent;

    Bitmap bitmap;
    int bmpWidth, bmpHeight;

    //Touch event related variables
    int touchState;
    final int IDLE = 0;
    final int TOUCH = 1;
    final int PINCH = 2;
    float dist0, distCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinch_zoom_gesture);
        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivObject = (ImageView) findViewById(R.id.ivObject);
        tvEvent = (TextView) findViewById(R.id.tvEvent);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        bmpWidth = bitmap.getWidth();
        bmpHeight = bitmap.getHeight();

        distCurrent = 1; //Dummy default distance
        dist0 = 1;   //Dummy default distance
        drawMatrix();

        ivObject.setOnTouchListener(MyOnTouchListener);
        touchState = IDLE;
    }


    private void drawMatrix() {
        float curScale = distCurrent / dist0;
        if (curScale < 0.1) {
            curScale = 0.1f;
        }

        Bitmap resizedBitmap;
        int newHeight = (int) (bmpHeight * curScale);
        int newWidth = (int) (bmpWidth * curScale);
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        ivObject.setImageBitmap(resizedBitmap);
    }

    View.OnTouchListener MyOnTouchListener
            = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            // TODO Auto-generated method stub

            float distx, disty;

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    //A pressed gesture has started, the motion contains the initial starting location.
                    tvEvent.setText("ACTION_DOWN");
                    touchState = TOUCH;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    //A non-primary pointer has gone down.
                    tvEvent.setText("ACTION_POINTER_DOWN");
                    touchState = PINCH;

                    //Get the distance when the second pointer touch
                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    dist0 = (float) Math.sqrt(distx * distx + disty * disty);

                    break;
                case MotionEvent.ACTION_MOVE:
                    //A change has happened during a press gesture (between ACTION_DOWN and ACTION_UP).
                    tvEvent.setText("ACTION_MOVE");

                    if (touchState == PINCH) {
                        //Get the current distance
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        distCurrent = (float) Math.sqrt(distx * distx + disty * disty);

                        drawMatrix();
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    //A pressed gesture has finished.
                    tvEvent.setText("ACTION_UP");
                    touchState = IDLE;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    //A non-primary pointer has gone up.
                    tvEvent.setText("ACTION_POINTER_UP");
                    touchState = TOUCH;
                    break;
            }

            return true;
        }

    };

}
