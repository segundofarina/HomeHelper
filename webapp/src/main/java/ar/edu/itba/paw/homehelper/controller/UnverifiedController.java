package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UnverifiedController {

    @RequestMapping("/unverified/user")
    public ModelAndView unverifiedUser(@ModelAttribute("loggedInUser") final User loggedInUser) {
        ModelAndView mav = new ModelAndView("unverified/indexUnverified");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", -1);

        return mav;
    }
}
