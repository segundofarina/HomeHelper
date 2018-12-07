package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class ReviewDto {

    private int id;
    private String comment;
    private CalificationDto scores;
    private String date;
    private BasicUserDto user;

    public ReviewDto() {
    }

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.user = new BasicUserDto(review.getUser());
        this.comment = review.getComment();
        this.scores = new CalificationDto(review);
        this.date = review.getDateDMY();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CalificationDto getScores() {
        return scores;
    }

    public void setScores(CalificationDto scores) {
        this.scores = scores;
    }

    public BasicUserDto getUser() {
        return user;
    }

    public void setUser(BasicUserDto user) {
        this.user = user;
    }


    /* Debugging */

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", scores=" + scores +
                ", date='" + date + '\'' +
                '}';
    }
}
