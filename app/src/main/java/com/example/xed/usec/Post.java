package com.example.xed.usec;

import java.util.Date;

public class Post {

    private String title, description, image;
    private Date timestamp;

    public Post(){
    }

    public Post(String title, String description, String image, Date timestamp) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
