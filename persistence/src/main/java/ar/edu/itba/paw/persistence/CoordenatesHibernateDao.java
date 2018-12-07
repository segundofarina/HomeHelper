package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.CoordenatesDao;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class CoordenatesHibernateDao implements CoordenatesDao {
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordenatesHibernateDao.class);

    @Transactional
    @Override
    public boolean insertCoordenatesOfProvider(int providerId, List<CoordenatesPoint> coordenatesPointSet) {

        SProvider sProvider = em.find(SProvider.class, providerId);
        if (sProvider == null) {
            return false;
        }

        sProvider.getCoordenates().clear();
        em.flush();
        sProvider.getCoordenates().addAll(coordenatesPointSet);


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
