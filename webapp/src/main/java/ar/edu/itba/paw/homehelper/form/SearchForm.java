package ar.edu.itba.paw.homehelper.form;

import javax.validation.constraints.Pattern;

public class SearchForm {

    @Pattern(regexp = "[0-9]+")
    private String city;
    @Pattern(regexp = "[0-9]+")
    private String serviceType;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getCityId() {
        return Integer.valueOf(city);
    }

    public int getServiceTypeId() {
        return Integer.valueOf(serviceType);
    }
}
