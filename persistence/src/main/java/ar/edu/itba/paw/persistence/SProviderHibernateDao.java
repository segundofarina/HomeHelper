package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<SProvider> getServiceProviders() {
        return em.createQuery("from SProvider", SProvider.class)
                .getResultList();
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
