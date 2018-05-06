package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Review {
    private int rating;
    private String comment;
    private Timestamp date;

    public Review(int rating, String comment, Timestamp date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getDate() {
        return date;
    }
}
