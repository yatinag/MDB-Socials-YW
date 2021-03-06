package com.example.mdb_socials_yw;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mdb_socials_yw.Objects.EventPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewEventActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1023;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    Button submitBtn;
    Button uploadBtn;
    EditText userCaption;
    EditText userTitle;
    String picturePath;
    ImageView imgImage;
    CalendarView date;

    String title = "no title";
    String dateVal;
    String description = "no description";
    String  email = "no@email.com";
    String img;
    int attendance = 0;
    String uID = "0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        submitBtn = findViewById(R.id.submitBtn);
        uploadBtn = findViewById(R.id.uploadBtn);
        userCaption = findViewById(R.id.userCaption);
        userTitle = findViewById(R.id.userTitle);
        imgImage = findViewById(R.id.imgImage);
        date = findViewById(R.id.calendarDate);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //find img url and pass into mainactivity
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);

            }
        });

        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateVal = month + "/" + dayOfMonth + "/" + year;
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getBack  = new Intent(NewEventActivity.this, MainActivity.class);
                if(TextUtils.isEmpty(picturePath)) {
                    Toast toast = Toast.makeText( NewEventActivity.this, "Please select an image", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(dateVal == null) {
                    Toast toast = Toast.makeText( NewEventActivity.this, "Please select a date", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                title = (userTitle.getText().toString());
                description = (userCaption.getText().toString());
                FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    email = user.getEmail();
                    uID = user.getUid();
                }
                Uri file = Uri.fromFile(new File(picturePath));
                String picID = writeNewEventPost(title, description, dateVal, img, uID, email);
                StorageReference picsRef = mStorage.child(String.format("pictures/%s.jpg",picID));
                picsRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                System.out.println("successfully uploaded image");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast toast = Toast.makeText( NewEventActivity.this, "[server error] failure to upload image", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                System.out.println("Uploaded image");

                //addEvent(description);
                startActivity(getBack);
            }
        });
    }

    private String writeNewEventPost(String title, String description, String dateVal, String img, String userId, String email) {
        System.out.println("sending data to database");
        String id = mDatabase.push().getKey();
        EventPost post = new EventPost(title, description, dateVal, email, img, 0, id);
        System.out.println(post);

        mDatabase.child("posts").child(id).setValue(post);
        return id;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                }

            }
            cursor.close();
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    imgImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}