package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "aptitudes")
public class Aptitude {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aptitudes_aptitudeid_seq")
    @SequenceGenerator(sequenceName = "aptitudes_aptitudeid_seq", name = "aptitudes_aptitudeid_seq", allocationSize = 1)
    @Column(name = "aptitudeid")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicetypeid")
    private ServiceType service;

    @Column(name = "description", length = 10000, nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "aptitudeid")
    private Set<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private SProvider sProvider;


    /* package*/ Aptitude() {

    }

    public Aptitude(int id) {
        this.id = id;
    }

    public Aptitude(SProvider sProvider, ServiceType service, String description, Set<Review> reviews) {
        this.service = service;
        this.description = description;
        this.reviews = reviews;
        this.sProvider = sProvider;
    }

    public Aptitude(ServiceType service, String description) {
        this.service = service;
        this.description = description;
    }
    public Aptitude(int id ,ServiceType service, String description, Set<Review> reviews){
        this.id =id;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public SProvider getsProvider() {
        return sProvider;
    }

    public int getId() {
        return id;
    }

    public double getQualityCalification() {

        if (reviews.size() == 0) {
            return 0;
        }

        double quality = 0;

        for (Review review : reviews) {
            quality += review.getQualityCalification();
        }
        return Math.floor((quality / reviews.size()) * 100) / 100;
    }

    public double getCleannessCalification() {

        if (reviews.size() == 0) {
            return 0;
        }

        double cleanness = 0;

        for (Review review : reviews) {
            cleanness += review.getCleannessCalification();
        }
        return Math.floor((cleanness / reviews.size()) * 100) / 100;
    }

    public double getPriceCalification() {

        if (reviews.size() == 0) {
            return 0;
        }

        double price = 0;

        for (Review review : reviews) {
            price += review.getPriceCalification();
        }
        return Math.floor((price / reviews.size()) * 100) / 100;
    }

    public double getPunctualityCalification() {

        if (reviews.size() == 0) {
            return 0;
        }

        double punctuality = 0;

        for (Review review : reviews) {
            punctuality += review.getPunctualityCalification();
        }
        return Math.floor((punctuality / reviews.size()) * 100) / 100;
    }

    public double getTreatmentCalification() {

        if (reviews.size() == 0) {
            return 0;
        }

        double treatment = 0;

        for (Review review : reviews) {
            treatment += review.getTreatmentCalification();
        }

        return Math.floor((treatment / reviews.size()) * 100) / 100;
    }

    public double getGeneralCalification() {

        if (reviews.size() == 0) {
            return 0;
        }

        double generalCalification = 0;

        for (Review review : reviews) {
            generalCalification += review.getGeneralCalification();
        }

        return Math.floor((generalCalification / reviews.size()) * 100) / 100;


    }

    public boolean hasReviews() {
        return !reviews.isEmpty();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setService(ServiceType service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aptitude)) return false;

        Aptitude aptitude = (Aptitude) o;

        return getId() == aptitude.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
