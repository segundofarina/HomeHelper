package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class AptitudeForm {

    private int elemId;

    @NotBlank
    private String serviceType;

    @NotBlank
    @Size(max = 1000)
    private String aptDescription;

    public int getElemId() {
        return elemId;
    }

    public void setElemId(int elemId) {
        this.elemId = elemId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAptDescription() {
        return aptDescription;
    }

    public void setAptDescription(String aptDescription) {
        this.aptDescription = aptDescription;
    }

    public int getServiceTypeId() {
        return Integer.parseInt(serviceType);
    }
}
