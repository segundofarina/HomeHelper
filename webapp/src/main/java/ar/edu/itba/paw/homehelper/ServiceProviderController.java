package ar.edu.itba.paw.homehelper;

import ar.edu.itba.paw.interfaces.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ServiceProviderController {

    @Autowired
    private SProviderService sProviderService;

    @RequestMapping("/sprovider/{providerId}")
    public ModelAndView provider(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        mav.addObject("providerId", providerId);
        mav.addObject("postList", sProviderService.getPosts(providerId));

        return mav;
    }

}
