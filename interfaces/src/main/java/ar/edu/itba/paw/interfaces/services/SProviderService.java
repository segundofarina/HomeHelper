package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.AptitudeForm;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.ServiceType;

import java.util.List;

public interface SProviderService {
    SProvider create(int userId, String description);

    List<SProvider> getServiceProviders();

    SProvider getServiceProviderWithUserId(int userId);

    double getCalificationOfServiceProvider(int userId);

    boolean addReviewToAptitude(int userId, int serviceType, int rating, String comment);

    boolean addAptitude(int userId, int serviceType, String description);

    public List<ServiceType> getServiceTypes();

    public boolean updateAptitude(int aptId,String newDescription);

    public boolean updateAptitudes(int spId, List<AptitudeForm> list);

}
