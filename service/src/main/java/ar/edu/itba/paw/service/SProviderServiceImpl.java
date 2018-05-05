package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.SProviderDao;
import ar.edu.itba.paw.interfaces.SProviderService;
import ar.edu.itba.paw.model.Post;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SProviderServiceImpl implements SProviderService {

    @Autowired
    SProviderDao sProviderDao;

    @Override
    public SProvider create(int userId, String description) {
        return sProviderDao.create(userId,description).get();
    }

    @Override
    public List<SProvider> getServiceProviders() {
        return sProviderDao.getServiceProviders();
    }

    @Override
    public SProvider getServiceProviderWithUserId(int userId) {
        return sProviderDao.getServiceProviderWithUserId(userId).get();
    }

    @Override
    public double getCalificationOfServiceProvider(int userId) {
        return sProviderDao.getCalificationOfServiceProvider(userId);
    }

}
