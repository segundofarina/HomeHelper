package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.ServiceType;

public class ServiceTypeDTO {
    private int id;
    private String name;

    public ServiceTypeDTO() {
    }

    public ServiceTypeDTO(ServiceType serviceType) {
        this.id = serviceType.getId();
        this.name = serviceType.getName();
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
