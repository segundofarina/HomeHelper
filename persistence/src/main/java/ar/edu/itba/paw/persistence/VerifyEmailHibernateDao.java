package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.VerifyEmailDao;
import ar.edu.itba.paw.model.MailKey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VerifyEmailHibernateDao implements VerifyEmailDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean insert(int userId, String key) {
        MailKey mailKey = new MailKey(userId, key);
        em.persist(mailKey);
        return true;
    }

    @Override
    public Optional<String> getKey(int userId) {
        return Optional.ofNullable(em.find(MailKey.class, userId))
                .map(MailKey::getKey);

    }

    @Override
    public Optional<Integer> getUserId(String key) {
        return em.createQuery("FROM MailKey as m  where m.key = :keyval", MailKey.class)
                .setParameter("keyval", key)
                .getResultList()
                .stream()
                .findFirst()
                .map(MailKey::getUserId);

    }

    @Override
    public boolean deleteEntry(String key) {
        Optional<MailKey> mailKeyOp =
                em.createQuery("FROM MailKey as m  where m.key = :keyval", MailKey.class)
                        .setParameter("keyval", key)
                        .getResultList()
                        .stream()
                        .findFirst();

        mailKeyOp.ifPresent(mailKey -> em.remove(mailKey));

        return mailKeyOp.isPresent();


    }
}
