package com.example.mdb_socials_yw;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mdb_socials_yw.Objects.EventPost;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<EventPost> postsList;
    private Context mContext;
    RequestOptions option;

    Adapter(Context context, ArrayList<EventPost> pokeNames){
        this.layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.postsList = pokeNames;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_card, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String title = postsList.get(i).getTitle();
        String img = postsList.get(i).getImg();
        String desc = postsList.get(i).getDescription();
        String email = postsList.get(i).getEmail();
        int likeCount = postsList.get(i).getAttendance();

        viewHolder.postTitle.setText(title);
        viewHolder.postDesc.setText(desc);
        viewHolder.postEmail.setText(email);
        viewHolder.likeCount.setText(likeCount + " Interested");


        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+postsList.get(i).getuID());

        // ImageView in your Activity

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        System.out.println("pictures/"+postsList.get(i).getuID());
        Glide.with(mContext)
                .load(storageReference)
                .into(viewHolder.postImg);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView postTitle, postDesc, postEmail, likeCount;
        ImageView postImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventDetailsActivity.class);
                    intent.putExtra("post_title", postsList.get(getAdapterPosition()).getTitle());
                    intent.putExtra("post_desc", postsList.get(getAdapterPosition()).getDescription());
                    intent.putExtra("post_email", postsList.get(getAdapterPosition()).getEmail());
                    intent.putExtra("post_img", postsList.get(getAdapterPosition()).getImg());
                    intent.putExtra("post_att", postsList.get(getAdapterPosition()).getAttendance());
                    intent.putExtra("post_uid", postsList.get(getAdapterPosition()).getuID());
                    v.getContext().startActivity(intent);
                }
            });
            postTitle = itemView.findViewById(R.id.eventTitle);
            postDesc = itemView.findViewById(R.id.eventDesc);
            likeCount = itemView.findViewById(R.id.likeText);
            postEmail = itemView.findViewById(R.id.eventPersonEmail);
            postImg = itemView.findViewById(R.id.eventImg);
        }
    }
}