package ar.edu.itba.paw.persistence.utils;

import ar.edu.itba.paw.model.User;

import java.util.Date;

public class ReviewRow{

    private int id;

    private int userId;

    private int aptitudeId;

    private int appointmentId;

    private String comment;

    private int quality;

    private int cleanness;

    private int price;

    private int punctuality;

    private int treatment;

    private Date date;

    private User user;




    public ReviewRow(int id, int userId, int aptitudeId,int appointmentId, String comment, int quality, int cleanness, int price, int punctuality, int treatment,
                     Date date, String username, String password, String firstname, String lastname, String email, String phone, String address, boolean verified) {
        this.id = id;
        this.userId = userId;
        this.aptitudeId = aptitudeId;
        this.appointmentId = appointmentId;
        this.comment = comment;
        this.quality = quality;
        this.cleanness = cleanness;
        this.price = price;
        this.punctuality = punctuality;
        this.treatment = treatment;
        this.date = date;
        this.user = new User(userId,username,password,firstname,lastname,email,phone,address,verified);
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public int getAptitudeId() {
        return aptitudeId;
    }

    public int getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public int getQuality() {
        return quality;
    }

    public int getCleanness() {
        return cleanness;
    }

    public int getPrice() {
        return price;
    }

    public int getPunctuality() {
        return punctuality;
    }

    public int getTreatment() {
        return treatment;
    }

    public Date getDate() {
        return date;
    }

}