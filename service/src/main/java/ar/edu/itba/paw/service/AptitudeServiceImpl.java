package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.services.AptitudeService;
import ar.edu.itba.paw.model.Aptitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AptitudeServiceImpl implements AptitudeService {

    @Autowired
    AptitudeDao aptitudeDao;

    @Transactional
    @Override
    public Aptitude addAptitude(int sProviderId, int serviceId, String description){
        return aptitudeDao.insertAptitude(sProviderId,serviceId,description);
    }

    @Transactional
    @Override
    public Optional<Aptitude> getAptitude(int aptitudeId) {
        return aptitudeDao.getAptitude(aptitudeId);
    }

    @Transactional
    @Override
    public boolean removeAptitude(int aptId) {
        return aptitudeDao.removeAptitude(aptId);
    }
}
