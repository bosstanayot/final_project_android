package kmitl.project.bosstanayot.runranrun;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;

/**
 * Created by barjord on 11/8/2017 AD.
 */

public class Tab1run  extends Fragment {
    TextView stepnum;
    //public static Tab1run newInstance() {
    //    return new Tab1run();
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1run, container, false);
        Button start = (Button) rootView.findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SplashCount.class);
                startActivity(intent);
            }
        });
        return rootView;


    }


}

