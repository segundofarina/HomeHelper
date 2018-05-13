package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Neighborhood;

import java.util.Set;

public interface NeighborhoodService {

    void insertNeighborhood(int ngId);

    Set<Neighborhood> getAllNeighborhoods();
}
