package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    UserService userService;

    @Autowired
    HHUserDetailsService userDetailsService;

    @Autowired
    MailService mailService;


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

    @RequestMapping("/search")
    public ModelAndView searchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(loggedInUser, form);
        }

        final ModelAndView mav = new ModelAndView("profileSearch");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        List<SProvider> list = sProviderService.getServiceProvidersWithServiceType(form.getServiceTypeId());
        mav.addObject("list",list);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());
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

    @RequestMapping("/signup")
    public ModelAndView signup(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("signUpForm") final SignUpForm form) {
        final ModelAndView mav = new ModelAndView("signup");

        mav.addObject("user", loggedInUser);
        return mav;
    }

    @RequestMapping("/sendEmail")
    public ModelAndView sendEmail(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("signUpForm") final SignUpForm form) {
        mailService.sendConfirmationEmail("afarina@itba.edu.ar",1);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView createUser(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("signUpForm") final SignUpForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return signup(loggedInUser, form);
        }

        User user = userService.create(form.getUsername(),form.getPassword(),form.getFirstname(),form.getLastname(),form.getEmail(),form.getPhone());

        mailService.sendConfirmationEmail(user.getEmail(),user.getId());


        UserDetails userDetails =userDetailsService.loadUserByUsername(user.getUsername());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);


        return new ModelAndView("redirect:/");
    }


    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
