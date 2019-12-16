package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.CoordenatesDao;
import ar.edu.itba.paw.interfaces.services.CoordenatesService;
import ar.edu.itba.paw.model.CoordenatesPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CoordenatesServiceImpl implements CoordenatesService {
    @Autowired
    private CoordenatesDao coordenatesDao;

    @Transactional
    @Override
    public boolean insertCoordenatesOfProvider(int providerId, List<CoordenatesPoint> coordenatesPointSet) {
        return coordenatesDao.insertCoordenatesOfProvider(providerId, coordenatesPointSet);
    }

    @Transactional
    @Override
    public boolean deleteCoordenateOfProvideer(int providerId) {
        return coordenatesDao.deleteCoordenateOfProvideer(providerId);
    }
}
