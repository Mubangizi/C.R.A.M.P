package com.example.xed.usec;

public class Post {

    private String title;
    private String description;
    private String image;
    private String post_id;
    private String user_id;
    private String profileImage;
    private String username;
    private Long timestamp;
    private Double latitude;
    private Double logitude;

    public Post(String username, String profileImage) {
        this.profileImage = profileImage;
        this.username = username;
    }

    public Post(){
    }

    public Post(String post_id, String title, String description, String image, String user_id, Long timestamp,Double latitude,Double longitude) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.post_id = post_id;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.latitude=latitude;
        this.logitude=longitude;
    }

    public Post(String post_id, String title, String description, String image, String user_id, Long timestamp) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.post_id = post_id;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLogitude() {
        return logitude;
    }

    public void setLogitude(Double logitude) {
        this.logitude = logitude;
    }
}

