package ar.edu.itba.paw.persistence.utils;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.ServiceType;

import java.util.Set;

public class AptitudeRow{
    private int id;

    private ServiceType serviceType;


    private String description;

    private int userid;

    private Set<Review> reviews;

    public AptitudeRow(int id, int serviceTypeId,String serviceName, String description,int userid) {
        this.id = id;
        this.serviceType = new ServiceType(serviceTypeId,serviceName);
        this.description = description;
        this.userid=userid;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public String getDescription() {
        return description;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
