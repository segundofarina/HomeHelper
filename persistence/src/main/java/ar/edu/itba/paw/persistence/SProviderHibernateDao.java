package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.utils.AptitudeRow;
import ar.edu.itba.paw.persistence.utils.ProviderRow;
import ar.edu.itba.paw.persistence.utils.ReviewRow;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SProviderHibernateDao implements SProviderDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<SProvider> create(int userId, String description) {
        Optional<User> user = Optional.ofNullable(em.find(User.class, userId));
        if (!user.isPresent()) {
            return Optional.empty();
        }
        final SProvider sp = new SProvider(user.get(), description, Collections.EMPTY_SET, new TreeSet<>());
        em.persist(sp);
        return Optional.of(sp);
    }

    @Override
    public List<SProvider> getServiceProviders() {
        List<ProviderRow> providers = em.createQuery(
                " select new ar.edu.itba.paw.persistence.utils.ProviderRow(u.id,u.username,u.password,u.firstname,u.lastname,u.email,u.phone,u.address,u.verified,p.description)" +
                        " from SProvider as p  INNER JOIN FETCH  User as u on p.id=u.id",
                ProviderRow.class)
                .getResultList();


        Map<Integer,List<CoordenatesPoint>> coordenatesMap = em.createQuery("FROM CoordenatesPoint",CoordenatesPoint.class).getResultList()
                .stream().collect(Collectors.groupingBy(CoordenatesPoint::getUserId));

        Map<Integer,List<ReviewRow>>reviewsRowMap = em.createQuery(
                "select new ar.edu.itba.paw.persistence.utils.ReviewRow(r.id,r.user.id,r.aptitude.id,r.appointmentId,r.comment,r.quality, r.cleanness,r.price,r.punctuality,r.treatment,r.date," +
                        "u.username,u.password,u.firstname,u.lastname,u.email,u.phone,u.address,u.verified)" +
                        " FROM Review as r INNER JOIN FETCH User as u on r.user.id=u.id",ReviewRow.class)
                .getResultList()
                .stream()
                .collect(Collectors.groupingBy(ReviewRow::getAptitudeId));

        Map<Integer,List<AptitudeRow>> aptitudesRowMap = em.createQuery(
                "select new ar.edu.itba.paw.persistence.utils.AptitudeRow(a.id,s.id,s.name,a.description,a.sProvider.id)" +
                        " from Aptitude as a INNER JOIN FETCH ServiceType as s on a.service.id=s.id",AptitudeRow.class)
                .getResultList()
                .stream()
                .collect(Collectors.groupingBy(AptitudeRow::getUserid));



        /* adding the list of reviews each aptitude row */
        aptitudesRowMap.forEach( (providerId, list) ->
                    list.forEach(ap -> {
                        Set<Review> reviews = reviewsRowMap
                                .getOrDefault(ap.getId(), new ArrayList<>())
                                .stream()
                                .map(row -> new Review(row.getId(), row.getQuality(), row.getCleanness(), row.getPrice(), row.getPunctuality(), row.getTreatment(), row.getComment(), row.getDate(), row.getUser(), null,row.getAptitudeId()))
                                .collect(Collectors.toSet());
                        ap.setReviews(reviews);
                    })
        );



        Map<Integer,Set<Aptitude>> aptitudes = new HashMap<>();

        /*Filling apitutdes map*/
        aptitudesRowMap.forEach((provId, list)->
                aptitudes.put(provId, list.stream()
                           .map(apRow -> new Aptitude(
                                   apRow.getId(),
                                   apRow.getServiceType(),
                                   apRow.getDescription(),
                                   apRow.getReviews()))
                           .collect(Collectors.toSet())
           ));



        return  providers.stream().map(
                p-> new SProvider(
                        new User(p.getId(),p.getUsername(),p.getPassword(),p.getFirstname(),p.getLastname(),p.getEmail(),p.getPhone(),p.getAddress(),p.isVerified()),
                        p.getDescription(),
                        aptitudes.getOrDefault(p.getId(),new HashSet<>()),
                        new TreeSet<>(coordenatesMap.getOrDefault(p.getId(), new ArrayList<>()))
                )).collect(Collectors.toList());


    }

    @Override
    public Optional<SProvider> getServiceProviderWithUserId(int userId) {
        return Optional.ofNullable(em.find(SProvider.class, userId));
    }


    @Override
    public boolean updateDescriptionOfServiceProvider(int userId, String description) {
        Optional<SProvider> sProviderOp = Optional.ofNullable(em.find(SProvider.class, userId));
        sProviderOp.ifPresent(sProvider -> sProvider.setDescription(description));

        return sProviderOp.isPresent();
    }

}
