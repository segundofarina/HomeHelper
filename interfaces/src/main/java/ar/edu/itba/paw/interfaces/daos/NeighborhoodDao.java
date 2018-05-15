package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Neighborhood;

import java.util.List;
import java.util.Set;

public interface NeighborhoodDao {

    int insertNeighborhood(String ngname);

    List<Neighborhood> getAllNeighborhoods();
}
