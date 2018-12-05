package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Transactional
    @Override
    public boolean addCoordenates(int providerId, Set<CoordenatesPoint> coordenatesPoints) {
        return coordenatesDao.insertCoordenatesOfProvider(providerId, coordenatesPoints);
    }

    @Transactional
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

//    private boolean isLatLngInPolygon(double lat, double lng, List<CoordenatesPoint> polygon) {
//        double lengthToPoint[] = new double[polygon.size()];
//        double sideLength[] = new double[polygon.size()];
//        double anglesSum = 0;
//
//        /* Get distance to point, distance to side and angles */
//        for (int i = 0; i < polygon.size(); i++) {
//            lengthToPoint[i] = Math.sqrt(Math.pow(polygon.get(i).getLat() - lat, 2) + Math.pow(polygon.get(i).getLng() - lng, 2));
//
//            if (i < polygon.size() - 1) {
//                sideLength[i] = Math.sqrt(Math.pow(polygon.get(i + 1).getLat() - polygon.get(i).getLat(), 2) + Math.pow(polygon.get(i + 1).getLng() - polygon.get(i).getLng(), 2));
//
//            } else {
//                int lastSide = polygon.size() - 1;
//                sideLength[lastSide] = Math.sqrt(Math.pow(polygon.get(0).getLat() - polygon.get(lastSide).getLat(), 2) + Math.pow(polygon.get(0).getLng() - polygon.get(lastSide).getLng(), 2));
//            }
//        }
//
//        for (int i = 0; i < polygon.size(); i++) {
//            if (i < polygon.size() - 1) {
//                anglesSum += ((180 / (Math.PI))) * Math.acos((Math.pow(lengthToPoint[i], 2) + Math.pow(lengthToPoint[i + 1], 2) - Math.pow(sideLength[i], 2)) / (2 * lengthToPoint[i] * lengthToPoint[i + 1]));
//            } else {
//                int lastSide = polygon.size() - 1;
//                anglesSum += ((180 / (Math.PI))) * Math.acos((Math.pow(lengthToPoint[lastSide], 2) + Math.pow(lengthToPoint[0], 2) - Math.pow(sideLength[lastSide], 2)) / (2 * lengthToPoint[0] * lengthToPoint[lastSide]));
//            }
//        }
//
//        if (anglesSum >= 360 - 0.00001 && anglesSum <= 360 + 0.00001) {
//            return true;
//        }
//
//        return false;
//    }

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


        double latMin = polygon.stream().map(CoordenatesPoint::getLat).min(Comparator.comparingDouble(x-> x)).orElse(0.0);
        double latMax = polygon.stream().map(CoordenatesPoint::getLat).max(Comparator.comparingDouble(x-> x)).orElse(0.0);
        double lngMin = polygon.stream().map(CoordenatesPoint::getLng).min(Comparator.comparingDouble(x-> x)).orElse(0.0);
        double lngMax = polygon.stream().map(CoordenatesPoint::getLng).max(Comparator.comparingDouble(x-> x)).orElse(0.0);

        if (lat < latMin || lat> latMax || lng < lngMin || lng > lngMax) {
            // Definitely not within the polygon!
            return false;
        }

        Stream<Line> sides = IntStream.range(1,polygon.size()).mapToObj(i ->
                new Line(polygon.get(i-1).getLng(),
                        polygon.get(i-1).getLat(),
                        polygon.get(i).getLng(),
                        polygon.get(i).getLat()));

        Line mainLine = new Line(lngMin-1,lat,lng,lat);
        long intersections = sides.filter(s -> areIntersecting(s,mainLine)).count();


        if ((intersections %2 ) == 1) {
            // Inside of polygon
            return true;
        } else {
            // Outside of polygon
            return false;
        }

    }

    private boolean areIntersecting(Line s1, Line s2){
        return areIntersecting(
                s1.x1,s1.y1,s1.x2,s1.y2,
                s2.x1,s2.y1,s2.x2,s2.y2);
    }

    //code taken from https://stackoverflow.com/questions/217578/how-can-i-determine-whether-a-2d-point-is-within-a-polygon

    private boolean areIntersecting(
            double v1x1, double v1y1, double v1x2, double v1y2,
            double v2x1, double v2y1, double v2x2, double v2y2
    ) {
        double d1, d2;
        double a1, a2, b1, b2, c1, c2;

        // Convert vector 1 to a line (line 1) of infinite length.
        // We want the line in linear equation standard form: A*x + B*y + C = 0
        // See: http://en.wikipedia.org/wiki/Linear_equation
        a1 = v1y2 - v1y1;
        b1 = v1x1 - v1x2;
        c1 = (v1x2 * v1y1) - (v1x1 * v1y2);

        // Every point (x,y), that solves the equation above, is on the line,
        // every point that does not solve it, is not. The equation will have a
        // positive result if it is on one side of the line and a negative one
        // if is on the other side of it. We insert (x1,y1) and (x2,y2) of vector
        // 2 into the equation above.
        d1 = (a1 * v2x1) + (b1 * v2y1) + c1;
        d2 = (a1 * v2x2) + (b1 * v2y2) + c1;

        // If d1 and d2 both have the same sign, they are both on the same side
        // of our line 1 and in that case no intersection is possible. Careful,
        // 0 is a special case, that's why we don't test ">=" and "<=",
        // but "<" and ">".
        if (d1 > 0 && d2 > 0) return false;
        if (d1 < 0 && d2 < 0) return false;

        // The fact that vector 2 intersected the infinite line 1 above doesn't
        // mean it also intersects the vector 1. Vector 1 is only a subset of that
        // infinite line 1, so it may have intersected that line before the vector
        // started or after it ended. To know for sure, we have to repeat the
        // the same test the other way round. We start by calculating the
        // infinite line 2 in linear equation standard form.
        a2 = v2y2 - v2y1;
        b2 = v2x1 - v2x2;
        c2 = (v2x2 * v2y1) - (v2x1 * v2y2);

        // Calculate d1 and d2 again, this time using points of vector 1.
        d1 = (a2 * v1x1) + (b2 * v1y1) + c2;
        d2 = (a2 * v1x2) + (b2 * v1y2) + c2;

        // Again, if both have the same sign (and neither one is 0),
        // no intersection is possible.
        if (d1 > 0 && d2 > 0) return true;
        if (d1 < 0 && d2 < 0) return true;

        // If we get here, only two possibilities are left. Either the two
        // vectors intersect in exactly one point or they are collinear, which
        // means they intersect in any number of points from zero to infinite.
        if ((a1 * b2) - (a2 * b1) == 0.0) return false;

        // If they are not collinear, they must intersect in exactly one point.
        return true;
    }

    private class Line{
        double x1;
        double y1;
        double x2;
        double y2;

        public Line(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
}
