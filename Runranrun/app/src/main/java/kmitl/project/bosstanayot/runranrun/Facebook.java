package kmitl.project.bosstanayot.runranrun;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by barjord on 11/16/2017 AD.
 */

public class Facebook extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
