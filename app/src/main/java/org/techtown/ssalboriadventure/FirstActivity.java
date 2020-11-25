package org.techtown.ssalboriadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        MapActivity Start = (MapActivity)MapActivity.Start;

        Start.finish();

    }
}