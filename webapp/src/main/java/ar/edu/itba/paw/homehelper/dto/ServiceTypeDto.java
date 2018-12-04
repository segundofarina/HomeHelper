package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.ServiceType;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class ServiceTypeDto {
    private int id;
    private String name;

    public ServiceTypeDto() {
    }

    public ServiceTypeDto(ServiceType serviceType, Locale locale, MessageSource messageSource) {
        this.id = serviceType.getId();
        this.name = messageSource.getMessage("service-type."+id,null,locale);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
