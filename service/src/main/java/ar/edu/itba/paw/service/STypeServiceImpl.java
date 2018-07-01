package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.interfaces.services.STypeService;
import ar.edu.itba.paw.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class STypeServiceImpl implements STypeService {

    @Autowired
    STypeDao sTypeDao;

    @Transactional
    @Override
    public ServiceType create(String name) {
        return sTypeDao.create(name);
    }

    @Transactional
    @Override
    public List<ServiceType> getServiceTypes() {
        return sTypeDao.getServiceTypes();
    }

    @Transactional
    @Override
    public ServiceType getServiceTypeWithId(int serviceTypeId) {
        Optional<ServiceType> ans = sTypeDao.getServiceTypeWithId(serviceTypeId);

        if (ans.isPresent()) {
            return ans.get();
        }
        return null;
    }

    @Transactional
    @Override
    public ServiceType updateServiceTypeWithId(int serviceTypeId, String newServiceName) {
        Optional<ServiceType> ans = sTypeDao.updateServiceTypeWithId(serviceTypeId, newServiceName);

        if (ans.isPresent()) {
            return ans.get();
        }
        return null;
    }
}
