package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.interfaces.services.STypeService;
import ar.edu.itba.paw.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class STypeServiceImpl implements STypeService {

    @Autowired
    STypeDao sTypeDao;

    @Override
    public ServiceType create(String name) {
        return sTypeDao.create(name);
    }

    @Override
    public List<ServiceType> getServiceTypes() {
        return sTypeDao.getServiceTypes();
    }

    @Override
    public ServiceType getServiceTypeWithId(int serviceTypeId) {
        Optional<ServiceType> ans = sTypeDao.getServiceTypeWithId(serviceTypeId);

        if(ans.isPresent()){
            return ans.get();
        }
        return null;
    }

    @Override
    public ServiceType updateServiceTypeWithId(int serviceTypeId, String newServiceName) {
        Optional<ServiceType> ans = sTypeDao.updateServiceTypeWithId(serviceTypeId,newServiceName);

        if(ans.isPresent()){
            return ans.get();
        }
        return null;
    }
}
