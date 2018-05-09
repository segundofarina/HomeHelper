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
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<SProvider> getServiceProvidersWithServiceType(int serviceType) {
        List<SProvider> filteredSp = new ArrayList<SProvider>();

        for(SProvider sp: getServiceProviders()){
            for(Aptitude ap :sp.getAptitudes()){
                if(ap.getService().getServiceTypeId() == serviceType){
                    filteredSp.add(sp);
                }
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
    public boolean addReviewToAptitude(int userId, int serviceType, int quality,int cleanness, int price, int punctuality, int treatment, String comment) {
        List<Aptitude> list = aptitudeDao.getAptitudesOfUser(userId);
        for(Aptitude a : list){
            if(a.getService().getServiceTypeId() == serviceType){
                return reviewDao.insertReview(userId,a.getId(),quality,cleanness,price,punctuality,treatment,comment);
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

    private static AptitudeForm getApt ( List<AptitudeForm> list, int servicetypeId){
        for (AptitudeForm apt : list){
            if(servicetypeId == apt.getServiceTypeid()){
                return apt;
            }
        }
        return null;
    }



}
