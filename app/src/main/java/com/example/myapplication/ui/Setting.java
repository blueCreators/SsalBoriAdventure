package com.example.myapplication.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MyService;
import com.example.myapplication.R;

public class  Setting extends AppCompatActivity {
    TextView btn1;
    MediaPlayer m;
    SeekBar s;
    MyService ms;
    boolean mbound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);

        btn1 = (TextView) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        s = (SeekBar)findViewById(R.id.backSoundSeekBar);
        s.setThumbOffset(50);
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m.setVolume(s.getProgress(),s.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                m.setVolume(s.getProgress(),s.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                m.setVolume(s.getProgress(),s.getProgress());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mbound)
        {
            unbindService(conn);
            mbound = false;
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            ms = ((MyService.MyBinder)binder).getService();
            mbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ms = null;
            mbound = false;
        }
    };
}


