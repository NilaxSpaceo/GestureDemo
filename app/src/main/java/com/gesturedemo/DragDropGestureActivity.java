package com.gesturedemo;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DragDropGestureActivity extends AppCompatActivity {

    private TextView tvDragMe;
    private ImageView ivDustBin;
    private ImageView ivDust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop_gesture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initControls();

    }

    private void initControls() {

        tvDragMe = (TextView) findViewById(R.id.tvDragMe);
        ivDustBin = (ImageView) findViewById(R.id.ivDustBin);
        ivDust = (ImageView) findViewById(R.id.ivDust);

        ivDust.setOnTouchListener(new MyTouchListener());
        ivDustBin.setOnDragListener(new MyDragListener());
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


    private class MyDragListener implements View.OnDragListener {
        int resAct = R.drawable.dust_act;
        int resNormal = R.drawable.dust_normal;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    ((ImageView) v).setImageResource(resAct);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    ((ImageView) v).setImageResource(resNormal);
                    break;
                case DragEvent.ACTION_DROP:
                    ((ImageView) v).setImageResource(resAct);
                    tvDragMe.setVisibility(View.GONE);
                    // Display toast
                    showToast("Dropped into dustbin!");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult()) { // drop succeeded
                        ((ImageView) v).setImageResource(resAct);
                    } else { // drop failed
                        final View draggedView = (View) event.getLocalState();
                        draggedView.post(new Runnable() {
                            @Override
                            public void run() {
                                draggedView.setVisibility(View.VISIBLE);
                            }
                        });
                        ((ImageView) v).setImageResource(resNormal);
                    }
                default:
                    break;
            }
            return true;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
