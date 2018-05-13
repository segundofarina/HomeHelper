package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.User;

import java.sql.Blob;
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
    User create(String username, String password, String firstName, String lastName, String email, String phone, byte[] image);


    /**
     * Returns an {@link User} given it's username
     * @param username - username User is registered with
     * @return The corresponding User or Empty if it doesn't exist
     */
    Optional<User> findByUsername(String username);


    Optional<User> findById(int id);




}
