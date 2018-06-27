package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Set;

public interface SProviderService {



    SProvider create(int userId, String description);

    Set<SProvider> getServiceProviders();

    List<SProvider> getServiceProvidersWithServiceType(int serviceType);

    SProvider getServiceProviderWithUserId(int userId);

    int getServiceProviderId(int userId);

    boolean isServiceProvider(int userId);

    void insertWorkingZoneOfProvider(int userId, int ngId);

    List<SProvider> getServiceProvidersWorkingIn(int ngId);

    Set<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId);

    Set<Review> getReviewsOfServiceProvider(int sproviderId);

    Set<Aptitude> getAptitudesOfUser(int id);

    boolean updateDescriptionOfAptitude(int aptId, String description);

    boolean updateServiceTypeOfAptitude(int aptId, int stId);

    boolean removeWorkingZoneOfProvider(int userId, int ngId);

    void addAptitude(int userId, int serviceType, String description);

    boolean removeAptitude(int userId, int serviceType);

    void updateDescriptionOfServiceProvider(int userId, String description);

    List<Review> getLatestReviewsOfServiceProvider(int providerId);
}
