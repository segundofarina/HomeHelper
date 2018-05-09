package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.AptitudeForm;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.ServiceType;

import java.util.List;

public interface SProviderService {
    SProvider create(int userId, String description);

    List<SProvider> getServiceProviders();

    List<SProvider> getServiceProvidersWithServiceType(int serviceType);

    SProvider getServiceProviderWithUserId(int userId);

    boolean addReviewToAptitude(int userId, int serviceType, int quality,int cleanness, int price, int punctuality, int treatment, String comment);

    boolean addAptitude(int userId, int serviceType, String description);

    List<ServiceType> getServiceTypes();

    boolean updateAptitude(int aptId,String newDescription);

    boolean updateAptitudes(int spId, List<AptitudeForm> list);

    int getServiceProviderId(int userId);

    boolean isServiceProvider(int userId);

}
