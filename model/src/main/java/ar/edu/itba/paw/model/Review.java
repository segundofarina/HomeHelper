package ar.edu.itba.paw.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_reviewId_seq")
    @SequenceGenerator(sequenceName = "reviews_reviewId_seq", name = "reviews_reviewId_seq", allocationSize = 1)
    @Column(name = "reviewId")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "userId")
    @Fetch(FetchMode.JOIN)
    private User user;

    @Column(name = "appointmentId")
    private int appointmentId;

    @ManyToOne()
    @JoinColumn(name = "aptitudeId")
    @Fetch(FetchMode.JOIN)
    private Aptitude aptitude;

    @Column(name = "comment", length = 10000)
    private String comment;

    @Column(name = "quality")
    private int quality;

    @Column(name = "cleanness")
    private int cleanness;

    @Column(name = "price")
    private int price;

    @Column(name = "punctuality")
    private int punctuality;

    @Column(name = "treatment")
    private int treatment;

    @Column(name = "reviewdate")
    private Date date;

    /* package */ Review() {

    }

    public Review(int quality, int cleanness, int price, int punctuality, int treatment, String comment, Date date, User user, Aptitude aptitude, int appointmentId) {
        this.quality = quality;
        this.cleanness = cleanness;
        this.price = price;
        this.punctuality = punctuality;
        this.treatment = treatment;
        this.comment = comment;
        this.date = date;
        this.user = user;
        this.aptitude = aptitude;
        this.appointmentId = appointmentId;
    }

    public Review(int id,int quality, int cleanness, int price, int punctuality, int treatment, String comment, Date date, User user, Aptitude aptitude, int appointmentId) {
        this.id= id;
        this.quality = quality;
        this.cleanness = cleanness;
        this.price = price;
        this.punctuality = punctuality;
        this.treatment = treatment;
        this.comment = comment;
        this.date = date;
        this.user = user;
        this.aptitude = aptitude;
        this.appointmentId = appointmentId;
    }

    public int getQualityCalification() {
        return this.quality;
    }

    public int getCleannessCalification() {
        return this.cleanness;
    }

    public int getPriceCalification() {
        return this.price;
    }

    public int getPunctualityCalification() {
        return this.punctuality;
    }

    public int getTreatmentCalification() {
        return this.treatment;
    }

    public int getGeneralCalification() {
        int generalCalification = 0;
        generalCalification += quality;
        generalCalification += cleanness;
        generalCalification += price;
        generalCalification += punctuality;
        generalCalification += treatment;
        return generalCalification / 5;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public String getDateDMY() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(getDate());
    }

    public int getAppointmentId() {
        return appointmentId;
    }


    public User getUser() {
        return user;
    }

    public Aptitude getAptitude() {
        return aptitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        return id == review.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }
}
