package ar.edu.itba.paw.homehelper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@PathVariable("providerId") int providerId) {
        final ModelAndView mav = new ModelAndView("profile");

        return mav;
    }
}
