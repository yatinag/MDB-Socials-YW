package com.example.mdb_socials_yw.Objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPost {
    private String title = "no title";
    private String description = "no description";
    private String email;
    private String img;
    private int attendance = 0;
    private long date;
    private String uID;

    public EventPost() {
    }

    @Override
    public String toString() {
        return "EventPost{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                ", img='" + img + '\'' +
                ", attendance=" + attendance +
                ", uID='" + uID + '\'' +
                '}';
    }

    public EventPost(String title, String description, long date, String email, String img, int attendance, String uID) {
        this.title = title;
        this.description = description;
        this.email = email;
        this.img = img;
        this.attendance = attendance;
        this.date = date;
        this.uID = uID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public long getDate() {
        return date;
    }

    public String getDateString() {
        Date stringDate = new Date(date);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy");
        String dateText = df2.format(date);
        return dateText;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

}
