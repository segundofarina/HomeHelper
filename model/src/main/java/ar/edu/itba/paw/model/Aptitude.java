package ar.edu.itba.paw.model;

import org.w3c.dom.Comment;

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
}
