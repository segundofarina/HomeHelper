package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.SProvider;

import java.util.List;
import java.util.Optional;

public interface SProviderDao {

    Optional<SProvider> create(int userId);

    List<SProvider> getServiceProviders();

    Optional<SProvider> getServiceProviderWithUserId(int userId);
}
