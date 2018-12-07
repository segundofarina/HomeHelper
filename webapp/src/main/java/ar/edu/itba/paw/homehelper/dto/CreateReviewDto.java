package ar.edu.itba.paw.homehelper.dto;

public class CreateReviewDto {
    private int appointmentId;
    private ReviewDto review;

    public CreateReviewDto() {
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public ReviewDto getReview() {
        return review;
    }

    public void setReview(ReviewDto review) {
        this.review = review;
    }
}
