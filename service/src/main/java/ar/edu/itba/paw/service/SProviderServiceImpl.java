package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.service.polygonUtils.Point;
import ar.edu.itba.paw.service.polygonUtils.Polygon;
import ar.edu.itba.paw.model.utils.SizeListTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SProviderServiceImpl implements SProviderService {

    @Autowired
    SProviderDao sProviderDao;

    @Autowired
    AptitudeDao aptitudeDao;

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    CoordenatesDao coordenatesDao;

    private final static Logger LOGGER = LoggerFactory.getLogger(SProviderService.class);

    @Transactional
    @Override
    public SProvider create(int userId, String description) {

        Optional<SProvider> sp = sProviderDao.create(userId, description);
        return sp.isPresent() ? sp.get() : null;
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

    @Transactional
    @Override
    public boolean addCoordenates(int providerId, List<CoordenatesPoint> coordenatesPoints) {
        return coordenatesDao.insertCoordenatesOfProvider(providerId, coordenatesPoints);
    }

    @Transactional
    @Override
    public boolean deleteCoordenates(int providerId) {
        return coordenatesDao.deleteCoordenateOfProvideer(providerId);
    }

    @Transactional
    @Override
    public Optional<SProvider> create(int id, String description, Map<Integer, String> aptitudes, List<CoordenatesPoint> coordenates) {
        SProvider provider = create(id,description);

        if(provider == null){
            return Optional.empty();
        }

        for(Map.Entry<Integer,String> aptitude: aptitudes.entrySet()){
            aptitudeDao.insertAptitude(id,aptitude.getKey(),aptitude.getValue());
        }
        coordenatesDao.insertCoordenatesOfProvider(id,coordenates);

        return Optional.ofNullable(provider);
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


    private List<SProvider> getServiceProviders() {
        return sProviderDao.getServiceProviders();
    }
    private List<SProvider> getServiceProvidersWithServiceType(int serviceType) {
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
    public SizeListTuple<SProvider> getServiceProvidersByNeighborhood(double clientLocationLat, double clientLocationLng, int userId, int page, int pageSize) {
        List<SProvider> provider = getServiceProvidersByNeighborhood(clientLocationLat,clientLocationLng,userId);

        int start = (page-1) * pageSize;
        int end = start + pageSize;


        if(start > provider.size()){
            return new SizeListTuple<>(0,Collections.emptyList());
        }else if(end > provider.size()){
            return new SizeListTuple<>(provider.size(),provider.subList(start,provider.size()));
        }

        return new SizeListTuple<>(provider.size(),provider.subList(start,end));
    }
    private List<SProvider> getServiceProvidersByNeighborhood(double clientLocationLat, double clientLocationLng, int userId) {

        List<SProvider> allServiceProviders = getServiceProviders();

        List<SProvider> res = new ArrayList<>();

        for (SProvider sp : allServiceProviders) {
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

    @Transactional
    @Override
    public SizeListTuple<SProvider> getServiceProvidersByNeighborhoodAndServiceType(double clientLocationLat, double clientLocationLng, int stId, int userId, int page, int pageSize) {
        List<SProvider> provider = getServiceProvidersByNeighborhoodAndServiceType(clientLocationLat,clientLocationLng,stId,userId);

        int start = (page-1) * pageSize;
        int end = start + pageSize;


        if(start > provider.size()){
            return new SizeListTuple<>(0,Collections.emptyList());
        }else if(end > provider.size()){
            return new SizeListTuple<>(provider.size(),provider.subList(start,provider.size()));
        }

        return new SizeListTuple<>(provider.size(),provider.subList(start,end));
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

    @Transactional
    @Override
    public SizeListTuple<SProvider> getServiceProvidersByServiceType(int stId, int userId, int page, int pageSize) {
        List<SProvider> provider = getServiceProvidersByServiceType(stId,userId);

        int start = (page-1) * pageSize;
        int end = start + pageSize;


        if(start > provider.size()){
            return new SizeListTuple<>(0,Collections.emptyList());
        }else if(end > provider.size()){
            return new SizeListTuple<>(provider.size(),provider.subList(start,provider.size()));
        }

        return new SizeListTuple<>(provider.size(),provider.subList(start,end));
    }
    private List<SProvider> getServiceProvidersByServiceType(int stId, int userId) {
        List<SProvider> allServiceProviders = getServiceProvidersWithServiceType(stId);

        List<SProvider> res = new ArrayList<>();
        for (SProvider sp : allServiceProviders) {
            /* Avoid me in the list */
            if (userId < 0 || sp.getId() != userId) {
                res.add(sp);
            }
        }

        return res;
    }

    @Transactional
    @Override
    public SizeListTuple<SProvider> getServiceProviders(int userId, int page, int pageSize) {
        List<SProvider> provider = getServiceProviders(userId);

        int start = (page-1) * pageSize;
        int end = start + pageSize;


        if(start > provider.size()){
            return new SizeListTuple<>(0,Collections.emptyList());
        }else if(end > provider.size()){
            return new SizeListTuple<>(provider.size(),provider.subList(start,provider.size()));
        }

        return new SizeListTuple<>(provider.size(),provider.subList(start,end));
    }
    private List<SProvider> getServiceProviders(int userId) {
        List<SProvider> allServiceProviders = getServiceProviders();

        List<SProvider> res = new ArrayList<>();

        for (SProvider sp : allServiceProviders) {
            if (userId < 0 || sp.getId() != userId) {
                res.add(sp);
            }
        }

        return res;
    }

    @Transactional
    @Override
    public SizeListTuple<Review> getReviewsOfServiceProvider(int userId, Integer serviceTypeId, int page, int pageSize) {

        List<Review> reviews = getReviewsOfServiceProvider(userId,serviceTypeId);

        int start = (page-1) * pageSize;
        int end = start + pageSize;


        if(start > reviews.size()){
            return new SizeListTuple<>(0,Collections.emptyList());
        }else if(end > reviews.size()){
            return new SizeListTuple<>(reviews.size(),reviews.subList(start,reviews.size()));
        }

        return new SizeListTuple<>(reviews.size(),reviews.subList(start,end));
    }
    private List<Review> getReviewsOfServiceProvider(int userId, Integer serviceTypeId) {
        List<Review> reviews = new ArrayList<>(getReviewsOfServiceProvider(userId));

        List<Review> res = new ArrayList<>();


        for (Review review : reviews) {
            if(review.getAptitude().getService().getId() == serviceTypeId || serviceTypeId == -1){
                res.add(review);
            }
        }

        reviews.sort(new Comparator<Review>() {
            @Override
            public int compare(Review o1, Review o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return res;
    }

    @Transactional
    @Override
    public List<Review> getReviewsOfServiceProvider(int id, int page, int pageSize) {
        List<Review> reviews = new ArrayList<>(getReviewsOfServiceProvider(id));

        int start = page * pageSize;
        int end = start + pageSize;

        if(end > reviews.size()){
            return reviews.subList(start,reviews.size());
        }else if(start > reviews.size()){
            return reviews;
        }

        return reviews.subList(start,end);
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

    public boolean isLatLngInPolygon(double lat, double lng, List<CoordenatesPoint> polygon){
        Collections.sort(polygon,Comparator.comparingInt(CoordenatesPoint::getPosition));
        Polygon.Builder builder = Polygon.Builder();
        polygon.forEach(cor -> builder.addVertex(new Point(cor.getLng(),cor.getLat())));
        boolean ans;
        try{
            ans = builder.build().contains(new Point(lng,lat));
        }catch (RuntimeException e){
            ans = false;
            LOGGER.info("User has less than 3 coordenates");
        }
        return ans;

    }



}
