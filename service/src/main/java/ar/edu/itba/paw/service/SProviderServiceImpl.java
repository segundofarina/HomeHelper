package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    WZoneDao wZoneDao;

    @Override
    public SProvider create(int userId, String description) {
        return sProviderDao.create(userId,description).get();
    }

    @Override
    public List<SProvider> getServiceProviders() {
        return sProviderDao.getServiceProviders();
    }

    @Override
    public List<SProvider> getServiceProvidersWithServiceType(int serviceType) {
        List<SProvider> filteredSp = new ArrayList<SProvider>();

        for(SProvider sp: getServiceProviders()){
           if(hasAptitude(sp,serviceType)){
               filteredSp.add(sp);
           }
        }
        return filteredSp;

    }

    @Override
    public SProvider getServiceProviderWithUserId(int userId) {
        Optional<SProvider> sProvider = sProviderDao.getServiceProviderWithUserId(userId);
        if(sProvider.isPresent()) {
            return sProvider.get();
        }
        return null;
    }

    @Override
    public boolean insertReview(int userId, int aptitudeId, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {
        return reviewDao.insertReview(userId,aptitudeId,quality,cleanness,price,punctuality,treatment,comment);
    }

    @Override
    public boolean addAptitude(int userId, int serviceType, String description) {
        return aptitudeDao.insertAptitude(userId,serviceType,description);
    }

    @Override
    public boolean removeAptitude(int userId, int serviceType) {
        int aptitudeId = aptitudeDao.getAptitudeId(userId,serviceType);
        if(aptitudeId==-1){
            return false;
        }
        return aptitudeDao.removeAptitude(aptitudeId);
    }

    public List<ServiceType> getServiceTypes(){
        return sTypeDao.getServiceTypes();
    }

    @Override
    public boolean updateDescriptionOfAptitude(int aptId, String description) {
        return aptitudeDao.updateDescriptionOfAptitude(aptId,description);
    }

    @Override
    public boolean updateServiceTypeOfAptitude(int aptId, int stId){
        return aptitudeDao.updateServiceTypeOfAptitude(aptId,stId);
    }

    @Override
    public boolean removeWorkingZoneOfProvider(int userId, int ngId) {
        return wZoneDao.removeWorkingZoneOfProvider(userId,ngId);
    }

    @Override
    public int getServiceProviderId(int userId) {
        if(userId == -1) {
            return -1;
        }

        SProvider sProvider = getServiceProviderWithUserId(userId);
        if(sProvider == null) {
            return -1;
        }
        return sProvider.getId();
    }

    @Override
    public boolean isServiceProvider(int userId) {
        return getServiceProviderWithUserId(userId) != null;
    }

    @Override
    public boolean insertWorkingZoneOfProvider(int userId, int ngId) {
        return wZoneDao.insertWorkingZoneOfProvider(userId,ngId);
    }

    @Override
    public List<SProvider> getServiceProvidersWorkingIn(int neighborhood) {
            return wZoneDao.getServiceProvidersWorkingIn(neighborhood);
    }

    @Override
    public List<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId) {
        return sProviderDao.getServiceProvidersByNeighborhoodAndServiceType(ngId, stId);
    }

    private boolean hasAptitude(SProvider sp,int stId){
        for(Aptitude ap :sp.getAptitudes()){
            if(ap.getService().getServiceTypeId() == stId){
                return true;
            }
        }
        return false;
    }

    private boolean hasWorkingZone(SProvider sp,int wzId){
           for(Neighborhood ne :sp.getNeighborhoods()){
            if(ne.getNgId() == wzId){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Review> getReviewsOfServiceProvider(int sproviderId) {
        Optional<SProvider> provider = sProviderDao.getServiceProviderWithUserId(sproviderId);
        if(!provider.isPresent()){
            return null;
        }
        List<Review> reviews = new ArrayList<>();

        for(Aptitude aptitude : aptitudeDao.getAptitudesOfUser(provider.get().getId())){
            reviews.addAll(aptitude.getReviews());
        }

        return reviews;
    }

    @Override
    public List<Aptitude> getAptitudesOfUser(int id) {
        return aptitudeDao.getAptitudesOfUser(id);
    }


}
