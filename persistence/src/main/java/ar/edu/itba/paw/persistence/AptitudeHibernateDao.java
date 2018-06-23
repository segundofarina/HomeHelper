package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.ServiceType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class AptitudeHibernateDao implements AptitudeDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Aptitude> getAptitudesOfUser(int id) {
        final TypedQuery<Aptitude> query = em.createQuery("from aptitudes as a where a.userid = :userid", Aptitude.class);
        query.setParameter("userid",id);
        return query.getResultList();
    }

    @Override
    public boolean insertAptitude(int sProviderId, int serviceId, String description) {
        Optional<SProvider> sp = Optional.of(em.find(SProvider.class,sProviderId));
        if(!sp.isPresent()){
            return false;
        }
        Optional<ServiceType> st = Optional.of(em.find(ServiceType.class,serviceId));
        if(!st.isPresent()){
            return false;
        }
        final Aptitude user = new Aptitude(sp.get(),st.get(),description,new ArrayList<>());
        em.persist(user);
        return true;
    }

    @Override
    public boolean updateDescriptionOfAptitude(int aptId, String description) {
        Optional<Aptitude> aptitude = Optional.of(em.find(Aptitude.class,aptId));
        if(!aptitude.isPresent()){
            return false;
        }
        em.getTransaction().begin();
        aptitude.get().setDescription(description);
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean updateServiceTypeOfAptitude(int aptId, int stId) {
        Optional<Aptitude> aptitude = Optional.of(em.find(Aptitude.class,aptId));
        if(!aptitude.isPresent()){
            return false;
        }
        Optional<ServiceType> st = Optional.of(em.find(ServiceType.class,stId));
        if(!st.isPresent()){
            return false;
        }
        em.getTransaction().begin();
        aptitude.get().setService(st.get());
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean removeAptitude(int aptId) {
        return false;
    }

    @Override
    public int getAptitudeId(int userId, int stId) {
        final TypedQuery<Aptitude> query = em.createQuery("from aptitudes as a where a.userid = :userid and a.serviceTypeId = :stid", Aptitude.class);
        query.setParameter("userid",userId);
        query.setParameter("stid",stId);
        return query.getResultList().get(0).getId();
    }

    @Override
    public Optional<Aptitude> getAptitude(int id) {
        return  Optional.of(em.find(Aptitude.class,id));
    }
}
