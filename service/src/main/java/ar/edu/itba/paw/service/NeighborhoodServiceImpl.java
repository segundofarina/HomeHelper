package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
import ar.edu.itba.paw.interfaces.services.NeighborhoodService;
import ar.edu.itba.paw.model.Neighborhood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class NeighborhoodServiceImpl implements NeighborhoodService {

    @Autowired
    NeighborhoodDao neighborhoodDao;

    @Transactional
    @Override
    public int insertNeighborhood(String name) {
        return neighborhoodDao.insertNeighborhood(name);
    }

    @Transactional
    @Override
    public List<Neighborhood> getAllNeighborhoods() {
        return neighborhoodDao.getAllNeighborhoods();
    }
}
