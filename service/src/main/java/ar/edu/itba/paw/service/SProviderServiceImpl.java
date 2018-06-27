package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    AppointmentDao appointmentDao;

    @Transactional
    @Override
    public SProvider create(int userId, String description) {
        return sProviderDao.create(userId,description).get();
    }

    @Transactional
    @Override
    public Set<SProvider> getServiceProviders() {
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

    @Transactional
    @Override
    public SProvider getServiceProviderWithUserId(int userId) {
        Optional<SProvider> sProvider = sProviderDao.getServiceProviderWithUserId(userId);
        if(sProvider.isPresent()) {
            return sProvider.get();
        }
        return null;
    }


    @Transactional
    @Override
    public void addAptitude(int userId, int serviceType, String description) {
        aptitudeDao.insertAptitude(userId,serviceType,description);
    }

    @Transactional
    @Override
    public boolean removeAptitude(int userId, int serviceType) {
        int aptitudeId = aptitudeDao.getAptitudeId(userId,serviceType);
        if(aptitudeId==-1){
            return false;
        }
        return aptitudeDao.removeAptitude(aptitudeId);
    }

    @Transactional
    @Override
    public void updateDescriptionOfServiceProvider(int userId, String description) {
        sProviderDao.updateDescriptionOfServiceProvider(userId,description);
    }

    @Override
    public List<Review> getLatestReviewsOfServiceProvider(int providerId) {
        final List<Review> reviews = new ArrayList<>(getReviewsOfServiceProvider(providerId));
        int start = 0, end = reviews.size() >= 4 ? 4 : reviews.size();

        return reviews.subList(start, end);
    }


    @Transactional
    @Override
    public boolean updateDescriptionOfAptitude(int aptId, String description) {
        return aptitudeDao.updateDescriptionOfAptitude(aptId,description);
    }

    @Transactional
    @Override
    public boolean updateServiceTypeOfAptitude(int aptId, int stId){
        return aptitudeDao.updateServiceTypeOfAptitude(aptId,stId);
    }

    @Transactional
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

    @Transactional
    @Override
    public void insertWorkingZoneOfProvider(int userId, int ngId) {
        wZoneDao.insertWorkingZoneOfProvider(userId,ngId);
    }

    @Transactional
    @Override
    public List<SProvider> getServiceProvidersWorkingIn(int neighborhood) {
            return wZoneDao.getServiceProvidersWorkingIn(neighborhood);
    }

    @Transactional
    @Override
    public Set<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId) {
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


    @Transactional
    @Override
    public Set<Review> getReviewsOfServiceProvider(int sproviderId) {
        Optional<SProvider> provider = sProviderDao.getServiceProviderWithUserId(sproviderId);
        if(!provider.isPresent()){
            return null;
        }
        Set<Review> reviews = new HashSet<>();

        for(Aptitude aptitude : aptitudeDao.getAptitudesOfUser(provider.get().getId())){
            reviews.addAll(aptitude.getReviews());
        }

        return reviews;
    }

    @Transactional
    @Override
    public Set<Aptitude> getAptitudesOfUser(int id) {
        return aptitudeDao.getAptitudesOfUser(id);
    }


}
