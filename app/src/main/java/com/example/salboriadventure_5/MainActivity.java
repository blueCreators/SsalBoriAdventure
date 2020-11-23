package com.example.salboriadventure_5;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView, countdownText;
    Button btnStart;
    CountDownTimer countDownTimer;

    private long timeLeftInMilliseconds = 300000; // 5분
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        countdownText = (TextView)findViewById(R.id.countdownText);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("민속촌을 둘러보자!");
                startStop();
            }
        });
    }

    public void startStop(){
        if(timerRunning){
            stopTimer();
        }else{
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        btnStart.setText("PAUSE");
        timerRunning = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        btnStart.setText("START");
        timerRunning = false;
    }

    public void updateTimer(){
        int minutes = (int)timeLeftInMilliseconds / 60000;
        int seconds = (int)timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = minutes + "";
        timeLeftText += ":";
        if(seconds <  10) timeLeftText += "0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);

        if(timeLeftText.equals("0:01")){
            //타이머 종료 화면
            timeFinished();
        }
    }

    public void timeFinished(){
        countdownText.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        textView.setText("민속촌 둘러보기 완료!");
    }
}