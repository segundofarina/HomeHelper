package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.WZoneDao;
import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Transactional
@Repository
public class WZoneHibernateDao implements WZoneDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean insertWorkingZoneOfProvider(int userId, int ngId) {
        SProvider user = em.find(SProvider.class,userId);
        if(user == null){
            return false;
        }
        Neighborhood neighborhood = em.find(Neighborhood.class,ngId);
        if(neighborhood == null){
            return false;
        }
        WorkingZone wz = new WorkingZone(user,neighborhood);
        em.persist(wz);
        return true;
    }

    @Override
    public List<Neighborhood> getWorkingZonesOfProvider(int providerId) {
       final TypedQuery<Neighborhood> query = em.createQuery("select wz.neighborhood from WorkingZone as wz where wz.sProvider.id = :providerid",Neighborhood.class);
       query.setParameter("providerid",providerId);
       return query.getResultList();
    }

    @Override
    public List<SProvider> getServiceProvidersWorkingIn(int ngId) {
       final TypedQuery<SProvider> query = em.createQuery("select wz.sProvider from WorkingZone as wz where wz.neighborhood.ngId = :ngId",SProvider.class);
        query.setParameter("ngId",ngId);
       return query.getResultList();
    }

    @Override
    public boolean removeWorkingZoneOfProvider(int userId, int ngId) {
        final TypedQuery<WorkingZone> query = em.createQuery("from WorkingZone as wz where wz.neighborgood.ngId = :ngId and wz.user.id = :userId",WorkingZone.class);
        query.setParameter("ngId",ngId);
        query.setParameter("userId",userId);
        if(query.getSingleResult() == null){
            return false;
        }else{
            em.remove(query.getSingleResult());
            return true;
        }
    }
}
