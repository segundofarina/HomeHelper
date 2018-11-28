package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;

import java.util.Date;

public class ReviewDto {
    private int id;
    private ClientDto user;
    private AptitudeDto aptitude;
    private String comment;
    private CalificationDto calificationDto;
    private Date reviewDate;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.user = new ClientDto(review.getUser());
        this.aptitude = new AptitudeDto(review.getAptitude());
        this.comment = review.getComment();
        this.calificationDto = new CalificationDto(review.getAptitude());
        this.reviewDate = review.getDate();
    }

    public int getId() {
        return id;
    }

    public ClientDto getUser() {
        return user;
    }

    public AptitudeDto getAptitude() {
        return aptitude;
    }

    public String getComment() {
        return comment;
    }

    public CalificationDto getCalificationDto() {
        return calificationDto;
    }

    public Date getReviewDate() {
        return reviewDate;
    }
}
