package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;

import java.util.Optional;

public interface UserDao {
    /**
     * Creates an {@link User} given this params
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @return Created User or null if it's a duplicate
     */
    User create(String username, String password, String firstName, String lastName, String email, String phone, String address);


    Optional<User> verifyUser(int userId);

    /**
     * Returns an {@link User} given it's username
     * @param username - username User is registered with
     * @return The corresponding User or Empty if it doesn't exist
     */
    Optional<User> findByUsername(String username);


    Optional<User> findById(int id);

    Optional<UserImage> getImage(int userId);

    boolean updatePasswordOfUser(int userId, String password);

    boolean updateFirstNameOfUser(int userId, String firstname);

    boolean updateLastNameOfUser(int userId, String lastname);

    boolean updateEmailOfUser(int userId, String email);

    boolean updatePhoneOfUser(int userId, String phone);

    boolean updateImageOfUser(int userId, byte [] image);

    boolean updateAddressOfUser(int userId, String address);

}
