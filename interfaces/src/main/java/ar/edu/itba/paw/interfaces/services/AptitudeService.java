package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Aptitude;

import java.util.List;

public interface AptitudeService {

    //List<Aptitude> getAptitudesOfUser(int id);

    //boolean insertAptitude(int sProviderId, int serviceId, String description);

    Aptitude getAptitude(int aptitudeId);
}
