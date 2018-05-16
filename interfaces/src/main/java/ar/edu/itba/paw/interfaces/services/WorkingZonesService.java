package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Neighborhood;

import java.util.List;

public interface WorkingZonesService {

    boolean insertWorkingZoneOfProvider(int userId, int ngId);

    List<Neighborhood> getWorkingZonesOfProvider(int providerId);

    boolean removeWorkingZoneOfProvider(int userId, int ngId);


}
