package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Neighborhood;
import ar.edu.itba.paw.model.SProvider;

import java.util.Set;

public interface WZoneDao {

    boolean insertWorkingZoneOfProvider(int userId, int ngId);

    Set<Neighborhood> getWorkingZonesOfProvider(int providerId);

    Set<SProvider> getServiceProvidersWorkingIn(int ngId);

}
