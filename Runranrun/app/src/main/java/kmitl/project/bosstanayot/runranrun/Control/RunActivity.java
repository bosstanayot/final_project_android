package kmitl.project.bosstanayot.runranrun.Control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.hoang8f.widget.FButton;
import kmitl.project.bosstanayot.runranrun.R;

public class RunActivity extends AppCompatActivity implements SensorEventListener {
    boolean running = true;
    private SensorManager mSensorManager;
    private android.hardware.Sensor mSensor;
    TextView step_num, dis_num, kmperhour;
    int count_step, hours = 0, minutes = 0, secs = 0, first_step, seconds = 0;
    FButton start, cancel;
    String time;
    TextView timenum, speedtext;
    float first_distance;
    Handler handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
        count_step = 0;
        first_distance = 0;
        first_step = 0;
        step_num = findViewById(R.id.stepnum);
        dis_num = findViewById(R.id.disnum);
        start =  findViewById(R.id.pausebutton);
        cancel = findViewById(R.id.cancel);
        timenum = findViewById(R.id.timenum);
        kmperhour = findViewById(R.id.steppermin);
        speedtext = findViewById(R.id.speed);
        cancel.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin));
        start.setButtonColor(getResources().getColor(R.color.fbutton_color_sun_flower));
        handler = new Handler();
        runTimer();
    }
    private void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                hours = seconds/3600;
                minutes = (seconds%3600)/60;
                secs = seconds%60;
                if(running){
                    int spm = (count_step-first_step)*(60);
                    float speed = (getDistanceRun(count_step)-first_distance)*3600;
                    String kmph = String.format("%.2f",speed);
                    speedtext.setText(String.valueOf(kmph));
                    kmperhour.setText(String.valueOf(spm));
                    first_distance = getDistanceRun(count_step);
                    first_step = count_step;
                    time = String.format("%02d:%02d:%02d",hours,minutes,secs);
                    timenum.setText(String.valueOf(time));
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(running){
            Sensor sensor = sensorEvent.sensor;
            if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                count_step++;
            }
            step_num.setText(String.valueOf(count_step));
            String stepsdis = String.format("%.02f", getDistanceRun(count_step));
            dis_num.setText(String.valueOf(stepsdis));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    public float getDistanceRun(long steps){
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }
    public void start(View view) {
        step_num.setText(String.valueOf(count_step));
        if(running == true ){
            running = false;
            start.setText("Unpause");
            cancel.setVisibility(View.VISIBLE);
        }else{
            running = true;
            start.setText("Pause");
            cancel.setVisibility(View.GONE);
        }
    }

    public void cancel(View view) {
        running = false;
        start.setText("Start");
        cancel.setVisibility(View.GONE);
        Intent intent = new Intent(this, ConcludeActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("count_step", count_step);//int
        intent.putExtra("distance", getDistanceRun(count_step));//float
        intent.putExtra("duration", time);//String
        intent.putExtra("sec", seconds);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RunActivity.this);
        builder.setMessage("Do you want to leave running?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               RunActivity.super.onBackPressed();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
