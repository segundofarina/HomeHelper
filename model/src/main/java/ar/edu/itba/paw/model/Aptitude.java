package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "aptitudes")
public class Aptitude {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aptitudes_aptitudeid_seq")
    @SequenceGenerator(sequenceName = "aptitudes_aptitudeid_seq", name = "aptitudes_aptitudeid_seq", allocationSize = 1)
    @Column(name = "aptitudeid")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicetypeid")
    private ServiceType service;

    @Column(name = "description", length = 10000, nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "aptitudeid")
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private SProvider sProvider;



    /* package*/ Aptitude(){

    }
    public Aptitude(SProvider sProvider, ServiceType service, String description, List<Review> reviews) {
        this.service = service;
        this.description = description;
        this.reviews = reviews;
        this.sProvider = sProvider;
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

    public SProvider getsProvider() {
        return sProvider;
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
        return !reviews.isEmpty();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setService(ServiceType service) {
        this.service = service;
    }
}
