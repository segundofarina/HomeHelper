package ar.edu.itba.paw.interfaces.services;

import java.util.Optional;

public interface DefaultImagesService {


    public boolean insertImage(int id, byte[] image);

    public Optional<byte[]> getImage(int id);
}
