package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.utils.SizeListTuple;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface SProviderService {

    SProvider create(int userId, String description);

    SProvider getServiceProviderWithUserId(int userId);

    int getServiceProviderId(int userId);

    boolean isServiceProvider(int userId);

    SizeListTuple<SProvider> getServiceProvidersByNeighborhood(double clientLocationLat, double clientLocationLng, int userId, int page, int pageSize);

    SizeListTuple<SProvider> getServiceProvidersByNeighborhoodAndServiceType(double clientLocationLat, double clientLocationLng, int stId, int userId, int page, int pageSize);

    SizeListTuple<SProvider> getServiceProvidersByServiceType(int stId, int userId, int page, int pageSize);


    Set<Review> getReviewsOfServiceProvider(int sproviderId);

    Set<Aptitude> getAptitudesOfUser(int id);

    boolean updateDescriptionOfAptitude(int aptId, String description);

    boolean updateServiceTypeOfAptitude(int aptId, int stId);

    void addAptitude(int userId, int serviceType, String description);

    boolean removeAptitude(int userId, int serviceType);

    void updateDescriptionOfServiceProvider(int userId, String description);

    List<Review> getLatestReviewsOfServiceProvider(int providerId);

    Aptitude getAptitudeOfProvider(int serviceTypeId, SProvider provider);

    List<Aptitude> getAllAptitudesExcept(int serviceTypeId, SProvider provider);

    boolean addCoordenates(int providerId, List<CoordenatesPoint> coordenatesPoints);

    boolean deleteCoordenates(int providerId);

    Optional<SProvider> create(int id, String description, Map<Integer, String> aptitudes, List<CoordenatesPoint> coordenates);

    SizeListTuple<SProvider> getServiceProviders(int id, int page, int pageSize);

    SizeListTuple<Review> getReviewsOfServiceProvider(int id, Integer serviceTypeId, int page, int pageSize);

    List<Review> getReviewsOfServiceProvider(int id, int page, int pageSize);
}
