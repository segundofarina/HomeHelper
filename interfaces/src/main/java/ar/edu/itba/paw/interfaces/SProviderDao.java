package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.SProvider;

import java.util.List;

public interface SProviderDao {

    SProvider create(int userId, int postId);

    List<SProvider> getServiceProviders();

    SProvider getServiceProviderWithUserId(int userId);
}
