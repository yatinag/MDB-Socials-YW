package com.example.mdb_socials_yw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mdb_socials_yw.Objects.EventPost;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button signoutBtn;
    Button btnNewEvent;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<EventPost> listPosts = new ArrayList<>();

        signoutBtn = findViewById(R.id.signoutBtn);
        btnNewEvent = findViewById(R.id.btnNewEvent);
        recyclerView = findViewById(R.id.recyclerView);

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });
        btnNewEvent.setText("Create new event");
        btnNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newevent(v);
            }
        });

        for (int i = 0; i < 3; i++) {
            EventPost temp = new EventPost("Sample Event - " + i,
                    "Event will be very fun", "boom@boom.com", "src.png", i,"123");
            listPosts.add(temp);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new Adapter(this, listPosts);
        recyclerView.setAdapter(adapter);


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
