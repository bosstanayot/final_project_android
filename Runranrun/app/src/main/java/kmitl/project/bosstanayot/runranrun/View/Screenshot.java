package kmitl.project.bosstanayot.runranrun.View;

import android.graphics.Bitmap;
import android.view.View;


public class Screenshot {
    public static Bitmap takescreenshot(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());

        v.setDrawingCacheEnabled(false);

        return b;
    }

    public static Bitmap takescreenshotOfRottView(View v){
        return takescreenshot(v.getRootView());
    }
}
