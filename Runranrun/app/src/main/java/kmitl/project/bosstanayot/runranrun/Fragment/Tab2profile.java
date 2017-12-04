package kmitl.project.bosstanayot.runranrun.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
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

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;
import kmitl.project.bosstanayot.runranrun.Control.HistotyActivity;
import kmitl.project.bosstanayot.runranrun.Model.ProfileInfo;
import kmitl.project.bosstanayot.runranrun.R;
import kmitl.project.bosstanayot.runranrun.facebook.LoginActivity;

public class Tab2profile extends Fragment {
    private TextView nameTextView;
    private CircleImageView display;
    String uid;
    FButton logoutbtn, genderText;
    CharSequence gendername;
    String name, photoUrl;
    FButton number, history;
    String mgender, mweight;
    private Firebase mFirebase;
    int  num_weight;
    private int selected = 0;
    private int buffKey = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2profile, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebase = new Firebase("https://runranrun-a104c.firebaseio.com/").child("profile");
        history = rootView.findViewById(R.id.history);
        nameTextView = rootView.findViewById(R.id.nameTextView);
        display = rootView.findViewById(R.id.profile_image);
        logoutbtn = rootView.findViewById(R.id.logoutbtn);
        genderText = rootView.findViewById(R.id.genderText);
        number = rootView.findViewById(R.id.number);
        genderText.setButtonColor(getResources().getColor(R.color.colorPrimary));
        number.setButtonColor(getResources().getColor(R.color.colorPrimary));
        history.setButtonColor(getResources().getColor(R.color.fbutton_color_sun_flower));
        logoutbtn.setButtonColor(getResources().getColor(R.color.com_facebook_blue));
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HistotyActivity.class);
                startActivity(intent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goLoginScreen();
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPickerDiallog();
            }
        });
        genderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogButtonClick();
            }
        });
        if(user != null){
            settingProfile(user);
            queryData();
        }else{
            goLoginScreen();
        }
        return rootView;
    }
    public void settingProfile(FirebaseUser user){
        name = user.getDisplayName();
        photoUrl = "https://graph.facebook.com/" + getUID(user) + "/picture?height=500";
        nameTextView.setText(name);
        Picasso.with(getActivity()).load(photoUrl).into(display);
    }
    public String  getUID(FirebaseUser user){
        for(UserInfo profile : user.getProviderData()) {
            if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                uid = AccessToken.getCurrentAccessToken().getUserId();
                ProfileInfo.uid = uid;
            }
        }
        return uid;
    }
    private void queryData() {
        Query query = mFirebase.child(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    mgender = newPost.get("gender").toString();
                    genderText.setText(mgender);
                }catch (Exception e){
                    mFirebase.child(uid).child("gender").setValue("gender");
                }
                try {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    mweight =newPost.get("weight").toString();
                    num_weight = Integer.parseInt(mweight);
                    if(num_weight != 0){
                        number.setText("weight: " + String.valueOf(num_weight)+"KG.");
                        ProfileInfo.weight = num_weight;
                    }

                }catch (Exception e){
                    mFirebase.child(uid).child("weight").setValue("weight");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void setProfile() {
        Map<String, Object> profile = new HashMap<String, Object>();
            profile.put(uid+"/gender", gendername);
            profile.put(uid+"/weight", num_weight);
            ProfileInfo.gender = gendername;
            ProfileInfo.weight = num_weight;
        mFirebase.updateChildren(profile);
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
        numberPicker.setMinValue(1);
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
        builder.setPositiveButton("cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(weight[0] != 0) {
                    number.setText("weight: " + weight[0]+ " KG.");
                    num_weight = weight[0];
                    setProfile();
                }
            }
        });
        builder.show();
    }
    private void showDialogButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Gender");
        final CharSequence[] choiceList = {"Male", "Female" };
        builder.setSingleChoiceItems(choiceList, selected,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buffKey = which;
                    }
                }).setCancelable(false).setPositiveButton("Ok",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                gendername = choiceList[buffKey];
                                genderText.setText(gendername);
                                selected = buffKey;
                                setProfile();
                            }
                        }
                ).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
}

