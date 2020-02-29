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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<EventPost> postsList;

    Adapter(Context context, ArrayList<EventPost> pokeNames){
        this.layoutInflater = LayoutInflater.from(context);
        this.postsList = pokeNames;
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
        viewHolder.likeCount.setText(likeCount + " Likes");
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
                    v.getContext().startActivity(intent);
                }
            });
            postTitle = itemView.findViewById(R.id.eventTitle);
            postDesc = itemView.findViewById(R.id.eventDesc);
            likeCount = itemView.findViewById(R.id.likeText);
            postEmail = itemView.findViewById(R.id.eventPersonEmail);
        }
    }
}