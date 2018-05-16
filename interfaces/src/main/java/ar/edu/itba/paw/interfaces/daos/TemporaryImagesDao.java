package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.TemporaryImage;

import java.util.Optional;

public interface TemporaryImagesDao {

    TemporaryImage insertImage(byte[] image);

    boolean deleteImage(int imageId);

    Optional<TemporaryImage> getImage(int imageId);
}
