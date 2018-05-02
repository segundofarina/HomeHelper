package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.ServiceType;

import java.util.List;

public interface STypeDao {

    ServiceType create(String name);

    List<ServiceType> getServiceTypes();

    ServiceType getServiceTypeWithId(int serviceTypeId);

    ServiceType updateServiceTypeWithId(int serviceTypeId, String newServiceName);
}
