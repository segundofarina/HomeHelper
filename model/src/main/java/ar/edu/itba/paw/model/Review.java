package ar.edu.itba.paw.model;

public class Review {
    private int rating;
    private String comment;
    private String date;

    public Review(int rating, String comment, String date) {
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

    public String getDate() {
        return date;
    }
}
