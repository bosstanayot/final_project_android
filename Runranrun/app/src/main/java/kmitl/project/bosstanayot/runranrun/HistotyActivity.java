package kmitl.project.bosstanayot.runranrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HistotyActivity extends AppCompatActivity {
    private Firebase hisFirebase;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<HistoryModel> listItems;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoty);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Tab2profile tab2profile = new Tab2profile();
        uid = tab2profile.getUID(user);
        hisFirebase = new Firebase("https://runranrun-a104c.firebaseio.com/").child("history");
        recyclerView = findViewById(R.id.his_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        queryData();

    }

    private void queryData() {

        Query query = hisFirebase.child(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    HistoryModel listItem = new HistoryModel(
                            newPost.get("time").toString(),newPost.get("calories").toString(),newPost.get("distance").toString(),newPost.get("duration").toString()
                            ,newPost.get("step").toString(),newPost.get("sec").toString()
                    );
                    listItems.add(0,listItem);

                adapter = new HistoryAdapter(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        /**query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    HistoryModel post = postSnapshot.getValue(HistoryModel.class);
                    HistoryModel listItem = new HistoryModel(
                            post.getTime().toString(),post.getCalories().toString(),post.getDistance().toString(),post.getDuration().toString()
                    );
                    listItems.add(listItem);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });**/

    }
}
