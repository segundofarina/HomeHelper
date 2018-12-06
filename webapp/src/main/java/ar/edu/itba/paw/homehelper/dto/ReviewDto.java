package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class ReviewDto {
    private int id;
    private String comment;
    private CalificationDto scores;
    private String date;

    public ReviewDto() {
    }

    public ReviewDto(Review review, Locale locale, MessageSource messageSource) {
        this.id = review.getId();
        //this.user = new ClientDto(review.getClient());
        //this.aptitude = new AptitudeDto(review.getAptitude());

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
