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
    STypeDao sTypeDao;

    @Autowired
    WZoneDao wZoneDao;

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    CoordenatesDao coordenatesDao;


    @Transactional
    @Override
    public SProvider create(int userId, String description) {

        Optional<SProvider> sp = sProviderDao.create(userId, description);
        return sp.isPresent() ? sp.get() : null;
    }

    @Transactional
    @Override
    public Set<SProvider> getServiceProviders() {
        return sProviderDao.getServiceProviders();
    }


    @Override
    public List<SProvider> getServiceProvidersWithServiceType(int serviceType) {
        List<SProvider> filteredSp = new ArrayList<SProvider>();

        for (SProvider sp : getServiceProviders()) {
            if (hasAptitude(sp, serviceType)) {
                filteredSp.add(sp);
            }
        }
        return filteredSp;

    }

    @Transactional
    @Override
    public SProvider getServiceProviderWithUserId(int userId) {
        Optional<SProvider> sProvider = sProviderDao.getServiceProviderWithUserId(userId);
        if (sProvider.isPresent()) {
            return sProvider.get();
        }
        return null;
    }


    @Transactional
    @Override
    public void addAptitude(int userId, int serviceType, String description) {
        aptitudeDao.insertAptitude(userId, serviceType, description);
    }

    @Transactional
    @Override
    public boolean removeAptitude(int userId, int serviceType) {
        int aptitudeId = aptitudeDao.getAptitudeId(userId, serviceType);
        if (aptitudeId == -1) {
            return false;
        }
        return aptitudeDao.removeAptitude(aptitudeId);
    }

    @Transactional
    @Override
    public void updateDescriptionOfServiceProvider(int userId, String description) {
        sProviderDao.updateDescriptionOfServiceProvider(userId, description);
    }

    @Override
    public List<Review> getLatestReviewsOfServiceProvider(int providerId) {
        final List<Review> reviews = new ArrayList<>(getReviewsOfServiceProvider(providerId));
        int start = 0, end = reviews.size() >= 4 ? 4 : reviews.size();

        return reviews.subList(start, end);
    }

    @Override
    public Aptitude getAptitudeOfProvider(int serviceTypeId, SProvider provider) {
        for (Aptitude ap : provider.getAptitudes()) {
            if (ap.getService().getServiceTypeId() == serviceTypeId) {
                return ap;
            }
        }
        return null;
    }

    @Override
    public List<Aptitude> getAllAptitudesExcept(int serviceTypeId, SProvider provider) {
        List<Aptitude> aptitudes = new ArrayList<>();
        for (Aptitude ap : provider.getAptitudes()) {
            if (ap.getService().getServiceTypeId() != serviceTypeId) {
                aptitudes.add(ap);
            }
        }
        return aptitudes;
    }

    @Override
    public boolean addCoordenates(int providerId, Set<CoordenatesPoint> coordenatesPoints) {
        return coordenatesDao.insertCoordenatesOfProvider(providerId, coordenatesPoints);
    }

    @Override
    public boolean deleteCoordenates(int providerId) {
        return coordenatesDao.deleteCoordenateOfProvideer(providerId);
    }

    public List<ServiceType> getServiceTypes() {
        return sTypeDao.getServiceTypes();
    }


    @Transactional
    @Override
    public boolean updateDescriptionOfAptitude(int aptId, String description) {
        return aptitudeDao.updateDescriptionOfAptitude(aptId, description);
    }

    @Transactional
    @Override
    public boolean updateServiceTypeOfAptitude(int aptId, int stId) {
        return aptitudeDao.updateServiceTypeOfAptitude(aptId, stId);
    }

    @Transactional
    @Override
    public boolean removeWorkingZoneOfProvider(int userId, int ngId) {
        return wZoneDao.removeWorkingZoneOfProvider(userId, ngId);
    }


    @Override
    public int getServiceProviderId(int userId) {
        if (userId == -1) {
            return -1;
        }

        SProvider sProvider = getServiceProviderWithUserId(userId);
        if (sProvider == null) {
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
        wZoneDao.insertWorkingZoneOfProvider(userId, ngId);
    }

    @Transactional
    @Override
    public List<SProvider> getServiceProvidersWorkingIn(int neighborhood) {
        return wZoneDao.getServiceProvidersWorkingIn(neighborhood);
    }

    @Transactional
    @Override
    public List<SProvider> getServiceProvidersByNeighborhoodAndServiceType(double clientLocationLat, double clientLocationLng, int stId, int userId, int page, int pageSize) {
        List<SProvider> provider = getServiceProvidersByNeighborhoodAndServiceType(clientLocationLat,clientLocationLng,stId,userId);

        int start = page * pageSize;
        int end = start + pageSize;

        if(end > provider.size()){
            return provider.subList(start,provider.size());
        }else if(start > provider.size()){
            return provider;
        }

        return provider.subList(start,end);
    }
    private List<SProvider> getServiceProvidersByNeighborhoodAndServiceType(double clientLocationLat, double clientLocationLng, int stId, int userId) {
        List<SProvider> allServiceProviders = getServiceProvidersWithServiceType(stId);

        List<SProvider> res = new ArrayList<>();
        for (SProvider sp : allServiceProviders) {
            /* Avoid me in the list */
            if (userId < 0 || sp.getId() != userId) {
                /* Check if service provider works in clientLocation */


                List<CoordenatesPoint> polygon = new ArrayList<>();
                polygon.addAll(sp.getCoordenates());

                if (isLatLngInPolygon(clientLocationLat, clientLocationLng, polygon)) {
                    res.add(sp);
                }
            }
        }

        return res;
    }

    private boolean isLatLngInPolygon(double lat, double lng, List<CoordenatesPoint> polygon) {
        double lengthToPoint[] = new double[polygon.size()];
        double sideLength[] = new double[polygon.size()];
        double anglesSum = 0;

        /* Get distance to point, distance to side and angles */
        for (int i = 0; i < polygon.size(); i++) {
            lengthToPoint[i] = Math.sqrt(Math.pow(polygon.get(i).getLat() - lat, 2) + Math.pow(polygon.get(i).getLng() - lng, 2));

            if (i < polygon.size() - 1) {
                sideLength[i] = Math.sqrt(Math.pow(polygon.get(i + 1).getLat() - polygon.get(i).getLat(), 2) + Math.pow(polygon.get(i + 1).getLng() - polygon.get(i).getLng(), 2));

            } else {
                int lastSide = polygon.size() - 1;
                sideLength[lastSide] = Math.sqrt(Math.pow(polygon.get(0).getLat() - polygon.get(lastSide).getLat(), 2) + Math.pow(polygon.get(0).getLng() - polygon.get(lastSide).getLng(), 2));
            }
        }

        for (int i = 0; i < polygon.size(); i++) {
            if (i < polygon.size() - 1) {
                anglesSum += ((180 / (Math.PI))) * Math.acos((Math.pow(lengthToPoint[i], 2) + Math.pow(lengthToPoint[i + 1], 2) - Math.pow(sideLength[i], 2)) / (2 * lengthToPoint[i] * lengthToPoint[i + 1]));
            } else {
                int lastSide = polygon.size() - 1;
                anglesSum += ((180 / (Math.PI))) * Math.acos((Math.pow(lengthToPoint[lastSide], 2) + Math.pow(lengthToPoint[0], 2) - Math.pow(sideLength[lastSide], 2)) / (2 * lengthToPoint[0] * lengthToPoint[lastSide]));
            }
        }

        if (anglesSum >= 360 - 0.00001 && anglesSum <= 360 + 0.00001) {
            return true;
        }

        return false;
    }

    private boolean hasAptitude(SProvider sp, int stId) {
        for (Aptitude ap : sp.getAptitudes()) {
            if (ap.getService().getServiceTypeId() == stId) {
                return true;
            }
        }
        return false;
    }


    @Transactional
    @Override
    public Set<Review> getReviewsOfServiceProvider(int sproviderId) {
        Optional<SProvider> provider = sProviderDao.getServiceProviderWithUserId(sproviderId);
        if (!provider.isPresent()) {
            return null;
        }
        Set<Review> reviews = new HashSet<>();

        for (Aptitude aptitude : aptitudeDao.getAptitudesOfUser(provider.get().getId())) {
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
