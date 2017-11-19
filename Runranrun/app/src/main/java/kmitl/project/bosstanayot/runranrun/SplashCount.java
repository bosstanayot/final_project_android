package kmitl.project.bosstanayot.runranrun;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class SplashCount extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;
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

                //num.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //num.setText("done");
                //num.setText(String.valueOf(count));
                Intent intent = new Intent(SplashCount.this, RunActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
        /*handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashCount.this, RunActivity.class);
                startActivity(intent);
                finish();
            }
        };*/
    }

   /* public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }*/

    @Override
    public void onBackPressed() {

    }
}