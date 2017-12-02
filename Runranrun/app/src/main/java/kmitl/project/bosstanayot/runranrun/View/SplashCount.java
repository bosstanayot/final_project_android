package kmitl.project.bosstanayot.runranrun.View;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import kmitl.project.bosstanayot.runranrun.R;
import kmitl.project.bosstanayot.runranrun.Control.RunActivity;

public class SplashCount extends Activity {
    int count = 3;
    TextView num;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_count);
        num = findViewById(R.id.num);
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                num.setText(String.valueOf(count));
                num.startAnimation(AnimationUtils.loadAnimation(SplashCount.this , R.anim.text));
                count--;
            }

            public void onFinish() {
                Intent intent = new Intent(SplashCount.this, RunActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }
}