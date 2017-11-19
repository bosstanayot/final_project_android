package kmitl.project.bosstanayot.runranrun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;
import com.vanniktech.vntnumberpickerpreference.VNTNumberPickerPreference;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by barjord on 11/8/2017 AD.
 */

public class Tab2profile extends Fragment {
    private TextView nameTextView;
    private CircleImageView display;
    String uid;
    Button logoutbtn;
    Button genderText;
    CharSequence gendername;
    String name;
    String photoUrl;
    Button number;
    String mgender;
    String mweight;
    private Firebase mFirebase;
    int  num_weight;

    private int selected = 0;
    private int buffKey = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2profile, container, false);
        nameTextView = rootView.findViewById(R.id.nameTextView);
        display = rootView.findViewById(R.id.profile_image);
        logoutbtn = rootView.findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goLoginScreen();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            for(UserInfo profile : user.getProviderData()) {
                // check if the provider id matches "facebook.com"
                if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                    uid = AccessToken.getCurrentAccessToken().getUserId(); //use for get public profile on facebook;
                }
            }

            name = user.getDisplayName();
            photoUrl = "https://graph.facebook.com/" + uid + "/picture?height=500";
            nameTextView.setText(name);
            Picasso.with(getActivity()).load(photoUrl).into(display);

        }else{
            goLoginScreen();
        }
        number = rootView.findViewById(R.id.number);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPickerDiallog();
            }
        });
        genderText = rootView.findViewById(R.id.genderText);
        genderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogButtonClick();
            }
        });
        mFirebase = new Firebase("https://runranrun-a104c.firebaseio.com/").child("profile");
        queryData();
        return rootView;
    }
    private void queryData() {
        Query query = mFirebase;

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try{
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    mgender = newPost.get("gender").toString();
                    num_weight = Integer.parseInt(newPost.get("weight").toString());
                    genderText.setText(mgender);
                    number.setText("weight: " + String.valueOf(num_weight)+"KG.");
                }catch (Exception e){
                    setProfile();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    genderText.setText(mgender);
                    number.setText(mweight);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void setProfile() {
     ProfileInfo profileInfo = new ProfileInfo(num_weight, uid);
        Map<String, Object> profile = new HashMap<String, Object>();
        profile.put(uid+"/gender", gendername);
        if(num_weight == 0){
            profile.put(uid+"/weight", "Weight");
        }else {
            profile.put(uid+"/weight", num_weight);
        }

        mFirebase.updateChildren(profile);
        //mFirebase.push().setValue(profile);
    }

    private void goLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void numberPickerDiallog(){
        final int[] weight = new int[1];
        NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMaxValue(200);
        numberPicker.setMinValue(0);
        numberPicker.setValue(num_weight);
        NumberPicker.OnValueChangeListener myValChange = new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                weight[0] = i1;
            }
        };
        numberPicker.setOnValueChangedListener(myValChange);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(numberPicker);
        builder.setTitle("Select Weight").setIcon(R.mipmap.ic_launcher);
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(weight[0] != 0) {
                    number.setText("weight: " + weight[0]+ " KG.");
                    num_weight = weight[0];
                    setProfile();
                    queryData();
                }
            }
        });
        builder.show();
    }
    // add buffer value
    private void showDialogButtonClick() {
        Log.i(TAG, "show Dialog ButtonClick");
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle("Show dialog");
        final CharSequence[] choiceList =
                {"Male", "Female" };
        builder.setSingleChoiceItems(
                choiceList,
                selected,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
//set to buffKey instead of selected
//(when cancel not save to selected)
                        buffKey = which;
                    }
                })
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //genderText.setText(choiceList[buffKey]);
                                gendername = choiceList[buffKey];
                                selected = buffKey;
                                setProfile();
                                queryData();
//set buff to selected

                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
}

