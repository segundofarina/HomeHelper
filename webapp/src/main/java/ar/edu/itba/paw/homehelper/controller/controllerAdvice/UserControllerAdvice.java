package ar.edu.itba.paw.homehelper.controller.controllerAdvice;

import ar.edu.itba.paw.interfaces.controller.SecurityUserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        return null;
        //return securityUserService.getLoggedInUser();
    }
}
