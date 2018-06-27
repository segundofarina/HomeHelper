package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.CoordenatesPoint;

import java.util.Set;

public interface CoordenatesService {
    boolean insertCoordenatesOfProvider(int providerId, Set<CoordenatesPoint> coordenatesPointSet);

    boolean deleteCoordenateOfProvideer(int providerId);
}
