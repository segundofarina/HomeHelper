package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionController {

    @Autowired
    SProviderService sProviderService;

    @RequestMapping("/error/400")
    public ModelAndView error400(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/400");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "400");
        mav.addObject("errorDesc", "Bad Request");

        return mav;
    }

    @RequestMapping("/error/404")
    public ModelAndView error404(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/404");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "404");
        mav.addObject("errorDesc", "Page Not Found");

        return mav;
    }

    @RequestMapping("/error/403")
    public ModelAndView error403(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/403");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "403");
        mav.addObject("errorDesc", "Access Forbidden");

        return mav;
    }

    @RequestMapping("/error/500")
    public ModelAndView error500(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("error/500");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("errorNum", "500");
        mav.addObject("errorDesc", "Internal Server Error");

        return mav;
    }

    private int getUserId(User user) {
        if (user == null) {
            return -1;
        }
        return user.getId();
    }
}
