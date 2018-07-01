package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TemporaryImagesDao;
import ar.edu.itba.paw.model.TemporaryImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TemporaryImagesHibernateDao implements TemporaryImagesDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public TemporaryImage insertImage(byte[] image) {
        TemporaryImage temporaryImage = new TemporaryImage(image);
        em.persist(temporaryImage);
        return temporaryImage;
    }

    @Override
    public boolean deleteImage(int imageId) {
        Optional<TemporaryImage> temporaryImageOp = Optional.ofNullable(em.find(TemporaryImage.class, imageId));
        temporaryImageOp.ifPresent(temporaryImage -> em.remove(temporaryImage));
        return temporaryImageOp.isPresent();
    }

    @Override
    public Optional<TemporaryImage> getImage(int imageId) {
        return Optional.ofNullable(em.find(TemporaryImage.class, imageId));
    }
}
