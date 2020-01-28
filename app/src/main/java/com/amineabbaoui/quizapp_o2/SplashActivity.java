package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar=(ProgressBar)findViewById(R.id.progressBarSplash);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(3000,3) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*100/(3000/3));

            }

            @Override
            public void onFinish() {
                i++;
                mProgressBar.setProgress(100);
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                //Intent intent1=new Intent(SplashActivity.this,ListActivity.class);
                Intent intent1=new Intent(SplashActivity.this,CameraActivity.class);
                startActivity(intent );
            }
        };
        mCountDownTimer.start();
    }
}
