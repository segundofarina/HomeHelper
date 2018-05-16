package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.services.AptitudeService;
import ar.edu.itba.paw.model.Aptitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AptitudeServiceImpl implements AptitudeService {

    @Autowired
    AptitudeDao aptitudeDao;

    @Override
    public Optional<Aptitude> getAptitude(int aptitudeId) {
        return aptitudeDao.getAptitude(aptitudeId);
    }

    @Override
    public boolean removeAptitude(int aptId) {
        return aptitudeDao.removeAptitude(aptId);
    }
}
