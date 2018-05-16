package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Set;

public interface SProviderService {


    SProvider create(int userId, String description);

    List<SProvider> getServiceProviders();

    List<SProvider> getServiceProvidersWithServiceType(int serviceType);

    SProvider getServiceProviderWithUserId(int userId);

    boolean insertReview(int quality,int cleanness, int price, int punctuality, int treatment, String comment);

    int getServiceProviderId(int userId);

    boolean isServiceProvider(int userId);

    boolean insertWorkingZoneOfProvider(int userId, int ngId);

    List<SProvider> getServiceProvidersWorkingIn(int ngId);

    List<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId);

    List<Review> getReviewsOfServiceProvider(int sproviderId);

    List<Aptitude> getAptitudesOfProvider(int id);

    boolean updateDescriptionOfAptitude(int aptId, String description);

    boolean updateServiceTypeOfAptitude(int aptId, int stId);

    boolean removeWorkingZoneOfProvider(int userId, int ngId);

    boolean addAptitude(int userId, int serviceType, String description);

    boolean removeAptitude(int userId, int serviceType);

    boolean updateDescriptionOfServiceProvider(int userId, String description);

    List<Review> getReviewsOfAptitude(int aptId);

    List<Review> getAllReviewsOfProvider(int userId);

    boolean removeAllReviewsFromAptitude(int aptId);
}
