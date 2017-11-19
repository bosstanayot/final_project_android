package kmitl.project.bosstanayot.runranrun;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RunActivity extends AppCompatActivity implements SensorEventListener {
    boolean running = true;
    private SensorManager mSensorManager;
    private android.hardware.Sensor mSensor;
    int first_check;
    TextView step_num;
    TextView dis_num;
    TextView kmperhour;
    int count_step;
    Button start;
    Button cancel;
    String time;
    int hours = 0;
    int minutes = 0;
    int secs = 0;
    TextView timenum;
    TextView speedtext;
    int first_step;
    float first_distance;
    Handler handler = null;
    int seconds = 0;

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
                    int spm = (count_step-first_step)*(60-secs); // Not straight
                    float speed = (getDistanceRun(count_step)-first_distance)*3600; //Not straight
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
            /*float[] values = sensorEvent.values;
            int value = -1;

            if (values.length > 0) {
                value = (int) values[0];
            }*/


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
    //function to determine the distance run in kilometers using average step length for men and number of steps
    // 78 for men 70 for women
    public float getDistanceRun(long steps){
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }

    public void start(View view) {
        step_num.setText(String.valueOf(count_step));
        if(running == true ){
            running = false;
            start.setText("Back");
            cancel.setVisibility(View.VISIBLE);
            Toast.makeText(RunActivity.this,String.valueOf(running),Toast.LENGTH_SHORT).show();

        }else{
            running = true;
            start.setText("Pause");
            cancel.setVisibility(View.GONE);
            Toast.makeText(RunActivity.this,String.valueOf(running),Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        running = false;
        start.setText("Start");
        cancel.setVisibility(View.GONE);
        Intent intent = new Intent(this, ConcludeActivity.class);
        intent.putExtra("count_step", count_step);//int
        intent.putExtra("distance", getDistanceRun(count_step));//float
        intent.putExtra("time", time);//String
        intent.putExtra("sec", seconds);
        startActivity(intent);
        finish();
        //count_step = 0;
        //step_num.setText("0");
        //dis_num.setText("0.00");
        //seconds = 0;
        //timenum.setText(String.valueOf("00:00:00"));
        //Toast.makeText(RunActivity.this,String.valueOf(running),Toast.LENGTH_SHORT).show();

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
