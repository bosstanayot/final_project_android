package kmitl.project.bosstanayot.runranrun;

import android.content.Context;
import android.webkit.WebView;

/**
 * Created by barjord on 11/12/2017 AD.
 */

public class GifWebView extends WebView {

    public GifWebView(Context context, String path) {
        super(context);

        loadUrl(path);
    }
}