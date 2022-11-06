package com.example.crs.constructors;
public class complaints_messages {

    private String image , Status , name , id;


    public complaints_messages(String image, String status, String name, String id) {
        this.image = image;
        Status = status;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public complaints_messages(String image, String status, String name) {
        this.image = image;
        this.Status = status;
        this.name = name;
    }

    public complaints_messages() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}