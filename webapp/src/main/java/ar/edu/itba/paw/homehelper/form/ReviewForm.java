package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class ReviewForm {

    private int appointmentId;

    @NotBlank
    private String quality;

    @NotBlank
    private String cleannes;

    @NotBlank
    private String price;

    @NotBlank
    private String punctuality;

    @NotBlank
    private String treatment;

    @NotBlank
    @Size(max = 500)
    private String msg;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getCleannes() {
        return cleannes;
    }

    public void setCleannes(String cleannes) {
        this.cleannes = cleannes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(String punctuality) {
        this.punctuality = punctuality;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public int getQualityInt() {
        return Integer.parseInt(quality);
    }

    public int getCleannesInt() {
        return Integer.parseInt(cleannes);
    }

    public int getPriceInt() {
        return Integer.parseInt(price);
    }

    public int getPunctualityInt() {
        return Integer.parseInt(punctuality);
    }

    public int getTreatmentInt() {
        return Integer.parseInt(treatment);
    }

    public int getMsgInt() {
        return Integer.parseInt(msg);
    }
}

