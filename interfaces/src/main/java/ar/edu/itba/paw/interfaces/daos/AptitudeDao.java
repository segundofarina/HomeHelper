package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Aptitude;


import java.util.List;

public interface AptitudeDao {

    List<Aptitude> getAptitudesOfUser(int id);

    boolean insertAptitude(int sProviderId, int serviceId, String description);

    boolean updateDescriptionOfAptitude(int aptId, String description);

    boolean updateServiceTypeOfAptitude(int aptId, int stId);

    boolean removeAptitude(int aptId);

    int getAptitudeId(int userId, int stId);

    Aptitude getAptitude(int id);

}
