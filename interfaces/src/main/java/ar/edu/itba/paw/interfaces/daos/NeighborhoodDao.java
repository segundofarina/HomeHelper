package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Neighborhood;

import java.util.Set;

public interface NeighborhoodDao {

    void insertNeighborhood(int ngId);

    Set<Neighborhood> getAllNeighborhoods();
}
