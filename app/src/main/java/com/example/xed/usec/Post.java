package com.example.xed.usec;

public class Post {

    private String title;
    private String description;
    private String image;
    private String entry_id;
    String user_id;
    private String profileImage;
    private String username;

    public Post(String username, String profileImage) {
        this.profileImage = profileImage;
        this.username = username;
    }

    public Post(){
    }

    public Post(String entry_id, String title, String description, String image, String user_id) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.entry_id = entry_id;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        }
}

