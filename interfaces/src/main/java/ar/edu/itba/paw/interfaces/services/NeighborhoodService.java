package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Neighborhood;

import java.util.List;
import java.util.Set;

public interface NeighborhoodService {

    void insertNeighborhood(int ngId);

    List<Neighborhood> getAllNeighborhoods();
}
