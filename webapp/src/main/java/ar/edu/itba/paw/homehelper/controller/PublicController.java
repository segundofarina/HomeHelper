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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PublicController {
    @Autowired
    private SProviderService sProviderService;

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }

    @ModelAttribute("appointmentForm")
    public AppointmentForm appointmentForm() {
        return new AppointmentForm();
    }


    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("serviceTypes", sProviderService.getServiceTypes());
        return mav;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView processSearch(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors, final RedirectAttributes redrAttr, @RequestHeader(value = "referer", required = false, defaultValue = "/") final String referer, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, @RequestParam(required = false, value = "cty", defaultValue = "-1") final int cityId) {

        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.searchForm", errors);
            redrAttr.addFlashAttribute("searchForm", form);
            String redirect = "redirect:";

            if(referer.contains("/searchResults")) {
                /* Referer page is searchResults, send it back there */
                redirect += "/searchResults?st=" + serviceTypeId + "&cty=" + cityId;
            } else {
                /* Referer page is index */
                redirect += "/";
            }

            return new ModelAndView(redirect);
        }

        redrAttr.addFlashAttribute("searchForm", form);
        String redirect = "redirect:/searchResults?st=" + form.getServiceTypeId() + "&cty=" + form.getCityId();
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/searchResults", method = RequestMethod.GET)
    public ModelAndView searchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, @RequestParam(required = false, value = "cty", defaultValue = "-1") final int cityId) {
        final ModelAndView mav = new ModelAndView("profileSearch");

        /* Lanzar excepcion cuando serviceTypeId es -1 o cuando cityId es -1 */

        final List<SProvider> list = sProviderService.getServiceProvidersWithServiceType(serviceTypeId);;


        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        mav.addObject("list",list);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());

        /* Current params showing */
        mav.addObject("serviceTypeId", serviceTypeId);
        mav.addObject("cityId", cityId);
        return mav;
    }

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId) {
        final ModelAndView mav = new ModelAndView("profile");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("provider",sProviderService.getServiceProviderWithUserId(providerId));

        return mav;
    }

    @RequestMapping(value = "/profile/sendAppointment", method = RequestMethod.POST)
    public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("appointmentForm") final AppointmentForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            /* Back to form */
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.appointmentForm", errors);
            redrAttr.addFlashAttribute("appointmentForm", form);

            String redirect = "redirect:/profile/" + form.getProviderId();
            return new ModelAndView(redirect);
        }

        redrAttr.addFlashAttribute("appointmentForm", form);

        if(loggedInUser == null) {
            String redirect = "redirect:/login";
            return new ModelAndView(redirect);
        }

        return new ModelAndView("forward:/client/sendAppointment");
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
