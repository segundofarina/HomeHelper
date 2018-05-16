package ar.edu.itba.paw.model;


import java.util.List;

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

        if(reviews.size() == 0){
            return 0;
        }

        double quality = 0;

        for(Review review : reviews){
            quality += review.getQualityCalification();
        }
        return Math.floor((quality/reviews.size()) * 100) / 100;
    }

    public double getCleannessCalification(){

        if(reviews.size() == 0){
            return 0;
        }

        double cleanness = 0;

        for(Review review : reviews){
            cleanness += review.getCleannessCalification();
        }
        return Math.floor((cleanness/reviews.size()) * 100) / 100;
    }

    public double getPriceCalification(){

        if(reviews.size() == 0){
            return 0;
        }

        double price = 0;

        for(Review review : reviews){
            price += review.getPriceCalification();
        }
        return Math.floor((price/reviews.size()) * 100) / 100;
    }

    public double getPunctualityCalification(){

        if(reviews.size() == 0){
            return 0;
        }

        double punctuality = 0;

        for(Review review : reviews){
            punctuality += review.getPunctualityCalification();
        }
        return Math.floor((punctuality/reviews.size()) * 100) / 100;
    }

    public double getTreatmentCalification(){

        if(reviews.size() == 0){
            return 0;
        }

        double treatment = 0;

        for(Review review : reviews){
            treatment += review.getTreatmentCalification();
        }

        return Math.floor((treatment/reviews.size()) * 100) / 100;
    }

    public double getGeneralCalification() {

        if(reviews.size() == 0){
            return 0;
        }

        double generalCalification = 0;

        for(Review review : reviews){
            generalCalification += review.getGeneralCalification();
        }

        return Math.floor((generalCalification/reviews.size()) * 100) / 100;


    }

    public boolean hasReviews(){
        if(reviews.size() != 0){
            return true;
        }
        return false;
    }
}
