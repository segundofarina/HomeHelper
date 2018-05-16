package ar.edu.itba.paw.homehelper.controller.controllerAdvice;

import ar.edu.itba.paw.homehelper.exceptions.*;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsControllerAdvice {

    @Autowired
    SProviderService sProviderService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ProviderNotFoundException.class, InvalidUsernameException.class})
    public ModelAndView providerNotFound(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/404");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "404");
        mav.addObject("errorDesc", "Provider Not Found");

        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidQueryException.class)
    public ModelAndView invalidQueryExeption(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/500");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "400");
        mav.addObject("errorDesc", "Bad Request");

        return mav;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, UploadException.class})
    public ModelAndView generalFailureHandler(@ModelAttribute("loggedInUser") final User loggedInUser) {
        System.out.println("500 error");

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
