package com.example.crs.constructors;

public class Messages {
    String Sender,Receiver,Message;
    String date,type,time,name_document;
    String senderImage;
    String longi,lati;
    String policeName,phone,complainant,image;
    boolean seen;

    public Messages() {
    }

    public Messages(String sender, String receiver, String message, String date, String type, String time, String name_document,String senderImage,String longi,String lati,String phone,String policeName,String complainant,boolean seen,String image) {
        Sender = sender;//complainant and police
        Receiver = receiver;//police and complainant
        Message = message;//crime detail i.e child abuse,theft etc
        this.date = date;//date crime reported
        this.type = type;//image ,pdf,text typed
        this.time = time;//time crime reported
        this.name_document = name_document;
        this.senderImage=senderImage;
        this.longi=longi;
        this.lati=lati;
        this.policeName=policeName;
        this.phone=phone;
        this.complainant=complainant;
        this.seen=seen;
        this.image=image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComplainant() {
        return complainant;
    }

    public void setComplainant(String complainant) {
        this.complainant = complainant;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName_document() {
        return name_document;
    }

    public void setName_document(String name_document) {
        this.name_document = name_document;
    }
}
