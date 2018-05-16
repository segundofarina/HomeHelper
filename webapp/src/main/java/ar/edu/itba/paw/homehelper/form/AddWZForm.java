package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

public class AddWZForm {
    public int getElemId() {
        return elemId;
    }

    @NotBlank
    private String ng;

    public void setElemId(int elemId) {
        this.elemId = elemId;
    }

    private int elemId;

    public String getNg() {
        return ng;
    }

    public void setNg(String ng) {
        this.ng = ng;
    }

    public int getNgId() {
        return Integer.parseInt(ng);
    }
}
