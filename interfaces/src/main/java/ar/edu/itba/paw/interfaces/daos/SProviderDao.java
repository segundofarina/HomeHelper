package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface SProviderDao {

    Optional<SProvider> create(int userId, String description);

    List<SProvider> getServiceProviders();

    Optional<SProvider> getServiceProviderWithUserId(int userId);

    boolean updateDescriptionOfServiceProvider(int userId, String description);

}
