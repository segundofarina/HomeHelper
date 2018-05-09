package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    SProviderService sProviderService;


    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("searchForm") final SearchForm form) {
        final ModelAndView mav = new ModelAndView("index");
        int userId = -1;

        if(loggedInUser != null) {
            userId = loggedInUser.getId();
        }

        mav.addObject("userId", userId);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());
        return mav;
    }

    @RequestMapping("/login") public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/search")
    public ModelAndView searchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(loggedInUser, form);
        }

        final ModelAndView mav = new ModelAndView("profileSearch");
        int userId = -1;

        if(loggedInUser != null) {
            userId = loggedInUser.getId();
        }

        mav.addObject("userId", userId);

        List<SProvider> list =sProviderService.getServiceProvidersWithServiceType(form.getServiceTypeId());
        mav.addObject("list",list);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());
        return mav;
    }
    

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId,@ModelAttribute("appointmentForm") final AppointmentForm form) {
        final ModelAndView mav = new ModelAndView("profile");
        int userId = -1;

        if(loggedInUser != null) {
            userId = loggedInUser.getId();
        }

        mav.addObject("userId", userId);
        mav.addObject("provider",sProviderService.getServiceProviderWithUserId(providerId));

        return mav;
    }

    @RequestMapping(value = "/setAppointment", method = { RequestMethod.POST })
    public ModelAndView create(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("appointmentForm") final AppointmentForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return providerProfile(loggedInUser, 4,form);
        }
        final ModelAndView mav = new ModelAndView("profile");

        return mav;
    }
}
