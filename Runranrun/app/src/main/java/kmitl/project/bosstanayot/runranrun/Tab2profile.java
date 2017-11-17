package kmitl.project.bosstanayot.runranrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * Created by barjord on 11/8/2017 AD.
 */

public class Tab2profile extends Fragment {
    private TextView nameTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2profile, container, false);
        nameTextView = rootView.findViewById(R.id.nameTextView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            nameTextView.setText(name);
        }else{
            goLoginScreen();
        }
        return rootView;
    }
    private void goLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

