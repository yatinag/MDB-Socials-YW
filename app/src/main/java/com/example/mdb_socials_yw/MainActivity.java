package com.example.mdb_socials_yw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mdb_socials_yw.Objects.EventPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button signoutBtn;
    Button btnNewEvent;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<EventPost> listPosts;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listPosts = new ArrayList<>();

        signoutBtn = findViewById(R.id.signoutBtn);
        btnNewEvent = findViewById(R.id.btnNewEvent);
        recyclerView = findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("posts");

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });
        btnNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newevent(v);
            }
        });

        //
//        for (int i = 0; i < 3; i++) {
//            EventPost temp = new EventPost("Sample Event - " + i,
//                    "Event will be very fun", "boom@boom.com", "src.png", i, "2");
//            listPosts.add(temp);
//        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    EventPost temp = new EventPost();
                    String id = (String) ds.getKey();
                    String title = (String) ds.child("title").getValue();
                    String desc = (String) ds.child("description").getValue();
                    String email = (String) ds.child("email").getValue();
                    long attendance = (Long) ds.child("attendance").getValue();
                    temp.setTitle(title);
                    temp.setEmail(email);
                    temp.setDescription(desc);
                    temp.setAttendance((int) attendance);
                    temp.setImg("src.png");
                    temp.setuID(id);
                    listPosts.add(temp);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                adapter = new Adapter(MainActivity.this, listPosts);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void logout(View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void newevent(View v) {
        startActivity(new Intent(getApplicationContext(), NewEventActivity.class));
    }

}
