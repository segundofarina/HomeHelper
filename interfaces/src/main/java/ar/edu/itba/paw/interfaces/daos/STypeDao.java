package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.ServiceType;

import java.util.List;
import java.util.Optional;

public interface STypeDao {

    ServiceType create(String name);

    List<ServiceType> getServiceTypes();

    Optional<ServiceType> getServiceTypeWithId(int serviceTypeId);

    Optional<ServiceType> updateServiceTypeWithId(int serviceTypeId, String newServiceName);
}
