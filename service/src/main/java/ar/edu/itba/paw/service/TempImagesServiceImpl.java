package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.TemporaryImagesDao;
import ar.edu.itba.paw.interfaces.services.TempImagesService;
import ar.edu.itba.paw.model.TemporaryImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TempImagesServiceImpl implements TempImagesService {


    @Autowired
    private TemporaryImagesDao temporaryImagesDao;


    @Transactional
    @Override
    public TemporaryImage insertImage(byte[] image) {
        return temporaryImagesDao.insertImage(image);
    }

    @Transactional
    @Override
    public boolean deleteImage(int imageId) {
        return temporaryImagesDao.deleteImage(imageId);
    }

    @Transactional
    @Override
    public TemporaryImage getImage(int imageId) {
        Optional<TemporaryImage> temp = temporaryImagesDao.getImage(imageId);

        if (temp.isPresent()) {
            return temp.get();
        } else {
            return null;
        }
    }


}
