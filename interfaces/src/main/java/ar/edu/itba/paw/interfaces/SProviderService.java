package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.SProvider;

import java.util.List;
import java.util.Optional;

public interface SProviderService {
    SProvider create(int userId, String description);

    List<SProvider> getServiceProviders();

    SProvider getServiceProviderWithUserId(int userId);

    double getCalificationOfServiceProvider(int userId);
}
