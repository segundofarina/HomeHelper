package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.ServiceType;

import java.util.List;

public interface AptitudeDao {

    List<Aptitude> getAptitudesOfUser(int id);

    boolean insertAptitude(int sProviderId, int serviceId, String description);

}
