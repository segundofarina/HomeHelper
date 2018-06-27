package ar.edu.itba.paw.model;


import java.util.Objects;

public class AptitudeForm {

    private int serviceTypeid;
    private String description;
    private int serviceProviderId;

    public AptitudeForm(int serviceTypeid, String description, int serviceProviderId) {
        this.serviceTypeid = serviceTypeid;
        this.description = description;
        this.serviceProviderId = serviceProviderId;
    }

    public int getServiceTypeid() {
        return serviceTypeid;
    }

    public String getDescription() {
        return description;
    }

    public void setServiceTypeid(int serviceTypeid) {
        this.serviceTypeid = serviceTypeid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(int serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AptitudeForm)) return false;

        AptitudeForm that = (AptitudeForm) o;

        if (getServiceTypeid() != that.getServiceTypeid()) return false;
        if (getServiceProviderId() != that.getServiceProviderId()) return false;
        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getServiceTypeid();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getServiceProviderId();
        return result;
    }
}
