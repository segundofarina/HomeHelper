package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.WZoneDao;
import ar.edu.itba.paw.interfaces.services.WorkingZonesService;
import ar.edu.itba.paw.model.Neighborhood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkingZonesServiceImpl implements WorkingZonesService {
    @Autowired
    WZoneDao wZoneDao;

    @Transactional
    @Override
    public void insertWorkingZoneOfProvider(int userId, int ngId) {
        wZoneDao.insertWorkingZoneOfProvider(userId, ngId);
    }

    @Transactional
    @Override
    public List<Neighborhood> getWorkingZonesOfProvider(int providerId) {
        return wZoneDao.getWorkingZonesOfProvider(providerId);
    }

    @Transactional
    @Override
    public boolean removeWorkingZoneOfProvider(int userId, int ngId) {
        return wZoneDao.removeWorkingZoneOfProvider(userId, ngId);
    }
}
