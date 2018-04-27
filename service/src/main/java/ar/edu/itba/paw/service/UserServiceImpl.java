package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao us;


    public User findById(int id) {
        return us.findById(id);
    }
}
