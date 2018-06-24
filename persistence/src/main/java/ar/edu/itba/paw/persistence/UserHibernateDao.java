package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Transactional
@Repository
public class UserHibernateDao implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(String username, String password, String firstname, String lastname, String email, String phone, String address, byte[] image){
        final User user = new User(username,password, firstname, lastname, email, phone, address, image,false);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> verifyUser(int userId) {
        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return user;
        }
        user.get().setVerified(true);
        return user;
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class);
        query.setParameter("username", username);
        return Optional.of(query.getResultList().get(0));
    }

    @Override
    public java.util.Optional<User> findById(int id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public boolean updatePasswordOfUser(int userId, String password) {
        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setPassword(password);
        return true;
    }

    @Override
    public boolean updateFirstNameOfUser(int userId, String firstname) {

        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setFirstname(firstname);
        return true;
    }

    @Override
    public boolean updateLastNameOfUser(int userId, String lastname) {

        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setLastname(lastname);
        return true;
    }

    @Override
    public boolean updateEmailOfUser(int userId, String email) {
        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setEmail(email);
        return true;
    }

    @Override
    public boolean updatePhoneOfUser(int userId, String phone) {
        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setPhone(phone);
        return true;
    }

    @Override
    public boolean updateImageOfUser(int userId, byte[] image) {

        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setImage(image);
        return true;
    }

    @Override
    public boolean updateAddressOfUser(int userId, String address) {
        Optional<User> user = Optional.of(em.find(User.class,userId));
        if(!user.isPresent()){
            return false;
        }
        user.get().setAddress(address);
        return true;
    }
}
