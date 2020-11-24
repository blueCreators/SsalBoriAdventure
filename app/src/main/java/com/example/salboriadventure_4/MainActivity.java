package com.example.salboriadventure_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView cat, building, bus, person, tree;
    //ViewGroup view_root;

    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cat = (ImageView)findViewById(R.id.cat);
        building = (ImageView)findViewById(R.id.building);
        bus = (ImageView)findViewById(R.id.bus);
        person = (ImageView)findViewById(R.id.person);
        tree = (ImageView)findViewById(R.id.tree);

        //view_root = (ViewGroup)findViewById(R.id.view_root);

        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams();

        ChoiceTouchListener listener = new ChoiceTouchListener();

        //building.setLayoutParams(layoutParams);
        building.setOnTouchListener(listener);
        //bus.setLayoutParams(layoutParams);
        bus.setOnTouchListener(listener);
        cat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "고양이 발견!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        person.setOnTouchListener(listener);
        tree.setOnTouchListener(listener);
    }

    private final class ChoiceTouchListener implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent event){
            final int x = (int)event.getRawX();
            final int y = (int)event.getRawY();

            switch(event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    xDelta = x - lParams.leftMargin;
                    yDelta = y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
                    layoutParams.leftMargin = x - xDelta;
                    layoutParams.topMargin = y - yDelta;
                    //layoutParams.rightMargin = -250;
                    //layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            //view_root.invalidate();
            return true;
        }
    }
}