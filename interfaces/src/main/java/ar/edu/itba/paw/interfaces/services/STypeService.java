package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.ServiceType;

import java.util.List;
import java.util.Optional;

public interface STypeService {

    ServiceType create(String name);

    List<ServiceType> getServiceTypes();

    ServiceType getServiceTypeWithId(int serviceTypeId);

    ServiceType updateServiceTypeWithId(int serviceTypeId, String newServiceName);
}
