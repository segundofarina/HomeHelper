package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Status;

public class AppointmentStatusDto {
    private int id;
    private String value;

    public AppointmentStatusDto() {
    }

    public AppointmentStatusDto(Status status) {
        this.id = status.getNumVal();
        this.value = status.toString(); /* TODO: Internationalize value */
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
