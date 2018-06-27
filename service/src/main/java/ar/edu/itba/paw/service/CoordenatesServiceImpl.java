package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.CoordenatesDao;
import ar.edu.itba.paw.interfaces.services.CoordenatesService;
import ar.edu.itba.paw.model.CoordenatesPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CoordenatesServiceImpl implements CoordenatesService {
    @Autowired
    private CoordenatesDao coordenatesDao;

    @Override
    public boolean insertCoordenatesOfProvider(int providerId, Set<CoordenatesPoint> coordenatesPointSet) {
        return coordenatesDao.insertCoordenatesOfProvider(providerId, coordenatesPointSet);
    }

    @Override
    public boolean deleteCoordenateOfProvideer(int providerId) {
        return coordenatesDao.deleteCoordenateOfProvideer(providerId);
    }
}
