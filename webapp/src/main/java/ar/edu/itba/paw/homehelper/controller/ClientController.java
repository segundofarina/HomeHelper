package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.exceptions.ProviderNotFoundException;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.ImageForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.homehelper.validators.EqualsUsernameValidator;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.NeighborhoodService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ClientController {

    @Autowired
    private SProviderService sProviderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;


    @Autowired
    private HHUserDetailsService userDetailsService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EqualsUsernameValidator equalsUsernameValidator;

    @Autowired
    private AppointmentService appointmentService;



   /* Este metodo no andaa */
   @RequestMapping(value = "/client/getSendAppointment", method = RequestMethod.GET)
   public ModelAndView getSendAppointment(@ModelAttribute("appointmentForm") final AppointmentForm form, RedirectAttributes redrAttr, HttpServletRequest request) {
       if(form == null) {
           //throw new ForbiddenException();
       }

       System.out.println(form.getDate());

       redrAttr.addFlashAttribute("appointmentForm", form);
       //request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
       return new ModelAndView("forward:/client/sendAppointment");
   }

   @RequestMapping(value = "/client/sendAppointment", method = RequestMethod.POST)
   public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("appointmentForm") final AppointmentForm form) {
       if(form == null) {
           //throw exception
       }

       appointmentService.addAppointment(loggedInUser.getId(), form.getProviderId(), form.getServiceTypeId(), form.getDate(),  "", form.getDescription());

       return new ModelAndView("redirect:/client/appointmentConfirmed");
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


    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView createUser(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("signUpForm") final SignUpForm form, final BindingResult errors) {


        User invalidUser =  userService.findByUsername(form.getUsername());


        if(invalidUser != null) {

            equalsUsernameValidator.validate(EqualsUsernameValidator.buildUserNamePair(form.getUsername(),invalidUser.getUsername()), errors);
        }

        if (errors.hasErrors()) {
            return signup(loggedInUser, form);
        }

        //User user = userService.create(form.getUsername(),form.getPassword(),form.getFirstname(),form.getLastname(),form.getEmail(),form.getPhone());
        byte[] image;
        try{
            image =form.getProfilePicture().getBytes();
        }catch (Exception e){
            e.printStackTrace();
            image = null;
        }
        User user = userService.create(form.getUsername(),form.getPasswordForm().getPassword(),form.getFirstname(),form.getLastname(),form.getEmail(),form.getPhone(),form.getEmail(),image);

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





