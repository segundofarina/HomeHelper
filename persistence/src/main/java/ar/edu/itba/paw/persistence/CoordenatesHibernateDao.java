package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.CoordenatesDao;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Repository
public class CoordenatesHibernateDao implements CoordenatesDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean insertCoordenatesOfProvider(int providerId, Set<CoordenatesPoint> coordenatesPointSet) {
        SProvider sProvider = em.find(SProvider.class, providerId);
        if (sProvider == null) {
            return false;
        }
        for (CoordenatesPoint cor : sProvider.getCoordenates()) {
            em.remove(cor);
        }

        for (CoordenatesPoint cor : coordenatesPointSet) {
            cor.setUserId(providerId);
            em.persist(new CoordenatesPoint(providerId, cor.getPosition(), cor.getLat(), cor.getLng()));
        }
        return true;
    }

    @Override
    public boolean deleteCoordenateOfProvideer(int providerId) {
        SProvider sProvider = em.find(SProvider.class, providerId);
        if (sProvider == null) {
            return false;
        }
        for (CoordenatesPoint cor : sProvider.getCoordenates()) {
            em.remove(cor);
        }
        return true;
    }

}
