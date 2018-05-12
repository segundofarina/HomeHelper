package ar.edu.itba.paw.homehelper.controller.controllerAdvice;

import ar.edu.itba.paw.homehelper.exceptions.ForbiddenException;
import ar.edu.itba.paw.homehelper.exceptions.NotFoundException;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsControllerAdvice {

    @Autowired
    SProviderService sProviderService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundHandler(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/404");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "404");
        mav.addObject("errorDesc", "Page Not Found");

        return mav;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ModelAndView forbiddenHandler(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/403");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "403");
        mav.addObject("errorDesc", "Access Forbidden");

        return mav;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView generalFailureHandler(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/500");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "500");
        mav.addObject("errorDesc", "Internal Server Error");

        return mav;
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
