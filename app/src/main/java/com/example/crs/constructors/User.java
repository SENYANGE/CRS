package com.example.crs.constructors;

public class User {
    String User_Id,email,name;
    String lati,longi;
    String image,status,tag,phone;

    public User() {
    }

    public User(String user_Id, String email,String phone, String name,String lati, String longi,String image,String status,String tag) {
        User_Id = user_Id;
        this.email = email;
        this.name = name;
        this.lati = lati;
        this.longi = longi;
        this.status=status;
        this.image=image;
        this.tag=tag;
        this.phone=phone;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
