package kmitl.project.bosstanayot.runranrun;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class ConcludeActivity extends AppCompatActivity {
    TextView allsteps;
    TextView alldistance;
    TextView alltime;
    TextView totalTimeText;
    int count_step;
    float distance;
    String time;
    String currentDateTime;
    String total_time;
    TextView cal_text;
    int hour;
    int min;
    int sec;
    int weight;
    TextView timetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclude);
        Bundle bundle = getIntent().getExtras();
        allsteps = findViewById(R.id.allsteps);
        alldistance = findViewById(R.id.alldistance);
        alltime = findViewById(R.id.alltime);
        timetext = findViewById(R.id.timeText);
        cal_text = findViewById(R.id.caltext);
        totalTimeText = findViewById(R.id.totalTimeText);
        if (bundle != null) {
            count_step = bundle.getInt("count_step");
             distance = bundle.getFloat("distance");
             time = bundle.getString("time");
             sec = bundle.getInt("sec");
             totalTimeText.setText(toTextTime(sec-1));
        }
        Tab2profile tab2profile = new Tab2profile();
        weight = tab2profile.num_weight;
        cal_text.setText(String.valueOf(setCal(weight,distance)));
        allsteps.setText(String.valueOf(count_step));
        alldistance.setText(String.valueOf(distance));
        alltime.setText(String.valueOf(time));
        getDateTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
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
        RelativeLayout imageView;
        //imageView = findViewById(R.id.imglayout);
        //Bitmap b = Screenshot.takescreenshot(imageView);
        //saveBitmap(b);
        File imagePath = new File(this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, "kmitl.project.bosstanayot.runranrun.fileprovider", newFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));

        }
    }

    /**private Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);
        return bitmap;
    }**/

    private void saveBitmap(Bitmap bitmap) {
        // save bitmap to cache directory
        try {
            File cachePath = new File(this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDone(MenuItem item) {
        super.onBackPressed();
    }
    public void getDateTime(){
        currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
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
    public int setCal(int weight, float duration){
        Double cal = weight*duration*1.035;
        int callorie = cal.intValue();
        return callorie;
    }
}
