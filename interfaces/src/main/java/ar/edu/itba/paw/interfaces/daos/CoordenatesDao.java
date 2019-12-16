package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.CoordenatesPoint;

import java.util.List;

public interface CoordenatesDao {

    boolean insertCoordenatesOfProvider(int providerId, List<CoordenatesPoint> coordenatesPointSet);

    boolean deleteCoordenateOfProvideer(int providerId);



}
