package ar.edu.itba.paw.homehelper;

import ar.edu.itba.paw.interfaces.services.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    @Autowired
    SProviderService sProviderService;

    @RequestMapping("/search")
    public ModelAndView searchProfile() {
        final ModelAndView mav = new ModelAndView("profileSearch");
        mav.addObject("list",sProviderService.getServiceProviders());
        return mav;
    }

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@PathVariable("providerId") int providerId) {
        final ModelAndView mav = new ModelAndView("profile");

        return mav;
    }
}
