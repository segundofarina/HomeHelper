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
        Optional<User> user = Optional.ofNullable(em.find(User.class, userId));
        if (!user.isPresent()) {
            return Optional.empty();
        }
        final SProvider sp = new SProvider(user.get(), description, Collections.EMPTY_SET, new TreeSet<>());
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
                new HashSet<>(em.createQuery("select s from SProvider s left join fetch s.aptitudes a " +
                                "left join fetch a.reviews left join fetch s.workingZones as w" +
                                " where a.service.id = :stId AND w.neighborhood.ngId = :ngId"
                        , SProvider.class)
                        .setParameter("ngId", ngId)
                        .setParameter("stId", stId)
                        .getResultList());

    }

    @Override
    public boolean updateDescriptionOfServiceProvider(int userId, String description) {
        Optional<SProvider> sProviderOp = Optional.ofNullable(em.find(SProvider.class, userId));
        sProviderOp.ifPresent(sProvider -> sProvider.setDescription(description));

        return sProviderOp.isPresent();
    }
}
