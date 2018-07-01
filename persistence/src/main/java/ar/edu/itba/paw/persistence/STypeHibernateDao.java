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
        return em.createQuery("from ServiceType as st", ServiceType.class)
                .getResultList();
    }

    @Override
    public Optional<ServiceType> getServiceTypeWithId(int serviceTypeId) {
        return em.createQuery("from ServiceType as st where st.id = :servicetypeid", ServiceType.class)
                .setParameter("servicetypeid", serviceTypeId)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<ServiceType> updateServiceTypeWithId(int serviceTypeId, String newServiceName) {
        Optional<ServiceType> serviceTypeOp = Optional.ofNullable(em.find(ServiceType.class, serviceTypeId));
        serviceTypeOp.ifPresent(serviceType -> serviceType.setName(newServiceName));
        return serviceTypeOp;

    }
}
