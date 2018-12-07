package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;


@Repository
public class UserHibernateDao implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(String username, String password, String firstname, String lastname, String email, String phone, String address) {
        final User user = new User(username, password, firstname, lastname, email, phone, address, /*image,*/ false);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> verifyUser(int userId) {
        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setVerified(true));
        return userOp;
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return em.createQuery("from User as u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<UserImage> getImage(int userId){
        return em.createQuery("FROM UserImage as im  where im.userId = :userid", UserImage.class)
                        .setParameter("userid", userId)
                        .getResultList()
                        .stream()
                        .findFirst();
    }

    @Override
    public boolean updatePasswordOfUser(int userId, String password) {
        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setPassword(password));
        return userOp.isPresent();
    }

    @Override
    public boolean updateFirstNameOfUser(int userId, String firstname) {

        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setFirstname(firstname));
        return userOp.isPresent();
    }

    @Override
    public boolean updateLastNameOfUser(int userId, String lastname) {

        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setLastname(lastname));
        return userOp.isPresent();
    }

    @Override
    public boolean updateEmailOfUser(int userId, String email) {
        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setEmail(email));
        return userOp.isPresent();
    }

    @Override
    public boolean updatePhoneOfUser(int userId, String phone) {
        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setPhone(phone));
        return userOp.isPresent();
    }

    @Override
    public boolean updateImageOfUser(int userId, byte[] image) {

        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        if(!userOp.isPresent()){
            return false;
        }

        Optional<UserImage> previousImage=
                em.createQuery("FROM UserImage as im where im.userId = :userid", UserImage.class)
                .setParameter("userid", userId)
                .getResultList()
                .stream()
                .findFirst();

        previousImage.ifPresent(im -> em.remove(im));
        em.persist(new UserImage(userId,image));
        return true;
    }

    @Override
    public boolean updateAddressOfUser(int userId, String address) {
        Optional<User> userOp = Optional.ofNullable(em.find(User.class, userId));
        userOp.ifPresent(user -> user.setAddress(address));
        return userOp.isPresent();
    }
}
