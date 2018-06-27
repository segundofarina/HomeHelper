package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class SProviderHibernateDao implements SProviderDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<SProvider> create(int userId, String description) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        if(!user.isPresent()){
            return Optional.empty();
        }
        final SProvider sp =new SProvider(user.get(),description, Collections.EMPTY_SET, Collections.EMPTY_SET);
        em.persist(sp);
        return Optional.of(sp);
    }

    @Override
    public Set<SProvider> getServiceProviders() {
        return new HashSet<>(em.createQuery("from SProvider", SProvider.class)
         .getResultList());
    }

    @Override
    public Optional<SProvider> getServiceProviderWithUserId(int userId) {
        return Optional.ofNullable(em.find(SProvider.class, userId));
    }

    @Override
    public Set<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId) {
        return
                new HashSet<>(em.createQuery("select s from SProvider s join fetch s.aptitudes a join fetch a.reviews" +
                        " join fetch s.workingZones as w where a.service.id = :stId AND w.neighborhood.ngId = :ngId"
                        ,SProvider.class)
                        .setParameter("ngId",ngId)
                        .setParameter("stId",stId)
                        .getResultList());

//        final TypedQuery<SProvider> query = em.createQuery("from SProvider sp natural join sp.workingZones wz natural join sp.aptitudes ap where wz.neighborhood.ngId = :ngId and ap.service.id = :stId",SProvider.class);
//        query.setParameter("ngId",ngId);
//        query.setParameter("stId",stId);
    }

    @Override
    public boolean updateDescriptionOfServiceProvider(int userId, String description) {
        Optional<SProvider> sProviderOp = Optional.ofNullable(em.find(SProvider.class,userId));
        sProviderOp.ifPresent(sProvider -> sProvider.setDescription(description));

        return sProviderOp.isPresent();
    }
}
