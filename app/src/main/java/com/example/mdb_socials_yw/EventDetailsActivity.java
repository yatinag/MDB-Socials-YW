package com.example.mdb_socials_yw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    DatabaseReference mDatabase;
    EventPost details;
    TextView postTitle, postDesc, postEmail, likeCount, dateVal;
    ImageView postImg;
    ImageButton likeBtn;
    boolean isLiked;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();


        dateVal = findViewById(R.id.dateDisplay);
        likeBtn = findViewById(R.id.likeBtn);

        System.out.println(isLiked);
        final String uiD = getIntent().getStringExtra("post_uid");
        String title = getIntent().getStringExtra("post_title");
        String desc =  getIntent().getStringExtra("post_desc");
        String img = getIntent().getStringExtra("post_img");
        String email = getIntent().getStringExtra("post_email");
        int attendance = getIntent().getIntExtra("post_att", 0);
        String date = getIntent().getStringExtra("post_date");

        details = new EventPost(title, desc, date, email, img, attendance, uiD);

        postTitle.setText(title);
        postDesc.setText(desc);
        likeCount.setText(attendance + " Interested");
        postEmail.setText(email);
        dateVal.setText(date);
        dateVal.setPaintFlags(dateVal.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(uiD);
                incrementAttendance(uiD);
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

    private String incrementAttendance(String id) {
        System.out.println("sending increment to database");
        DatabaseReference ref = mDatabase;

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                System.out.println(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        String attendance = mDatabase.child("posts").child(id).child("attendance").getValue();
        return id;
    }


}
