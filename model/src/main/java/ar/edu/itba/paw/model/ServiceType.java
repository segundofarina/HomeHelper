package ar.edu.itba.paw.model;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceType)) return false;
        ServiceType that = (ServiceType) o;
        return getServiceTypeId() == that.getServiceTypeId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceTypeId(), getName());
    }
}
