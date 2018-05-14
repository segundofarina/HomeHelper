package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class ProfileGeneralInfo {

    private int elemId;

    @NotBlank
    @Size(max = 1000)
    private String generalDescription;

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generdalDescription) {
        this.generalDescription = generdalDescription;
    }

    public int getElemId() {
        return elemId;
    }

    public void setElemId(int elemId) {
        this.elemId = elemId;
    }
}
