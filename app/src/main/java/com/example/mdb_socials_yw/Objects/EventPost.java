package com.example.mdb_socials_yw.Objects;

public class EventPost {
    private String title;
    private String description;
    private String email;
    private String img;
    private int attendance;
    private String uID;

    public EventPost() {
    }

    @Override
    public String toString() {
        return "EventPost{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", img='" + img + '\'' +
                ", attendance=" + attendance +
                ", uID='" + uID + '\'' +
                '}';
    }

    public EventPost(String title, String description, String email, String img, int attendance, String uID) {
        this.title = title;
        this.description = description;
        this.email = email;
        this.img = img;
        this.attendance = attendance;
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

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

}
