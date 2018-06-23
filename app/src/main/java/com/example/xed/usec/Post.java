package com.example.xed.usec;

public class Post {

    private String title;
    private String description;
    private String image;
    private String entry_id;
    private String username;
    private String profileimage;

    public Post(){
    }

    public Post(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Post(String entry_id, String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.entry_id = entry_id;
    }

    public Post(String username, String profileimage) {
        this.username = username;
        this.profileimage =profileimage;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
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
}
