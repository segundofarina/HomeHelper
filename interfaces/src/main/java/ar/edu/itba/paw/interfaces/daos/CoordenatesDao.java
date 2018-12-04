package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;

import java.util.List;
import java.util.Set;

public interface CoordenatesDao {
    boolean insertCoordenatesOfProvider(int providerId, Set<CoordenatesPoint> coordenatesPointSet);

    boolean deleteCoordenateOfProvideer(int providerId);



}
