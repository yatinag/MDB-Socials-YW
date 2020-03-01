package com.example.mdb_socials_yw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mdb_socials_yw.Objects.EventPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EventDetailsActivity extends AppCompatActivity {
    Button signoutBtn;
    Button btnNewEvent;
    DatabaseReference databaseReference;
    EventPost details;
    TextView postTitle, postDesc, postEmail, likeCount;
    ImageView postImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        signoutBtn = findViewById(R.id.signoutBtn);
        btnNewEvent = findViewById(R.id.btnNewEvent);
        postTitle = findViewById(R.id.eventTitle);
        postDesc = findViewById(R.id.eventDesc);
        likeCount = findViewById(R.id.likeText);
        postEmail = findViewById(R.id.eventPersonEmail);
        postImg = findViewById(R.id.eventImg);

        String uiD = getIntent().getStringExtra("post_uid");
        String title = getIntent().getStringExtra("post_title");
        String desc =  getIntent().getStringExtra("post_desc");
        String img = getIntent().getStringExtra("post_img");
        String email = getIntent().getStringExtra("post_email");
        String attendance = getIntent().getStringExtra("post_att");

        postTitle.setText(title);
        postDesc.setText(desc);
        likeCount.setText(attendance);
        postEmail.setText(email);
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

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    if(ds.getKey().equals(uiD)) {
//                        String id = ds.getKey();
//                        String title = (String) ds.child("title").getValue();
//                        String desc = (String) ds.child("description").getValue();
//                        String email = (String) ds.child("email").getValue();
//                        long attendance = (Long) ds.child("attendance").getValue();
//                        details.setTitle(title);
//                        details.setEmail(email);
//                        details.setDescription(desc);
//                        details.setAttendance((int) attendance);
//                        details.setImg(uiD + "jpg");
//                        details.setuID(id);
//                    }
//                }
//            }
//        });

        postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setAttendance(details.getAttendance() + 1);
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
