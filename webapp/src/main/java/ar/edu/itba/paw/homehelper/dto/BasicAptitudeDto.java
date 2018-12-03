package ar.edu.itba.paw.homehelper.dto;

public class BasicAptitudeDto {
    private String description;
    private int serviceTypeId;

    public BasicAptitudeDto(String description, int serviceTypeId) {
        this.description = description;
        this.serviceTypeId = serviceTypeId;
    }

    public BasicAptitudeDto() {
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
