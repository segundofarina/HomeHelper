package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.ServiceType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class AptitudeHibernateDao implements AptitudeDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public Set<Aptitude> getAptitudesOfUser(int id) {
        return new HashSet<Aptitude>(em.createQuery("from Aptitude as a left join fetch a.reviews where a.sProvider.id = :userid", Aptitude.class)
                .setParameter("userid", id)
                .getResultList());
    }

    @Override
    public Aptitude insertAptitude(int sProviderId, int serviceId, String description) {
        Optional<SProvider> sp = Optional.ofNullable(em.find(SProvider.class, sProviderId));
        if (!sp.isPresent()) {
            return null;
        }
        Optional<ServiceType> st = Optional.ofNullable(em.find(ServiceType.class, serviceId));
        if (!st.isPresent()) {
            return null;
        }
        final Aptitude user = new Aptitude(sp.get(), st.get(), description, Collections.EMPTY_SET);
        em.persist(user);
        return user;
    }

    @Override
    public boolean updateDescriptionOfAptitude(int aptId, String description) {
        Optional<Aptitude> aptitude = Optional.ofNullable(em.find(Aptitude.class, aptId));
        if (!aptitude.isPresent() || description == null) {
            return false;
        }
        aptitude.get().setDescription(description);
        return true;
    }

    @Override
    public boolean updateServiceTypeOfAptitude(int aptId, int stId) {
        Optional<Aptitude> aptitude = Optional.ofNullable(em.find(Aptitude.class, aptId));
        if (!aptitude.isPresent()) {
            return false;
        }
        Optional<ServiceType> st = Optional.ofNullable(em.find(ServiceType.class, stId));
        if (!st.isPresent()) {
            return false;
        }
        aptitude.get().setService(st.get());
        return true;
    }

    @Override
    public boolean removeAptitude(int aptId) {
        Aptitude apt = em.find(Aptitude.class, aptId);
        if (apt == null) {
            return false;
        } else {
            em.remove(apt);
            return true;
        }
    }

    @Override
    public int getAptitudeId(int userId, int stId) {
        final List<Aptitude> list = em.createQuery("select a from Aptitude as a left join fetch a.reviews where a.sProvider.id = :userid and a.service.id = :stid", Aptitude.class)
                .setParameter("userid", userId)
                .setParameter("stid", stId)
                .getResultList();

        if (list.size() != 0) {
            return list.get(0).getId();
        } else {
            return -1;
        }
    }

    @Override
    public Optional<Aptitude> getAptitude(int id) {
        return Optional.ofNullable(em.find(Aptitude.class, id));
    }
}
