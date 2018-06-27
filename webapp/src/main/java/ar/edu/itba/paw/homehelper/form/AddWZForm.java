package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

public class AddWZForm {

    @NotBlank
    private String coordsStr;

    public String getCoordsStr() {
        return coordsStr;
    }

    public void setCoordsStr(String coordsStr) {
        this.coordsStr = coordsStr;
    }
}
