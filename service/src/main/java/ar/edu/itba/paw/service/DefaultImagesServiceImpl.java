package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.DefaultImagesDao;
import ar.edu.itba.paw.interfaces.services.DefaultImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultImagesServiceImpl implements DefaultImagesService{

    @Autowired
    DefaultImagesDao defaultImagesDao;

    @Override
    public boolean insertImage(int id, byte[] image) {
        return defaultImagesDao.insertImage(id,image);
    }

    @Override
    public Optional<byte[]> getImage(int id) {
        return defaultImagesDao.getImage(id);
    }
}
