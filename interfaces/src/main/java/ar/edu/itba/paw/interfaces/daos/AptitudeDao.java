package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Aptitude;


import java.util.List;

public interface AptitudeDao {

    List<Aptitude> getAptitudesOfUser(int id);

    boolean insertAptitude(int sProviderId, int serviceId, String description);

    boolean updateAptitude(int aptId, String description);

    Aptitude getAptitude(int id);

}
