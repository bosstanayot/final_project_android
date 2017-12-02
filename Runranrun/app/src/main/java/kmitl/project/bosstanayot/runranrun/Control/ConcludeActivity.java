package kmitl.project.bosstanayot.runranrun.Control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import kmitl.project.bosstanayot.runranrun.Model.ProfileInfo;
import kmitl.project.bosstanayot.runranrun.R;

public class ConcludeActivity extends AppCompatActivity {
    TextView allsteps, alldistance, alltime, totalTimeText, timetext, cal_text, speedText;
    int count_step, hour, min, sec, second, num_weight, type;
    float distance;
    String duration,currentDateTime,total_time, uid;
    double calorie;
    private Firebase hisFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclude);
        hisFirebase = new Firebase("https://runranrun-a104c.firebaseio.com/").child("history");
        uid = ProfileInfo.uid;
        speedText = findViewById(R.id.speedtext);
        allsteps = findViewById(R.id.allsteps);
        alldistance = findViewById(R.id.alldistance);
        alltime = findViewById(R.id.alltime);
        timetext = findViewById(R.id.timeText);
        cal_text = findViewById(R.id.caltext);
        totalTimeText = findViewById(R.id.totalTimeText);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
            if(type == 1) {
                currentDateTime = bundle.getString("time");
                calorie = bundle.getInt("cal");
                cal_text.setText(String.valueOf((int)calorie));
            }
            second = bundle.getInt("sec");
            totalTimeText.setText(toTextTime(second-1));
            count_step = bundle.getInt("count_step");
            distance = bundle.getFloat("distance");
            duration = bundle.getString("duration");
        }
        getDateTime();
        if(type == 0){
            getCal();
        }
        speedText.setText(String.valueOf(getAvgSp()));
        allsteps.setText(String.valueOf(count_step));
        alldistance.setText(String.valueOf(distance));
        alltime.setText(String.valueOf(duration));
    }
    private void  sendHistory(){
        if(type == 0){
            Map<String, Object> his = new HashMap();
            his.put("step", count_step);
            his.put("sec", second);
            his.put("calories", (int)calorie);
            his.put("duration", duration);
            his.put("time", currentDateTime);
            his.put("distance", distance);
            hisFirebase.child(uid).push().setValue(his);
        }

    }
    private String getAvgSp() {
        double avgSp = distance/(second*0.00027778);
        String txt_Avg = String.format("%.2f", avgSp);
        return txt_Avg;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }
    public void getCal(){
        num_weight = ProfileInfo.weight;
        if(num_weight == 0){
            num_weight = 55;
        }
        Toast.makeText(this, String.valueOf(num_weight),Toast.LENGTH_SHORT ).show();
        calorie = setCal((double)num_weight, (double)distance);
        cal_text.setText(String.valueOf((int)calorie));
        sendHistory();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    public void onShare(MenuItem menuItem) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra("cal", calorie);
        intent.putExtra("duration", duration);//int
        startActivity(intent);
    }

    public void onDone(MenuItem item) {
        super.onBackPressed();
    }
    public void getDateTime(){

        if(type == 0){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentDateTime = df.format(c.getTime());
        }
        timetext.setText(currentDateTime);
    }
    public String toTextTime(int seconds){
        hour = seconds/3600;
        min = (seconds%3600)/60;
        sec = seconds%60;
        if(hour == 0 && min != 0){
            total_time = String.format("%d min %d sec",min,sec);
        }else if(hour == 0 && min == 0){
            total_time = String.format("%d sec",sec);
        }else{
            total_time = String.format("%d hour %d min %d sec",hour,min,sec);
        }
        return total_time;
    }
    public double setCal(double weight, double distance){
       double incal = (double) weight*(double)distance*(double)1.035;
       return incal;
    }
}
