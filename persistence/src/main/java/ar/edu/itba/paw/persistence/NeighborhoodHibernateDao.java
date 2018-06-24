package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
import ar.edu.itba.paw.model.Neighborhood;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class NeighborhoodHibernateDao implements NeighborhoodDao {


    @PersistenceContext
    private EntityManager em;


    @Override
    public int insertNeighborhood(String ngname) {
        final Neighborhood ng = new Neighborhood(ngname);
        System.out.println(ng.getNgId());
         em.persist(ng);
        System.out.println(ng.getNgId());
         return ng.getNgId();
    }

    @Override
    public List<Neighborhood> getAllNeighborhoods() {
        final TypedQuery<Neighborhood> query = em.createQuery("SELECT n FROM Neighborhood as n ", Neighborhood.class);

        return query.getResultList();
    }
}
