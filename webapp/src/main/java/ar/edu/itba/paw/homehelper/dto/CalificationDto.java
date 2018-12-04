package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CalificationDto {
    private Double quality;
    private Double cleanness;
    private Double price;
    private Double punctuality;
    private Double treatment;
    private Double general;

    public CalificationDto() {
    }

    public CalificationDto(Aptitude aptitude) {
        this.quality = aptitude.getQualityCalification();
        this.cleanness = aptitude.getCleannessCalification();
        this.price = aptitude.getPriceCalification();
        this.punctuality = aptitude.getPunctualityCalification();
        this.treatment = aptitude.getTreatmentCalification();
        this.general = aptitude.getGeneralCalification();
    }

    public CalificationDto(Review review) {
        this.quality =  (double) review.getQualityCalification();
        this.cleanness = (double) review.getCleannessCalification();
        this.price = (double) review.getPriceCalification();
        this.punctuality =(double) review.getPunctualityCalification();
        this.treatment = (double)review.getTreatmentCalification();
        this.general =(double) review.getGeneralCalification();
    }
    public Double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public Double getCleanness() {
        return cleanness;
    }

    public void setCleanness(double cleanness) {
        this.cleanness = cleanness;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(double punctuality) {
        this.punctuality = punctuality;
    }

    public Double getTreatment() {
        return treatment;
    }

    public void setTreatment(double treatment) {
        this.treatment = treatment;
    }

    public Double getGeneral() {
        return general;
    }

    public void setGeneral(double general) {
        this.general = general;
    }
}