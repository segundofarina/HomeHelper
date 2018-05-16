package ar.edu.itba.paw.interfaces.daos;

import java.util.Optional;

public interface DefaultImagesDao {

    public boolean insertImage(int id, byte[] image);

    public Optional<byte[]> getImage(int id);

}
