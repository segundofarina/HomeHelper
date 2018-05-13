package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.exceptions.ProviderNotFoundException;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.ImageForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.interfaces.services.ChatService;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    SProviderService sProviderService;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;


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
    @ResponseBody
    @RequestMapping(value = "/profile/{userId}/profileimage",produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] providerProfileImage(@PathVariable("userId") int userId) {
        return userService.getProfileImage(userId);
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
       
        User user = userService.create(form.getUsername(),form.getPasswordForm().getPassword(),form.getFirstname(),form.getLastname(),form.getEmail(),form.getPhone(),null);

        mailService.sendConfirmationEmail(user.getEmail(),user.getId());


        UserDetails userDetails =userDetailsService.loadUserByUsername(user.getUsername());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);


        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/upload",method = {RequestMethod.GET})
    public ModelAndView formCompletion(@ModelAttribute("imageForm") final ImageForm form) {

        final ModelAndView mav = new ModelAndView("upload");
        return mav;
    }

    @RequestMapping(value = "/upload",method = {RequestMethod.POST})
    public ModelAndView formCompletion2(@ModelAttribute("imageForm") final ImageForm form) {

        User user= null;
        try {
            user =userService.create("segundo","123456","segundo","farina","segundo","23",form.getProfilePicture().getBytes());

        }catch (Exception e){
            e.printStackTrace();
        }

        SProvider sProvider = sProviderService.create(user.getId(),"hola que tal");

        final ModelAndView mav = new ModelAndView("redirect:/profile/"+sProvider.getId());
        return mav;
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
