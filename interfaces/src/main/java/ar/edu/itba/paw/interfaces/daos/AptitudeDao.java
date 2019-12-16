package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Aptitude;


import java.util.Optional;
import java.util.Set;

public interface AptitudeDao {

    Set<Aptitude> getAptitudesOfUser(int id);

    Aptitude insertAptitude(int sProviderId, int serviceId, String description);

    boolean updateDescriptionOfAptitude(int aptId, String description);

    boolean updateServiceTypeOfAptitude(int aptId, int stId);

    boolean removeAptitude(int aptId);

    int getAptitudeId(int userId, int stId);

    Optional<Aptitude> getAptitude(int id);

}
