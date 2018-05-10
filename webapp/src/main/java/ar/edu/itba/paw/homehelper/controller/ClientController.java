package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("serviceTypes", sProviderService.getServiceTypes());
        return mav;
    }

    @RequestMapping("/login") public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView processSearchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors, @RequestParam(required = false, defaultValue = "y", value = "redr") final String redr, @RequestParam(required = false, defaultValue = "none", value = "st") final String st) {
        if(errors.hasErrors()) {
            if(redr.equals("y")) {
                return index(loggedInUser, form);
            }
            return searchProfile(loggedInUser, form, st);
        }
        return searchProfile(loggedInUser, form, String.valueOf(form.getServiceTypeId()));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, @RequestParam(required = false, defaultValue = "none", value = "st") final String st) {
        final ModelAndView mav = new ModelAndView("profileSearch");
        final int serviceTypeId;
        final List<SProvider> list;

        if(st.equals("none")) {
            serviceTypeId = form.getServiceTypeId();
        } else {
            serviceTypeId = Integer.parseInt(st);
        }
        list = sProviderService.getServiceProvidersWithServiceType(serviceTypeId);

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

       // List<SProvider> list = sProviderService.getServiceProvidersWithServiceType(form.getServiceTypeId());
        mav.addObject("list",list);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());
        mav.addObject("serviceTypeId", serviceTypeId);
        return mav;
    }
    

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId,@ModelAttribute("appointmentForm") final AppointmentForm form) {
        final ModelAndView mav = new ModelAndView("profile");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
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

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
