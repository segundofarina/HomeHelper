package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.AptitudeForm;
import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.ServiceType;
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

    @Autowired
    STypeDao sTypeDao;

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
        List<Aptitude> aptitudes = aptitudeDao.getAptitudesOfUser(userId);
        double finalCalification = 0;
        double calification = 0;

        for(Aptitude aptitude : aptitudes){
            calification = 0;
            for(Review review : aptitude.getReviews()){
                calification+=review.getRating();
            }
            finalCalification = calification/aptitude.getReviews().size();
        }

        return finalCalification;
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

    public List<ServiceType> getServiceTypes(){
        return sTypeDao.getServiceTypes();
    }

    @Override
    public boolean updateAptitude(int aptId, String newDescription) {
        return aptitudeDao.updateAptitude(aptId,newDescription);
    }


    @Override
    public boolean updateAptitudes(int spId, List<AptitudeForm> list) {
        SProvider provider = getServiceProviderWithUserId(spId);
        if(provider == null){
            return false;
        }

        for(Aptitude apt : provider.getAptitudes()){
            AptitudeForm newApt = getApt(list,apt.getService().getServiceTypeId());
            boolean ans;
            if(newApt != null){
                ans =aptitudeDao.updateAptitude(apt.getId(),newApt.getDescription());
            }else{
                ans =aptitudeDao.insertAptitude(spId,newApt.getServiceTypeid(),newApt.getDescription());
            }
            if(ans == false){
                return ans;
            }
        }

        return true;

    }

    private static AptitudeForm getApt ( List<AptitudeForm> list, int servicetypeId){
        for (AptitudeForm apt : list){
            if(servicetypeId == apt.getServiceTypeid()){
                return apt;
            }
        }
        return null;
    }



}
