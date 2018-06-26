package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Neighborhood;
import ar.edu.itba.paw.model.SProvider;

import java.util.List;

public interface WZoneDao {

    boolean insertWorkingZoneOfProvider(int userId, int ngId);

    List<Neighborhood> getWorkingZonesOfProvider(int providerId);

    List<SProvider> getServiceProvidersWorkingIn(int ngId);

    boolean removeWorkingZoneOfProvider(int userId, int ngId);
}
