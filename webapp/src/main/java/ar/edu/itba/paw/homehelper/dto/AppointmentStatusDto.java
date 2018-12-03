package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Status;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class AppointmentStatusDto {
    private int id;
    private String value;

    public AppointmentStatusDto() {
    }

    public AppointmentStatusDto(Status status, Locale locale, MessageSource messageSource) {
        this.id = status.getNumVal();
        this.value = messageSource.getMessage("status."+id,null,locale);
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
