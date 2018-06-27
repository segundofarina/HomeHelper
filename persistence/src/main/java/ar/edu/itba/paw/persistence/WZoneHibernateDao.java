package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.WZoneDao;
import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


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
       return em.createQuery("select wz.neighborhood from WorkingZone as wz where wz.sProvider.id = :providerid",Neighborhood.class)
             .setParameter("providerid",providerId)
             .getResultList();

    }

    @Override
    public List<SProvider> getServiceProvidersWorkingIn(int ngId) {
       return em.createQuery("select wz.sProvider from WorkingZone as wz where wz.neighborhood.ngId = :ngId",SProvider.class)
        .setParameter("ngId",ngId)
        .getResultList();

    }

    @Override
    public boolean removeWorkingZoneOfProvider(int userId, int ngId) {
        final Optional<WorkingZone> workingZoneOp =
                em.createQuery("from WorkingZone as wz where wz.neighborgood.ngId = :ngId and wz.user.id = :userId", WorkingZone.class)
                        .setParameter("ngId", ngId)
                        .setParameter("userId", userId)
                        .getResultList()
                        .stream()
                        .findFirst();
        workingZoneOp.ifPresent(workingZone -> em.remove(workingZone));
        return workingZoneOp.isPresent();
    }

}
