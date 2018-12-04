package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.SProvider;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SProviderDao {

    Optional<SProvider> create(int userId, String description);

    List<SProvider> getServiceProviders();

    Optional<SProvider> getServiceProviderWithUserId(int userId);

    Set<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId);

    boolean updateDescriptionOfServiceProvider(int userId, String description);

}
