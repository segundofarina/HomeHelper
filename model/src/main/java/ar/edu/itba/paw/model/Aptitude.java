package ar.edu.itba.paw.model;

import org.w3c.dom.Comment;

import java.util.List;

public class Aptitude {
    private ServiceType service;
    private String description;
    private List<Review> reviews;

    public Aptitude(ServiceType service, String description, List<Review> reviews) {
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
}
