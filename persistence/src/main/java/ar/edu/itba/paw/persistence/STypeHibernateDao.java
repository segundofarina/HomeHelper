package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.model.ServiceType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class STypeHibernateDao implements STypeDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public ServiceType create(String name) {
        ServiceType st = new ServiceType(name);
        em.persist(st);
        return st;
    }

    @Override
    public List<ServiceType> getServiceTypes() {
        final TypedQuery<ServiceType> query = em.createQuery("from ServiceType as st",ServiceType.class);
        return query.getResultList();
    }

    @Override
    public Optional<ServiceType> getServiceTypeWithId(int serviceTypeId) {
        final TypedQuery<ServiceType> query = em.createQuery("from ServiceType as st where st.id = :servicetypeid",ServiceType.class);
        query.setParameter("servicetypeid",serviceTypeId);
        if(query.getResultList().isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(query.getResultList().get(0));
    }

    @Override
    public Optional<ServiceType> updateServiceTypeWithId(int serviceTypeId, String newServiceName) {
        Optional<ServiceType> serviceType = Optional.ofNullable(em.find(ServiceType.class,serviceTypeId));
        if(!serviceType.isPresent()){
            return Optional.empty();
        }
        serviceType.get().setName(newServiceName);
        return serviceType;
    }
}
