package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Aptitude;

import java.util.Optional;

public interface AptitudeService {

    Aptitude addAptitude(int sProviderId, int serviceId, String description);

    Optional<Aptitude> getAptitude(int aptitudeId);

    boolean removeAptitude(int aptId);
}
