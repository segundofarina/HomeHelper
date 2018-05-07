package ar.edu.itba.paw.model;

import org.w3c.dom.Comment;

import java.util.List;
import java.util.Map;

public class Aptitude {
    private ServiceType service;
    private String description;
    private List<Review> reviews;
    private int id;


    public Aptitude(int id ,ServiceType service, String description, List<Review> reviews) {
        this.id = id;
        this.service = service;
        this.description = description;
        this.reviews = reviews;
    }

    public ServiceType getService() {
        return service;
    }

    public String getDescription() {
        return description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public int getId() {
        return id;
    }

    public double getQualityCalification(){

        double quality = 0;

        for(Review review : reviews){
            quality += review.getQualityCalification();
        }

        return quality/reviews.size();
    }

    public double getCleannessCalification(){

        double cleanness = 0;

        for(Review review : reviews){
            cleanness += review.getCleannessCalification();
        }

        return cleanness/reviews.size();
    }

    public double getPriceCalification(){

        double price = 0;

        for(Review review : reviews){
            price += review.getPriceCalification();
        }
        return price/reviews.size();
    }

    public double getPunctualityCalification(){

        double punctuality = 0;

        for(Review review : reviews){
            punctuality += review.getPunctualityCalification();
        }

        return punctuality/reviews.size();
    }

    public double getTreatmentCalification(){

        double treatment = 0;

        for(Review review : reviews){
            treatment += review.getTreatmentCalification();
        }

        return treatment/reviews.size();
    }

    public double getGeneralCalification() {

        double generalCalification = 0;

        for(Review review : reviews){
            generalCalification += review.getGeneralCalification();
        }

        return generalCalification/reviews.size();
    }
}
