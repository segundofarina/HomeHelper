package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
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
        final SProvider sp =new SProvider(user.get(),description, new ArrayList<>(), new ArrayList<>());
        em.persist(sp);
        return Optional.of(sp);
    }

    @Override
    public List<SProvider> getServiceProviders() {
        final TypedQuery<SProvider> query = em.createQuery("from SProvider", SProvider.class);
        return query.getResultList();
    }

    @Override
    public Optional<SProvider> getServiceProviderWithUserId(int userId) {
        return Optional.ofNullable(em.find(SProvider.class, userId));
    }

    @Override
    public List<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId) {
        return null;
    }

    @Override
    public boolean updateDescriptionOfServiceProvider(int userId, String description) {
        Optional<SProvider> user = Optional.ofNullable(em.find(SProvider.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().getUser().setAddress(description);
        return true;
    }
}
