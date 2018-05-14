package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class UpdateAptitudeForm {
    private int aptitutdeId;

    private int serviceType;

    @NotBlank
    @Size(max = 1000)
    private String aptDescription;

    private String action;

    public int getAptitutdeId() {
        return aptitutdeId;
    }

    public void setAptitutdeId(int aptitutdeId) {
        this.aptitutdeId = aptitutdeId;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getAptDescription() {
        return aptDescription;
    }

    public void setAptDescription(String aptDescription) {
        this.aptDescription = aptDescription;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
