package ar.edu.itba.paw.model;

public class ServiceType {
    private int serviceTypeId;
    private String name;

    public ServiceType(int serviceTypeId, String name) {
        this.serviceTypeId = serviceTypeId;
        this.name = name;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
