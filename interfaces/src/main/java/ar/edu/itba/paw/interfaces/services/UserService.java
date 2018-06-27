package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.User;

public interface UserService {


    User findById(int id);

    User findByUsername(String username);

    User create(String username, String password, String firstname, String lastname, String email, String phone,String address, byte[] image);

    boolean login(String username, String password);

    byte[] getProfileImage(int id);

    boolean updatePasswordOfUser(int userId, String password);

    boolean updateFirstNameOfUser(int userId, String firstname);

    boolean updateLastNameOfUser(int userId, String lastname);

    boolean updateEmailOfUser(int userId, String email);

    boolean updatePhoneOfUser(int userId, String phone);

    boolean updateImageOfUser(int userId, byte [] image);

    boolean updateAddressOfUser(int userId, String address);
}
