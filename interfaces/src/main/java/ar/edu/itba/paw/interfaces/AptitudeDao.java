package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.ServiceType;

import java.util.List;

public interface AptitudeDao {

    List<Aptitude> getById(int id);

    boolean insertAptitude(int id, ServiceType service, String description);

}
