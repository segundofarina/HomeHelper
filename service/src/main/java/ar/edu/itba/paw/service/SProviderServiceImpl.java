package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SProviderServiceImpl implements SProviderService {

    @Autowired
    SProviderDao sProviderDao;

    @Autowired
    AptitudeDao aptitudeDao;

    @Autowired
    ReviewDao reviewDao;

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

    @Override
    public boolean addReviewToAptitude(int userId, int serviceType, int rating, String comment) {
        List<Aptitude> list = aptitudeDao.getAptitudesOfUser(userId);
        for(Aptitude a : list){
            if(a.getService().getServiceTypeId() == serviceType){
                return reviewDao.insertReview(userId,a.getId(),rating,comment);
            }
        }

        return false;
    }


    @Override
    public boolean addAptitude(int userId, int serviceType, String description) {
        return aptitudeDao.insertAptitude(userId,serviceType,description);
    }


}
