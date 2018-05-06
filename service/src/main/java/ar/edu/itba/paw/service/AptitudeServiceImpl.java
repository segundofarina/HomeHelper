package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.services.AptitudeService;
import ar.edu.itba.paw.model.Aptitude;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AptitudeServiceImpl implements AptitudeService {

    @Autowired
    AptitudeDao aptitudeDao;

    @Override
    public List<Aptitude> getAptitudesOfUser(int id) {
        return aptitudeDao.getAptitudesOfUser(id);
    }

    @Override
    public boolean insertAptitude(int sProviderId, int serviceId, String description) {
        return aptitudeDao.insertAptitude(sProviderId,serviceId,description);
    }
}
