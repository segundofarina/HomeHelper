package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

public class SearchForm {

    @Valid
    @NotBlank
    private String addressField;

    private String lat;
    private String lng;

    //private String city;
    @Pattern(regexp = "[0-9]+")
    private String serviceType;


    public String getAddressField() {
        return addressField;
    }

    public void setAddressField(String addressField) {
        this.addressField = addressField;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceTypeId() {
        return Integer.parseInt(serviceType);
    }

    public double getLatDouble() {
        return Double.parseDouble(lat);
    }

    public double getLngDouble() {
        return Double.parseDouble(lng);
    }

}
