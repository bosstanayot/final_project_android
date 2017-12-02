package kmitl.project.bosstanayot.runranrun.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.markushi.ui.CircleButton;
import kmitl.project.bosstanayot.runranrun.R;
import kmitl.project.bosstanayot.runranrun.View.SplashCount;

public class Tab1run  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1run, container, false);
        CircleButton start =  rootView.findViewById(R.id.button);
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

