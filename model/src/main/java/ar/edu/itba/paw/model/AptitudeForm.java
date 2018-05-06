package ar.edu.itba.paw.model;


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
}
