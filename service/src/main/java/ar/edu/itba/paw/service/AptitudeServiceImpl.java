package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.services.AptitudeService;
import ar.edu.itba.paw.model.Aptitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AptitudeServiceImpl implements AptitudeService {

    @Autowired
    AptitudeDao aptitudeDao;

    @Override
    public Aptitude getAptitude(int aptitudeId) {
        return aptitudeDao.getAptitude(aptitudeId);
    }
}
