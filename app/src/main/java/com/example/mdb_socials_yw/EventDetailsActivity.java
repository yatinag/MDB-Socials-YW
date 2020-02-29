package com.example.mdb_socials_yw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        String post_title = getIntent().getStringExtra("post_title");


    }
}
