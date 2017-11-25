package kmitl.project.bosstanayot.runranrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConcludeActivity extends AppCompatActivity {
    TextView allsteps;
    TextView alldistance;
    TextView alltime;
    TextView totalTimeText;
    int count_step;
    float distance;
    String duration;
    String currentDateTime;
    String total_time;
    TextView cal_text;
    TextView speedText;
    String mweight;
    int hour;
    int min;
    int sec;
    String uid;
    double calorie;
    int num_weight;
    TextView timetext;
    int type;
    private Firebase hisFirebase;
    private Firebase firebaseCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclude);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseCal =  new Firebase("https://runranrun-a104c.firebaseio.com/profile/");
        hisFirebase = new Firebase("https://runranrun-a104c.firebaseio.com/").child("history");
        Bundle bundle = getIntent().getExtras();
        uid = setUid(user);
        speedText = findViewById(R.id.speedtext);
        allsteps = findViewById(R.id.allsteps);
        alldistance = findViewById(R.id.alldistance);
        alltime = findViewById(R.id.alltime);
        timetext = findViewById(R.id.timeText);
        cal_text = findViewById(R.id.caltext);
        totalTimeText = findViewById(R.id.totalTimeText);
        if (bundle != null) {
            type = bundle.getInt("type");
            if(type == 1) {
                currentDateTime = bundle.getString("time");
            }
            sec = bundle.getInt("sec");
            totalTimeText.setText(toTextTime(sec-1));
            count_step = bundle.getInt("count_step");
            distance = bundle.getFloat("distance");
            duration = bundle.getString("duration");
        }

        calorie = getCal();
        Log.d("mid", String.valueOf(calorie));
        speedText.setText(String.valueOf(getAvgSp()));
        allsteps.setText(String.valueOf(count_step));
        alldistance.setText(String.valueOf(distance));
        alltime.setText(String.valueOf(duration));
        cal_text.setText(String.valueOf(calorie));
        Log.d("after", String.valueOf(calorie));
        getDateTime();
        sendHistory();
        Log.d("last", String.valueOf(calorie));
    }
    private void  sendHistory(){


    }
    private String getAvgSp() {
        double avgSp = distance/(sec*0.00027778);
        String txt_Avg = String.format("%.2f", avgSp);
        return txt_Avg;
    }

    public String setUid(FirebaseUser user){
        for(UserInfo profile : user.getProviderData()) {
            // check if the provider id matches "facebook.com"
            if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                uid = AccessToken.getCurrentAccessToken().getUserId(); //use for get public profile on facebook;
            }
        }
        return uid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }
    public double getCal(){
        final double[] calll = new double[1];
        Log.d("first callll", String.valueOf(calll[0]));
        Query query = firebaseCal.child(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    mweight =newPost.get("weight").toString();
                Log.d("second callll", String.valueOf(calll[0]));
                    if(mweight != "weight"){
                        num_weight = Integer.parseInt(mweight);
                        calorie = num_weight*distance*1.035;
                        calll[0] = calorie;
                        Log.d("after set callll", String.valueOf(calll[0]));
                        CalModel calModel = new CalModel();
                        calModel.setCal((int)calorie);
                        Log.d("after callmodel callll", String.valueOf(calModel.getCal()));
                        cal_text.setText(String.valueOf((int)calorie));
                        if(type == 0){
                            Map<String, Object> his = new HashMap<String, Object>();
                            //his.put("uid", uid);
                            his.put("step", count_step);
                            his.put("sec", sec);
                            his.put("calories", (int)calorie);
                            his.put("duration", duration);
                            his.put("time", currentDateTime);
                            his.put("distance", distance);
                            hisFirebase.child(uid).push().setValue(his);
                        }
                    }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Log.d("last callll", String.valueOf(calll[0]));
        return calll[0];
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    public void onShare(MenuItem menuItem) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra("cal", cal_text.getText());
        intent.putExtra("duration", duration);//int
        startActivity(intent);
    }

    public void onDone(MenuItem item) {
        super.onBackPressed();
    }
    public void getDateTime(){

        if(type == 0){
            currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
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
    public int setCal(int weight, float distance){
        Double cal = weight*distance*1.035;
        int callories = cal.intValue();
        return callories;
    }
}
