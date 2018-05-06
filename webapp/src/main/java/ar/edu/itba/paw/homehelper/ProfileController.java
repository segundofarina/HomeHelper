package ar.edu.itba.paw.homehelper;

import ar.edu.itba.paw.interfaces.services.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    SProviderService sProviderService;

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@PathVariable("providerId") int providerId,@ModelAttribute("appointmentForm") final AppointmentForm form) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("provider",sProviderService.getServiceProviderWithUserId(providerId));

        return mav;
    }

    @RequestMapping(value = "/setAppointment", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final AppointmentForm form, final BindingResult errors) {
        if (errors.hasErrors()) { return providerProfile(4,form);
        }
        final ModelAndView mav = new ModelAndView("profile");

        return mav;
    }
}
