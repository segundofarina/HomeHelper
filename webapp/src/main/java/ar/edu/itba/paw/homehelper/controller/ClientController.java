package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.exceptions.ProviderNotFoundException;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.CreateSProviderForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.interfaces.services.*;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    SProviderService sProviderService;

    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @Autowired
    HHUserDetailsService userDetailsService;

    @Autowired
    MailService mailService;

    @ModelAttribute("createSProviderForm")
    public CreateSProviderForm createSProviderForm() {
        return new CreateSProviderForm();
    }


    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("searchForm") final SearchForm form) {
        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("serviceTypes", sProviderService.getServiceTypes());
        return mav;
    }

    @RequestMapping("/login") public ModelAndView login(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referer);

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

        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        if(provider == null) {
            throw new ProviderNotFoundException();
        }

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("provider", provider);

        return mav;
    }

    @RequestMapping(value = "/client/setAppointment", method = { RequestMethod.POST })
    public ModelAndView create(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("appointmentForm") final AppointmentForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return providerProfile(loggedInUser, 4,form);
        }
        final ModelAndView mav = new ModelAndView("profile");

        return mav;
    }

    @RequestMapping(value = "/client/messages/{providerId}", method = { RequestMethod.POST })
    public ModelAndView sendMessagePost(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId, @RequestParam("msg") String msg) {
        final int userId = loggedInUser.getId();

        chatService.sendMsg(userId, providerId, msg);

        return new ModelAndView("redirect:/client/messages/" + providerId);
    }

    @RequestMapping(value = "/client/messages/{providerId}", method = {RequestMethod.GET})
    public ModelAndView messages(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") final int providerId) {
        final ModelAndView mav = new ModelAndView("clientMessages");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        mav.addObject("chats", chatService.getChatsOf(loggedInUser.getId()));
        mav.addObject("currentChat", chatService.getChat(loggedInUser.getId(), providerId));

        return mav;
    }

    @RequestMapping("/client/messages")
    public ModelAndView messagesGeneral(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final int userId = loggedInUser.getId();
        return new ModelAndView("redirect:/client/messages/" + chatService.getLastMsgThread(userId));
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

        //User user = userService.create(form.getUsername(),form.getPassword(),form.getFirstname(),form.getLastname(),form.getEmail(),form.getPhone());

        User user = userService.create(form.getUsername(),form.getPasswordForm().getPassword(),form.getFirstname(),form.getLastname(),form.getEmail(),form.getPhone());

        mailService.sendConfirmationEmail(user.getEmail(),user.getId());


        UserDetails userDetails =userDetailsService.loadUserByUsername(user.getUsername());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);


        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/client/createSProvider", method = {RequestMethod.GET})
    public ModelAndView createProvider(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "error", defaultValue = "n") final String error) {
        final ModelAndView mav = new ModelAndView("createSProvider");

        mav.addObject("user", loggedInUser);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());

        if(error.equals("n")) {
            mav.addObject("hasError", 0);
        } else {
            mav.addObject("hasError", 1);
        }

        return mav;
    }

    @RequestMapping(value = "/client/createSProvider", method = RequestMethod.POST)
    public ModelAndView postCreateProvider(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("createSProviderForm") final CreateSProviderForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.createSProviderForm", errors);
            redrAttr.addFlashAttribute("createSProviderForm", form);
            return new ModelAndView("redirect:/client/createSProvider?error=y");
        }

        /* Save as service provider and add service provider privilages */
        sProviderService.create(loggedInUser.getId(), form.getProfileDesc());
        sProviderService.addAptitude(loggedInUser.getId(), form.getServiceTypeId(), form.getAptDesc());
        //update user

        UserDetails userDetails = userDetailsService.loadUserByUsername(loggedInUser.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/sprovider");
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
