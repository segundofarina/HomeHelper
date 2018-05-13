package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

public interface UserService {

    User findById(int id);

    User findByUsername(String username);

    User create(String username, String password, String firstname, String lastname, String email, String phone,String address, byte[] image);

    boolean login(String username, String password);

    byte[] getProfileImage(int id);

}
