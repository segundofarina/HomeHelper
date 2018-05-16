package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.TemporaryImage;

import java.util.Optional;

public interface TempImagesService {

    TemporaryImage insertImage(byte[] image);

    boolean deleteImage(int imageId);

    TemporaryImage getImage(int imageId);
}
